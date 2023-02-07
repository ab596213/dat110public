package no.hvl.dat110.rmiinterface;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * Lab0 - DS dat110 course.
 * A server defines a common interface where all remote methods that should be available to clients are defined.
 * This interface is made available (exposed) to the client and the server.
 * @author tdoy Tosin D. Oyetoyan
 */

public interface ComputeInterface extends Remote {
	
	public int addNumbers(int a, int b) throws RemoteException;

	public void stopServer() throws RemoteException;
}
