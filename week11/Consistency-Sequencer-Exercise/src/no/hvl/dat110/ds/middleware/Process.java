package no.hvl.dat110.ds.middleware;


/**
 * @author tdoy
 * For demo/teaching purpose at dat110 class
 */

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import no.hvl.dat110.ds.middleware.iface.OperationType;
import no.hvl.dat110.ds.middleware.iface.ProcessInterface;
import no.hvl.dat110.ds.util.Util;

public class Process extends UnicastRemoteObject implements ProcessInterface {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<Message> queue;					// queue for this process
	private int processID;							// id of this process
	private double balance = 1000;					// default balance (each replica has the same). Our goal is to keep the balance consistent
	private Map<String, Integer> replicas;			// list of other processes including self known to this process 
	
	protected Process(int id) throws RemoteException {
		super();
		processID = id;
		queue = new ArrayList<Message>();	
		replicas = Util.getProcessReplicas();
	}
	
	private void updateDeposit(double amount) throws RemoteException {

		balance += amount;
	}
	
	private void updateInterest(double interest) throws RemoteException {

		double intvalue = balance*interest;
		balance += intvalue;
	}
	
	private void updateWithdrawal(double amount) throws RemoteException {

		balance -= amount;
	}
	
	
	private void sortQueue() {
		// sort the queue by the clock (unique time stamped given by the sequencer)
		Comparator <Message> clock = Comparator.comparing(Message::getClock);
//		Comparator <Message> process = Comparator.comparing(Message::getProcessID);
//		Comparator <Message> both = clock.thenComparing(process);
		
		Collections.sort(queue, clock);
	}
	
	// client initiated method
	@Override
	public void requestInterest(double interest) throws RemoteException {
		// TODO 		
		// make a new message instance and set the following:
		// set the type of message - interest
		// set the process ID
		// set the interest
		Message message = new Message();	
		message.setOptype(OperationType.INTEREST);
		message.setProcessID(processID);
		message.setInterest(interest); 
		
		sendMessageToSequencer(message);

		// send the message to the sequencer by calling the sendMessageToSequencer
		

	}
	
	// client initiated method
	@Override
	public void requestDeposit(double amount) throws RemoteException {
		// TODO 		
		// make a new message instance and set the following
		// set the type of message - deposit
		// set the process ID
		// set the deposit amount
		Message mess = new Message();
		mess.setOptype(OperationType.DEPOSIT);
		mess.setProcessID(processID);
		mess.setDepositamount(amount);
		
		sendMessageToSequencer(mess);

		// send the message to the sequencer

	}
	
	// client initiated method
	@Override
	public void requestWithdrawal(double amount) throws RemoteException {
		// TODO 		
		// make a new message instance and set the following
		// set the type of message - withdrawal
		// set the process ID
		// set the withdrawal amount
		Message mess = new Message();
		mess.setOptype(OperationType.WITHDRAWAL);
		mess.setProcessID(processID);
		mess.setWithdrawamount(amount);
		
		sendMessageToSequencer(mess);

		// send the message to the sequencer

	}
	
	private void sendMessageToSequencer(Message message) throws RemoteException {
		// TODO
		
		// get the port for the sequencer: use Util class
		// get the sequencer stub: use Util class
		// using the sequencer stub, call the remote onMessageReceived method to send the message to the sequencer
/**
		replicas.forEach((name, port) -> {
			ProcessInterface pi = Util.getProcessStub(name, port);
			
			try {
				pi.onMessageReceived(message);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
			
		});
		**/
	}
	
		
	public void applyOperation() throws RemoteException {
		// TODO
		
		// iterate over the queue
		
		// for each message in the queue, check the operation type
		
		// call the appropriate update method for the operation type and pass the value to be updated
		for (Message m : queue) {

			if(m.getOptype().equals(OperationType.DEPOSIT)) 
				updateDeposit(m.getDepositamount());
			else if(m.getOptype().equals(OperationType.INTEREST)) 
				updateInterest(m.getInterest());
			else
				updateWithdrawal(m.getWithdrawamount());
		}
		Util.printClock(this);
		
	}
	
	@Override
	public void onMessageReceived(Message message) throws RemoteException {
		// TODO
		// upon receipt of a message, add message to the queue	
		// check the ordering limit, if equal to queue size, start to process the following:
			// sort the queue according to time stamped by the sequencer
			// apply operation and commit
			// clear the queue
		queue.add(message);
		if(queue.size() <= Sequencer.ORDERINGLIMIT) {
			sortQueue();
			applyOperation();
			queue.clear();
			
		}
		

	}
	
	@Override
	public double getBalance() throws RemoteException {
		return balance;
	}
	
	@Override
	public int getProcessID() throws RemoteException {
		return processID;
	}
	
	@Override
	public List<Message> getQueue() throws RemoteException {
		return queue;
	}
	
}
