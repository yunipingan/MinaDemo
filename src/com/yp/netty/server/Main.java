package com.yp.netty.server;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			new TimeServer().bind(8081);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
