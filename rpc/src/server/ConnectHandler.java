package server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ConnectHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg)  {

        //only include service name, no args,  //default futureId just 1
        /*
         *  消息结构 serviceName-methodName-InvokeId
         */
        if(msg.equals("heartBeat")){
            //ignore
            return;
        }
        String serviceName=msg.split("-")[0];
        String methodName=msg.split("-")[1];

        Integer invokeId=Integer.parseInt(msg.split("-")[2]);

        Channel channel= ctx.channel();

        MessageTask messageTask=new MessageTask(invokeId,serviceName,methodName,channel);

        //交给业务线程处理
        Server.threadPoolExecutor.execute(messageTask);
    }


}
