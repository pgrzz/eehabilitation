package register;

import java.util.Objects;

public class ServiceMetaInfo {

    private String serviceName;


    private String ip;

    private int port;

    private Object obj;



    public ServiceMetaInfo(String serviceName, String ip, int port, Object obj) {
        this.serviceName = serviceName;
        this.ip = ip;
        this.port = port;
        this.obj = obj;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }


    @Override
    public String toString() {
        return "register.ServiceMetaInfo{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServiceMetaInfo that = (ServiceMetaInfo) o;
        return port == that.port && Objects.equals(serviceName, that.serviceName) && Objects.equals(ip, that.ip);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serviceName, ip, port);
    }
}
