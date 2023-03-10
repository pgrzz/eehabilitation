package server;

import register.RegistryCenter;
import java.lang.reflect.Method;
import java.util.Optional;

public class DefaultMessageProcessor  implements Process {
    @Override
    public Object handlerMessageTask(MessageTask messageTask) {
        String interfaceName=messageTask.getServiceName();
        String methodName=messageTask.getMethodName();
        Object service=  RegistryCenter.getService(interfaceName);
        Class<?> clas=   service.getClass();
        //获取这个代理类的构造方法
        Method[] methods = clas.getMethods();

        System.out.println("---------------------begin Construstors-----------------");
        Optional<Method> mm=Optional.empty();
        //遍历构造方法
        for (Method method: methods) {
            //获取每个名称
            String name = method.getName();
            if(name.equals(methodName)){
                mm=Optional.of(method);

                break;
            }
        }
        // test method just  use void args
        Object[] arguments=null;
        try {
            return   mm.orElseThrow().invoke(service,arguments);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
