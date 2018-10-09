package client;

import java.util.List;

public interface IClient {
	
	public double balance();
	public void deposit(int amount);
	public void addInterest(double percent);
	public List<Transaction> getHistory();
	public void cleanHistory();
	public List<String> memberInfo();
	public void sleep(int duration);
	public void exit();
	
	public void addTransaction(Transaction transaction);
	public void processTransactions(List<Transaction> transactions);
	
	
	//Utility functions
	public void Connect(String[] args);
	public void Disconnect();
	
}
