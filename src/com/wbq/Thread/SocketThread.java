package com.wbq.Thread;

import java.net.Socket;

import com.wbq.interfacr.MessageListener;

public class SocketThread extends Thread{
	private Socket socket;
	private Client client;
	private String ip;
	private int port;
	private boolean isStart = false;
	private MessageListener mMessageListener;
	
	/**
	 * 使用TCP协议,连接访问 
	 * @param ip 目标机器的ip
	 * @param port
	 * @param mMessageListener
	 */
	public SocketThread(String ip, int port, MessageListener mMessageListener) {
		super();
		this.ip = ip;
		this.port = port;
		this.mMessageListener = mMessageListener;
	}
	
	//直接通过client得到读线程
	public ClientInputThread geClientInputThread(){
		return client.getIn();
	} 
	
	//直接通过client得到写线程
	public ClientOutputThread getClientOutputThread(){
		return client.getOut();
	}
	
	//返回Socket状态
	public boolean isStart(){
		return isStart;
	}
	
	// 直接通过client停止读写消息  
    public void setIsStart(boolean isStart) {  
        this.isStart = isStart;  
        client.getIn().setStart(isStart);  
        client.getOut().setStart(isStart);  
    }  
      
    //发送消息  
    public void sendMsg(String msg){  
        client.getOut().sendMsg(msg);  
    }  
	
	public class Client{
		public ClientInputThread in;
		public ClientOutputThread out;
		
		public Client(Socket socket,MessageListener mMessageListener){
			//用这个监听输入流线程来接收信息  
            in = new ClientInputThread(socket);  
            in.setMessageListener(mMessageListener);  
            //以后就用这个监听输出流的线程来发送信息了  
            out = new ClientOutputThread(socket);  
		}
		
		public void start(){
			in.setStart(true);
			out.setStart(true);
			in.start();
			out.start();
		}
		
		//得到读消息线程
		public ClientInputThread getIn(){
			return in;
		}
		
		//得到写消息线程
		public ClientOutputThread getOut(){
			return out;
		}
	}
}
