package no.hvl.dat110.rmiserver;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.paukov.combinatorics3.Generator;

import no.hvl.dat110.crack.PasswordUtility;
import no.hvl.dat110.rmiinterface.PassCrackInterface;
import no.hvl.dat110.rmiinterface.WorkerCallbackInterface;
import no.hvl.dat110.workernodes.Utility;

/**
 * For demonstration purpose in dat110 course
 */

public class PassCrackImpl extends UnicastRemoteObject implements PassCrackInterface{

	private static final long serialVersionUID = 1L;
	
	private WorkerCallbackInterface workercallback;

	public PassCrackImpl() throws RemoteException {
		super();
	}

	@Override
	public void crackPassword(int keylength, String hashtocrack, String workername) throws RemoteException {
		
		workercallback.acknowledge(workername);
		// hand this job to a new thread
		Runnable task = () -> {
			try {
				// start to crack - this is a compute intensive task
				System.out.println(workername +" is starting to crack password...");
				String password = "";
				long start = System.currentTimeMillis();
				
				// TODO
				// call the bruteforce method that performs the actual search and pass the result into password
				password = bruteforce(Utility.getKeyspace(), keylength, hashtocrack);
				
				long end = System.currentTimeMillis();
				long diff = end - start;
				
				if(!password.equals("")) {
					// TODO
					// call the foundPassword on workercallback to notify coordinator of the found password
					workercallback.foundPassword(password, diff, workername);
					// call shutdown on other workers
					sendShutdownMessage(workername);
					
				}
				
				shutdownWorker(); 				// call shutdown on self
			} catch (RemoteException | NoSuchAlgorithmException n) {
				n.printStackTrace();
			}			
		};
		
		Thread thread = new Thread(task);
		thread.start();
	}

	/**
	 * Permutation with repetition
	 * key = #alphabets^lengthOfKey
	 * @param keyspace
	 * @param keylength
	 * @param hashtocrack
	 * @throws NoSuchAlgorithmException 
	 */
	public String bruteforce(String[] keyspace, int keylength, String hashtocrack) throws NoSuchAlgorithmException{

		Iterator<List<String>> keys = Generator.permutation(keyspace).withRepetitions(keylength).iterator();
		
		while(keys.hasNext()) {
			List<String> key = keys.next();
			
			String skey = "";
			for(int i=0; i<key.size(); i++) {
				skey += key.get(i);
			}

			boolean found = PasswordUtility.verifyHash(skey, hashtocrack);
			if(found) {
				return skey;
				
			}
		}

		return "null";

	}
	
	@Override
	public void shutdownWorker() throws RemoteException {
		
		System.out.println("worker node will shut down in 1 sec...");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.exit(0);
	}

	@Override
	public void registerWorkerCallbackObject(WorkerCallbackInterface workercallback) throws RemoteException {
		
		this.workercallback = workercallback;
	}

	private void sendShutdownMessage(String caller) {
		
		Map<String, Integer> workers = Utility.getWorkers();
		Iterator<String> workernodes = workers.keySet().iterator();
		while(workernodes.hasNext()) {
			String workername = workernodes.next();
			if(workername.equals(caller))
				continue;
			int workerport = workers.get(workername);
			try {
				PassCrackInterface worker = Utility.getWorkerstub(workername, workerport);
				if(worker != null)
					worker.shutdownWorker();
			} catch (Exception e) {
				//e.printStackTrace();
			}
		}

	}

}
