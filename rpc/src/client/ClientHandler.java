package client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Created by lenovo on 2016/6/17.
 */
public class ClientHandler extends SimpleChannelInboundHandler<String> {

    private static final Logger logger = LoggerFactory.getLogger(ClientHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
            // decode     String returnMessage=serviceName+"-"+methodName+"-"+invokeId+"-"+obj;

        logger.error("msg:{}",msg);

        Client.threadPoolExecutor.execute(()->{

//            String serviceName=msg.split("-")[0];
//            String methodName=msg.split("-")[1];
            int invokeId=Integer.parseInt(msg.split("-")[2]);
            String obj=msg.split("-")[3];

            InvokeFuture<?> invokeFuture= InvokeFuture.getFuture(invokeId);
            //recover proxy thread status
            boolean success=  invokeFuture.finish(invokeId,obj);

        });

    }
}
