package client;

import spread.SpreadException;
import spread.SpreadMessage;

public class ThreadBroadcast extends Thread {

	private Client client;
	public ThreadBroadcast(Client client) {
		this.client = client;
	}
	public void run() {
		while(true) {
			try {
				Thread.sleep(10000);
				SpreadMessage msg = new SpreadMessage();
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
