package com.wbq.Thread;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.plaf.synth.SynthViewportUI;

public class ClientOutputThread extends Thread{
	private Socket socket;
	private DataOutputStream dos;
	private boolean isStart = true;
	private String msg;
	
	
	public ClientOutputThread(Socket socket) {
		super();
		this.socket = socket;
		try {
			dos = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setStart(boolean isStart){
		this.isStart = isStart;
	}
	
	//这里处理跟服务器是一样的
	public void sendMsg(String msg){
		this.msg = msg;
		synchronized (this) {
			notifyAll();
		}
	}
	
	@Override
	public void run() {
		super.run();
		try {
			while (isStart) {
				if (msg != null) {
					dos.writeUTF(msg);
					dos.flush();
					msg = null;
					synchronized (this) {
						wait();//发送完消息后，线程进入等待状态
					}
				}
			}
			dos.close();//循环结束后，关闭输出流和socket
			if (socket != null) {
				socket.close();
			}
		} catch (Exception e) {
		
		}
	}
}
