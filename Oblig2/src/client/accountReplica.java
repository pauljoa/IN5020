package client;

public class accountReplica {
	public static void main(String[] args) {
		Client client = new Client(args);
		if(client.state == State.Exiting) {
			System.exit(0);
		}
	}

}
