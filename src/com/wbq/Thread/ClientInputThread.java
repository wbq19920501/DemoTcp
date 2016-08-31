package com.wbq.Thread;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Logger;

import com.wbq.interfacr.MessageListener;

public class ClientInputThread extends Thread{
	private Socket socket;
	private String msg;
	private boolean isStart = true;
	private InputStream ois;
	private DataInputStream dis;
	private MessageListener messageListener;// 消息监听接口对象  
	public ClientInputThread(Socket socket) {
		super();
		this.socket = socket;
		try {
			ois = socket.getInputStream();
			dis = new DataInputStream(new BufferedInputStream(ois));
		} catch (Exception e) {
			
		}
	}
	
	/**
	 *  提供给外部的消息监听方法
	 * @param messageListener
	 */
	public void setMessageListener(MessageListener messageListener) {
		this.messageListener = messageListener;
	}

	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}
	
	@Override
	public void run() {
		super.run();
		try {
			while (isStart) {
				//读取信息，如果没信心将会阻塞线程
				msg = dis.readUTF();
				//每收到一条消息，就调用接口的方法Message(String msg)
				System.out.println(msg+"----");
				messageListener.Message(msg);
			}
			ois.close();
			if (socket != null) {
				socket.close();
			}
		} catch (Exception e) {
			
		}
	}
	
	BufferedReader reader = null;
	public String getInputStreamString(){
		if (ois != null) {
			reader = new BufferedReader(new InputStreamReader(ois));
		}
		StringBuffer sb = new StringBuffer();
		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
		} catch (Exception e) {

		}
		return sb.toString();
	}
	
}
