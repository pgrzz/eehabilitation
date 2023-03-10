package client;

import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceProxy  implements InvocationHandler {


    private static final Logger logger = LoggerFactory.getLogger(Client.class);

    /**
     * serviceName service Proxy
     */
    public  static final Map<String,Object> serviceProxy=new ConcurrentHashMap<>();


    @SuppressWarnings("unchecked")
    public static <T> T getServiceProxy(Class<T> interfaceType){
        //proxy
        T t=(T)serviceProxy.get(interfaceType.getName());
        if(t==null){
            t= newProxy(interfaceType,new ServiceProxy());
            serviceProxy.putIfAbsent(interfaceType.getName(),t);
        }
        return t;
    }

    private   static <T> T newProxy(Class<T> interfaceType, Object handler) {

        Object object = Proxy.newProxyInstance(
                interfaceType.getClassLoader(), new Class<?>[] { interfaceType }, (InvocationHandler) handler);

        return interfaceType.cast(object);
    };


    /**
     *返回代理类
     */




    @Override
    public  Object invoke(Object obj, Method method, Object[] args) throws Throwable {

        //做服务发现拿到service对应的service ip:port
        String serviceName=method.getDeclaringClass().getName();

        //payload channel
        Channel channel=  Client.payLoadChannel(serviceName);

        Random random = new Random();
        //生成 invokeId
         int invokeId = random.nextInt(1000);
        //生成访问结构
       // serviceName-methodName-InvokeId
        String methodName=method.getName();
        String request=serviceName+"-"+methodName+"-"+invokeId;

        InvokeFuture<Object> invokeFuture=new InvokeFuture<>(invokeId);
        Unpooled.copiedBuffer(request, CharsetUtil.UTF_8);
        channel.writeAndFlush(request).addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                logger.warn("proxy call channel write success");
            } else {
                logger.error("proxy call channel write fail,serviceName:{},methodName:{},invokeId:{}",serviceName,methodName,invokeId);

            }
        });

        //just show sync way
        return   invokeFuture.getResult();

    }
}
