package register;

import service.HelloService;

public class Tools {


    public static final int DEFAULTRPCPORT=8080;
    public static final String DEFAULTIP="127.0.0.1";

    public Class getService(){
        return HelloService.class;
    }

}
