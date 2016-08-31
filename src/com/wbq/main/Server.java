package com.wbq.main;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	public static void main(String[] args) {
		ServerReceviedByTcp();
	}

	private static void ServerReceviedByTcp() {
		ServerSocket serversocket = null;//声明一个socket对象
		try {
			serversocket = new ServerSocket(3344);//创建一socket对象并监听端口
			// 调用ServerSocket的accept()方法，接受客户端所发送的请求，  
	        // 如果客户端没有发送数据，那么该线程就停滞不继续  
			Socket socket = serversocket.accept();
			InputStream inputStream = socket.getInputStream();
			byte buffer[] = new byte[1024*4];
			int temp = 0;
			while ((temp = inputStream.read(buffer)) != -1) {
				System.out.println(new String(buffer,0,temp));
			}
			serversocket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
