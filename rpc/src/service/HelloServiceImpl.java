package service;

public class HelloServiceImpl  implements HelloService{


    @Override
    public String hello() {
        return "thisSimpleRpc";
    }
}
