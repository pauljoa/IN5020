package client;

public class Transaction {
	public String command;
	public String unique_id;
	public Transaction(String c, String id) {
		command = c;
		unique_id = id;
	}
	public String toString() {
		return command +"."+unique_id;
	}
	public String getCommand() {
		return command;
	}
}
