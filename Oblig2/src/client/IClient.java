package client;

import java.util.List;

public interface IClient {
	
	public int balance();
	public void deposit(int amount);
	public void addIntersest(int percent);
	public List<Transaction> getHistory();
	public void cleanHistory();
	public List<String> memberInfo();
	public void sleep(int duration);
	public void exit();
	
	public void addTransaction(Transaction transaction);
	public void processTransactions(List<Transaction> transactions);
}
