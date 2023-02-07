package no.hvl.dat110.display;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import no.hvl.dat110.rpcinterface.DisplayCallbackInterface;

public class DisplayCallBackImpl extends UnicastRemoteObject implements DisplayCallbackInterface {

	private static final long serialVersionUID = 1L;
	
	protected DisplayCallBackImpl () throws RemoteException {
		super();
	}
	
	@Override
	public void notifyTemp(String temp) throws RemoteException {
		System.out.println("Display: " + temp);
	}
	
	
	@Override
	public boolean isExit() throws RemoteException {
		return false;
	}

	@Override
	public void setExit(boolean exit) throws RemoteException {
		
	}
}
