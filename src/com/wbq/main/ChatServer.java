package com.wbq.main;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class ChatServer {
	boolean started = false;
	ServerSocket ss = null;
	
	List<Client> clients = new ArrayList<>();
	public static void main(String[] args) {
		System.out.println("启动服务了");
		new ChatServer().start();
	}
	
	private void start() {
		try {
			//ServerSocket 监听8888 端口
			ss = new ServerSocket(8888);
			started = true;
		} catch (Exception e) {
			
		}
		
		try {  
			
			while (started) {  
				Socket s = ss.accept();  
				Client c = new Client(s);  
				System.out.println("a client connected!");  
				new Thread(c).start();  
				clients.add(c);  
			}  
		} catch (IOException e) {  
			e.printStackTrace();  
		} finally {  
			try {  
				ss.close();  
			} catch (IOException e) {  
				e.printStackTrace();  
			}  
		}  
	}
	
	
	class Client implements Runnable{

		private Socket s;
		private DataInputStream dis = null;
		private DataOutputStream dos = null;
		private boolean bConnected = false;
		
		public Client(Socket s){
			this.s = s;
			try {
				dis = new DataInputStream(s.getInputStream());
				dos = new DataOutputStream(s.getOutputStream());
				bConnected = true;
			} catch (Exception e) {
				
			}
		}
		
		public void send(String str){
			try {
				dos.writeUTF(str);
			} catch (Exception e) {
				clients.remove(this);
				System.out.println("关闭一个连接");
			}
		}
		
		@Override
		public void run() {
			try {
				while (bConnected) {
					String str = dis.readUTF();
					System.out.println(str);
					for (int i = 0; i < clients.size(); i++) {
						Client c = clients.get(i);
						c.send(str);
					}
				}
			} catch (Exception e) {
				System.out.println("关闭了一个连接");
			}finally {
				try {
					System.out.println("close All　！");
					if(dis != null)
						dis.close();
					if(dos != null)
						dos.close();
					if(s != null){
						s.close();
					}
				} catch (Exception e1) {
					
				}
			}
		}
		
	}
}
