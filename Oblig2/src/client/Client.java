package client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import spread.AdvancedMessageListener;
import spread.MembershipInfo;
import spread.SpreadConnection;
import spread.SpreadMessage;

public class Client implements IClient, AdvancedMessageListener {
	public double balance = 0;
	public List<Transaction> executed_list = new ArrayList<Transaction>();
	public Collection<Transaction> outstanding_collection = new ArrayList<Transaction>();
	public int order_counter = 0;
	public int outstanding_counter = 0;
	public State state;
	public int numberOfMembers = 0;
	public UUID privateName = UUID.randomUUID();
	
	public SpreadConnection connection;

	public Client(String[] args) {
		state = State.Connecting;
		Connect(args);
		state = State.Running;
		//Main loop, after connection has been made
		while(state != State.Exiting) {
			
		}
	}

	@Override
	public double balance() {
		return balance;
	}

	@Override
	public void deposit(int amount) {
		balance += amount;
		
	}

	@Override
	public void addInterest(double percent) {
		balance = balance*(1+(percent/100));
		
	}

	@Override
	public List<Transaction> getHistory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void cleanHistory() {
		executed_list.clear();
	}

	@Override
	public List<String> memberInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sleep(int duration) {
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void exit() {
		state = State.Exiting;
		System.exit(0);
	}

	@Override
	public void addTransaction(Transaction transaction) {
		outstanding_collection.add(transaction);
		outstanding_counter++;
	}

	@Override
	public void processTransactions(List<Transaction> transactions) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void regularMessageReceived(SpreadMessage message) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void membershipMessageReceived(SpreadMessage message) {
		MembershipInfo info = message.getMembershipInfo();
		int length = info.getMembers().length;
		this.numberOfMembers = length;
	}

	@Override
	public void Connect(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
