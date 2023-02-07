package no.hvl.dat110.simulation;

import no.hvl.dat110.rmiclient.ComputeClient;
import no.hvl.dat110.rmiserver.ComputeServer;

public class SimulationSystem {

	public static void main(String[] args) throws InterruptedException {
		
		System.out.println("System starting ... ");
		
		// start the rpc server
		ComputeServer server = new ComputeServer();
		server.start();												// start the rpc server
		
		// start multiple clients
		ComputeClient c1 = new ComputeClient();
		ComputeClient c2 = new ComputeClient();
		ComputeClient c3 = new ComputeClient();
		
		c1.start();
		c2.start();
		c3.start();
		
		c1.join();
		c2.join();
		c3.join();
		
		System.out.println("System shutting ... ");	
		System.exit(0);
		
	}
}
