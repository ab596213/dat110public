package no.hvl.dat110.tcpexample.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import no.hvl.dat110.tcpexample.system.Configuration;

public class EchoServer {
	
	public static void main(String[] args) {
		
		int serverport = Configuration.SERVERPORT;
		
		if (args.length > 0) {			
			
			serverport = Integer.parseInt(args[0]);
		}
		
		System.out.println("TCP server starting # " + serverport);
		
		try (ServerSocket welcomeSocket = new ServerSocket(serverport)) {
				
			TCPEchoServer server = new TCPEchoServer(welcomeSocket);

			while (true) {
				
				server.process();
				
			}
			
		} catch (IOException ex) {
			System.out.println("TCP server: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}




/*
public class EchoServer {
	
	public static void main(String[] args) {
		
		ServerSocket welcomeSocket = null; 
				
		int serverport = Configuration.SERVERPORT;
		
		if (args.length > 0) {			
			
			serverport = Integer.parseInt(args[0]);
		}
		
		try {
			welcomeSocket = new ServerSocket(serverport);
			System.out.println("server listening");
			int i = 1;
			

			while (true) {
				System.out.println("TCP server starting # " + serverport);
				Runnable r = new TCPEchoServer(welcomeSocket);
				Thread t = new Thread(r);
				t.start();
				System.out.println("Starting thread number:" + i);
				i++;
				
			}
			
		} catch (IOException ex) {
			System.out.println("TCP server: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}
*/