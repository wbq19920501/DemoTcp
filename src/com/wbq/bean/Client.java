package com.wbq.bean;

public class Client {
	String IP;  
    int udpPort;
    
    public Client(String IP) {  
        this.IP = IP;  
    }  
    public void setUdpPort(int udpPort) {  
        this.udpPort = udpPort;  
    }  
}
