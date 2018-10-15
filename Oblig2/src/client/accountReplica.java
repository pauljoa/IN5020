package client;

public class accountReplica {
	public static void main(String[] args) {
		Client client = new Client(args);
		ThreadBroadcast cast = new ThreadBroadcast(client);
		cast.start();
		ThreadScanner scan = new ThreadScanner(client);
		scan.start();
		while(client.state != State.Exiting) {
		}
		System.exit(0);
	}
}
