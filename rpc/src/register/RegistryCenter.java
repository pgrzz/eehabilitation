package register;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RegistryCenter {

    public  static final Map<String,ServiceMetaInfo> serviceList=new ConcurrentHashMap<>();

    public static Object getService(String serviceName){
        ServiceMetaInfo serviceMetaInfo=   serviceList.get(serviceName);
        return serviceMetaInfo.getObj();
    }

    public static ServiceMetaInfo getServiceMetha(String serviceName){
        return serviceList.get(serviceName);
    }


    public static void registryService(String service,String ip,int port,Object ojb){



        ServiceMetaInfo serviceMetaInfo=new ServiceMetaInfo(service,ip,port,ojb);

        serviceList.put(service,serviceMetaInfo);
    }




}
