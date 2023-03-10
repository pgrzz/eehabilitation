package client;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

/**
 *  rpc future
 */
public class InvokeFuture<T> extends CompletableFuture<T> {



    //get result until the result is not null

    //模拟异步调用场景
    /**
     * 这里要小心的处理 timeout，error，释放掉invokeFutureMap 的对象，并且做假唤醒
     */
   private  static final Map<Integer,InvokeFuture<?>>    invokeFutureMap=new ConcurrentHashMap<>();





    public InvokeFuture(int invokeId) {
        invokeFutureMap.put(invokeId,this);
    }





    public   T getResult(){

        try {
            return this.get();
           // the time out version get(long timeout, TimeUnit unit) use for set rpc time out
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

    }




    public static InvokeFuture<?> getFuture(int invokeId){
        return invokeFutureMap.remove(invokeId);
    }

    @SuppressWarnings("all")
    public boolean  finish(int invokeId,Object object){
        InvokeFuture<?> invokeFuture= InvokeFuture.getFuture(invokeId);
       return complete((T)object);
    }



}
