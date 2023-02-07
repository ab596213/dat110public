package no.hvl.dat110.rmiserver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import no.hvl.dat110.rmiinterface.ClientCallbackInterface;
import no.hvl.dat110.rmiinterface.ServerCallbackInterface;

/**
 * dat110: DS-Lab 2
 * @author tdoy
 *
 */
public class ServerCallbackImplement extends UnicastRemoteObject implements ServerCallbackInterface {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private ClientCallbackInterface clientcallbackobj;

	protected ServerCallbackImplement() throws RemoteException {
		super();
	}

	@Override
	public void registerClientCallbackObject(ClientCallbackInterface clientcallbackobj) throws RemoteException {
		
		this.clientcallbackobj = clientcallbackobj;
		
	}
	
	public synchronized void doOperation(int a, int b) throws RemoteException {
		
		Thread thread = new Thread(new Runnable ()  {
			
			@Override
			public void run() {
				try {
					Thread.sleep(500);
				} catch (Exception e) {
					
				}
				int result = a+b;
				
				try {
					clientcallbackobj.notify(String.valueOf(result));
				} catch (RemoteException e){
					e.printStackTrace();
				}
			}
		});
		
		thread.start();
	}

	@Override
	public void shutdown() throws RemoteException {
		
		System.out.println("RPC Server will shut down in 2 sec...");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(0);
		
	}

}
