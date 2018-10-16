package client;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import spread.SpreadException;
import spread.SpreadMessage;

public class ThreadScanner extends Thread {

	private Client client;
	
	public ThreadScanner(Client client) {
		this.client = client;
	}
	public void run() {
	
	}
}
