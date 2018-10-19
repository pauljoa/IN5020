package client;

public class accountReplica {
	public static void main(String[] args) {
		Client client = new Client(args);
		while(client.state != State.Exiting) {
		}
		System.exit(0);
	}
}
