package server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageTask implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(MessageTask.class);


    private Integer invokeId;

    private String serviceName;

    private String methodName;

    private Channel channel;

    //this could be singleton
    private Process process=new DefaultMessageProcessor();



    public MessageTask(Integer invokeId, String serviceName, String methodName, Channel channel) {
        this.invokeId = invokeId;
        this.serviceName = serviceName;
        this.methodName = methodName;
        this.channel = channel;
    }


    public Integer getInvokeId() {
        return invokeId;
    }

    public void setInvokeId(Integer invokeId) {
        this.invokeId = invokeId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    @Override
    public void run() {

        Object obj=null;
        try {
             obj= process.handlerMessageTask(this);
            //ignore obj just test void method.
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        String returnMessage=serviceName+"-"+methodName+"-"+invokeId+"-"+obj;

       channel.writeAndFlush(returnMessage).addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
               //log success  query times.
                logger.warn("message task request success");
            } else {
                logger.error("Response sent failed, duration: {} millis, channel: {}, cause: {}.",
                        "ignore", channel, future.cause().getMessage());

            }
        });
    }

}
