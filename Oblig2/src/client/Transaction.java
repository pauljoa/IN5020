package client;

public class Transaction {
	public String command;
	public String unique_id;
	public Transaction(String c, String id) {
		command = c;
		unique_id = id;
	}
	@Override
	public boolean equals(Object arg0) {
		if(unique_id.equals(((Transaction) arg0).unique_id)) {
			return true;
		}
		return false;
		
	}
	public String toString() {
		return command +unique_id;
	}
}
