package no.hvl.dat110.util;


/**
 * @author tdoy
 * dat110 - DSLab 2
 */

import java.math.BigInteger;
import java.rmi.RemoteException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.platform.engine.discovery.FileSelector;

import no.hvl.dat110.rpc.interfaces.NodeInterface;

public class FileManager {
	
	public static BigInteger[] createReplicaFiles(String filename, int nreplicas) throws NoSuchAlgorithmException {
		
		// Task:given a filename, create nreplicas (1 to nreplicas)- idea, append each index to the filename before hash
		// hash the replica using the Hash.hashOf() and store it in an array
		BigInteger[] list = new BigInteger[nreplicas];
		for (int i = 0; i < nreplicas; i++) {
			String file = filename + i;
			list[i] = Hash.hashOf(file);
		}
		// return the replicas as array of BigInteger
		return list;
	}
	
    /**
     * 
     * @throws RemoteException 
     * @throws NoSuchAlgorithmException 
     */
    public void distributeReplicastoPeers(String filename) throws RemoteException, NoSuchAlgorithmException {
    	
    	// Task: Given a filename, make replicas and distribute them to all active peers such that: pred < replica <= peer
    	
    	// create replicas of the filename
    	BigInteger [] files = createReplicaFiles(filename, Util.numReplicas);
		// collect the 5 processes from the Util class	
		Map <String, Integer> processes = Util.getProcesses();
    	// iterate over the processes
		// iterate over the replicas
    	processes.keySet().forEach(name -> {
    		int port = processes.get(name);
    		NodeInterface node = Util.getProcessStub(name, port);
    		
    		try {
    			BigInteger nodeID = node.getNodeID();
    			BigInteger predID = node.getPredecessor().getNodeID();
    			
    			for(int i = 0; i < files.length; i++) {
    				BigInteger fileID = (BigInteger) files[i];
    				
    				boolean isServer = Util.checkInterval(fileID, predID.add(BigInteger.ONE), nodeID);
    				// for each replica, add the replica to the peer if the condition: pred < replica <= peer is satisfied	(i.e., use Util.checkInterval)
    				if (isServer)
    					node.addKey(fileID);
    			}
    			
    			
    		} catch (RemoteException e) {
    			
    		}
    	});
    	
    	
    	
		
    }
	
	/**
	 * 
	 * @param filename
	 * @return list of active nodes having the replicas of this file
	 * @throws RemoteException 
	 * @throws NoSuchAlgorithmException 
	 */
	public Set<NodeInterface> requestActiveNodesForFile(String filename) throws RemoteException, NoSuchAlgorithmException {
		
		// Task: Given a filename, find all the peers that hold a copy of this file
		
		// see the distributeReplicastoPeers(filename): same rules.
		BigInteger [] files = createReplicaFiles(filename, Util.numReplicas);
		
		Set<NodeInterface> peers = new HashSet<>(); 							
		
		Map <String, Integer> processes = Util.getProcesses();
		
		processes.keySet().forEach(name -> {
			int port = processes.get(name);
			NodeInterface node = Util.getProcessStub(name, port);
			
			try {
				for (int i = 0; i < files.length; i++) {
					BigInteger fileID = (BigInteger) files[i];
					
					if(node.getNodeKeys().contains(fileID))
						peers.add(node);
				}
			} catch (RemoteException e) {
				
			}
		});
		
		return peers;
	}

}
