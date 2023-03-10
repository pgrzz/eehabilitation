package client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import register.RegistryCenter;
import register.ServiceMetaInfo;
import register.Tools;
import service.HelloService;
import service.HelloServiceImpl;

import java.util.Map;

import java.util.Random;
import java.util.concurrent.*;

public class Client {

    private static final Logger logger = LoggerFactory.getLogger(Client.class);


    //service - channel List

    private static final EventLoopGroup eventExecutors = new NioEventLoopGroup();

    private static final Random random = new Random();


    public static final Map<ServiceMetaInfo, CopyOnWriteArrayList<Channel>> channelGroup = new ConcurrentHashMap<>();

    //ip port-channel

    public static final ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(4, 8, 60 * 5, TimeUnit.SECONDS, new ArrayBlockingQueue<>(20));


    private static final CountDownLatch countDownLatch=new CountDownLatch(1);

    private static final Bootstrap bootstrap = new Bootstrap();

    public static void main(String[] args) {


        initBootstrap();
        //先根据接口得到代理
        //这里也需要注册因为是内存级别的所以需要hack
        RegistryCenter.registryService(HelloService.class.getName(),"127.0.0.1", Tools.DEFAULTRPCPORT,new HelloServiceImpl());

        initConnectList();
        //等待连接可用
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //连接注册中心，初始化服务列表

        HelloService helloService = ServiceProxy.getServiceProxy(HelloService.class);

        String a= helloService.hello();
        //真实的时候这里会由spring在init的时候生成代理实例
        System.out.println(a);



    }

    private static void initConnectList() {

        //get service list ,test only have hello service
        String serviceName = HelloService.class.getName();
        ServiceMetaInfo serviceMetaInfo = RegistryCenter.getServiceMetha(serviceName);

        //init connectList and do connect reuse.
        connect(serviceMetaInfo);


    }


    private static void initBootstrap() {


        bootstrap.group(eventExecutors).channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_FASTOPEN, 128)
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringEncoder());
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new ClientHandler());
                        ch.pipeline().addLast(new ConnectorIdleStateTrigger());
                    }
                });
    }

    public static void connect(ServiceMetaInfo serviceMetaInfo) {

        ChannelFuture future = bootstrap.connect(serviceMetaInfo.getIp(), serviceMetaInfo.getPort());

        future.addListener((ChannelFutureListener) f -> {

            boolean succeed = f.isSuccess();



            logger.warn("Reconnects with {}, {}.", serviceMetaInfo, succeed ? "succeed" : "failed");
            if (!succeed) {
                //  try reconnect
                logger.error("connect fail with {}.", serviceMetaInfo);
                f.channel().pipeline().fireChannelInactive();
            } else {
                CopyOnWriteArrayList<Channel> channels = channelGroup.get(serviceMetaInfo);
                if (channels == null) {
                    channels = new CopyOnWriteArrayList<>();
                    channels.add(f.channel());
                    channelGroup.putIfAbsent(serviceMetaInfo, channels);

                    countDownLatch.countDown();
                }

            }
        });

    }

    public static Channel payLoadChannel(String serviceName) {
        ServiceMetaInfo serviceMetaInfo = RegistryCenter.getServiceMetha(serviceName);

        CopyOnWriteArrayList<Channel> channels = channelGroup.get(serviceMetaInfo);

        //payload
        int load = random.nextInt(channels.size());

        return channels.get(load);


    }


}
