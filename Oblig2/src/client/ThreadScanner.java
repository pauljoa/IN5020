package client;

import java.util.Scanner;

import spread.SpreadException;
import spread.SpreadMessage;

public class ThreadScanner extends Thread {

	private Client client;
	
	public ThreadScanner(Client client) {
		this.client = client;
	}
	public void run() {
		Scanner scanner = new Scanner(System.in);
		String input;
		while(true) {
			input = scanner.nextLine();
			client.addTransaction(new Transaction("test", "testete"));
		}
	}
}
