package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;
import register.RegistryCenter;
import service.HelloService;
import service.HelloServiceImpl;
import java.util.concurrent.*;

/**
 * 一把流
 */
public class Server {

    private static  final EventLoopGroup bossGroup=new NioEventLoopGroup();

    //most set cpu *2  use config file to set.
    private static  final   EventLoopGroup workGroup=new NioEventLoopGroup(4*2);


    // 业务线程池 default  AbortPolicy() when queue full, but most situation choice wait for block.
    public static  final ThreadPoolExecutor threadPoolExecutor=new ThreadPoolExecutor(4,8,60 *5, TimeUnit.SECONDS,new ArrayBlockingQueue<>(20));

    public static void main(String[] args){

        //一向注册中心注册服务
         //1、 扫描得到一组服务假设，服务
        String serviceName= HelloService.class.getName();

        int port=8080;
        //服务注册
        RegistryCenter.registryService(serviceName,"127.0.0.1",port,new HelloServiceImpl());

        ServerBootstrap serverBootstrap=new ServerBootstrap();

        serverBootstrap.group(bossGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                .childOption(ChannelOption.TCP_FASTOPEN,128)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) {
                        ch.pipeline().addLast(new LoggingHandler());
                        ch.pipeline().addLast(new StringDecoder());
                        ch.pipeline().addLast(new StringEncoder());
                        //由客服端来管理连接保持管理
                        ch.pipeline().addLast(new ConnectHandler());
                    }
                });
        try {
            serverBootstrap.bind(port).sync();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //ignore future status only consider success



    }

}
