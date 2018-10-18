package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import spread.AdvancedMessageListener;
import spread.MembershipInfo;
import spread.SpreadConnection;
import spread.SpreadException;
import spread.SpreadGroup;
import spread.SpreadMessage;

public class Client implements IClient, AdvancedMessageListener {
	public double balance = 0;
	public List<Transaction> executed_list = new ArrayList<Transaction>();
	public Collection<Transaction> outstanding_collection = new ArrayList<Transaction>();
	public int order_counter = 0;
	public int outstanding_counter = 0;
	public client.State state;
	public volatile int numberOfMembers = 0;
	public UUID privateName = UUID.randomUUID();
	public SpreadConnection connection;
	public SpreadGroup group;
	public int port = 8080;
	public List<SpreadGroup> groupMembers;
	ArrayList<String> toDoList = new ArrayList<String>();

	public Client(String[] args) {
		groupMembers = new ArrayList<SpreadGroup>();
		state = State.Connecting;
		System.out.println("Connecting");
		Connect(args);
		System.out.println("Connected");
		state = State.Running;
		showCommands();
		// Main loop, after connection has been made
		while (state != State.Exiting) {
			Input();
			Send();
		}
		Disconnect();
		System.out.println("Exiting");
		return;
	}

	@Override
	public double balance() {
		return balance;
	}

	@Override
	public void deposit(double amount) {
		balance += amount;
	}

	@Override
	public void addInterest(double percent) {
		balance = balance * (1 + (percent / 100));
	}

	public List<Transaction> getExecutedList() {
		return executed_list;
	}

	public Collection<Transaction> getOutstandingCollection() {
		return outstanding_collection;
	}

	@Override
	public List<Transaction> getHistory() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void cleanHistory() {
		executed_list.clear();
	}

	@Override
	public List<SpreadGroup> memberInfo() {
		return groupMembers;
	}

	@Override
	public void sleep(int duration) {
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void exit() {
		state = State.Exiting;
	}

	@Override
	public void addTransaction(Transaction transaction) {
		outstanding_collection.add(transaction);
		outstanding_counter++;
	}

	@Override
	public void processTransactions(List<Transaction> transactions) {
		for (Transaction t : transactions) {
			// Command already executed, ignore duplicate
			if (executed_list.contains(t)) {

			}
			// Execute command
			else {
				String cmd = t.command.split("\\s+")[0];
				String param = t.command.split("\\s+")[1];
				switch (cmd) {
				case "deposit":
					deposit(Double.parseDouble(param));
					if (outstanding_collection.remove(t)) {
						// Was a local command, removed from the outstanding_collection
					}
					executed_list.add(t);
					order_counter++;
					break;
				case "addinterest":
					addInterest(Double.parseDouble(param));
					if (outstanding_collection.remove(t)) {
						// Was a local command, removed from the outstanding_collection
					}
					executed_list.add(t);
					order_counter++;
					break;
				}

			}
		}

	}

	@Override
	public void regularMessageReceived(SpreadMessage message) {
		String val = new String(message.getData());
		List<Transaction> tempList = new ArrayList<Transaction>();
		String[] values = val.split(",");
		String[] state = val.split(" ");
        if (state[0].equals("State")) {
            this.balance = Double.parseDouble(state[1]);
        } 
        else {
			for (String e : values) {
				String[] split = e.split(" ");
				Transaction temp = new Transaction(split[0] + " " + split[1], split[2]);
				if (!executed_list.contains(temp)) {
					tempList.add(temp);
				}
			}
        }
		processTransactions(tempList);
	}

	@Override
	public void membershipMessageReceived(SpreadMessage message) {
		MembershipInfo info = message.getMembershipInfo();
		int length = info.getMembers().length;
		// adds the new member to the group, including this replica
		if (info.isCausedByJoin()) {
			SpreadGroup joined = info.getJoined();
			groupMembers.add(joined);
			// Check if the joined member is itself, TODO: Remove ! for testing purposes
			if (connection.getPrivateGroup().equals(joined)) {
				SpreadGroup[] members = info.getMembers();
				for(int i = 0;i<members.length;i++) {
					if(groupMembers.contains(members[i])) {
						//Do not add member again
					}
					else {
						groupMembers.add(members[i]);
					}
				}
				// Do nothing, as a state update will be sent by the other replicas (If
				// available)
			} else {
				// Client has executed transactions, send the state to the joined member
				if (order_counter != 0) {
					SpreadMessage msg = new SpreadMessage();
					byte[] data = new String("State " + Double.toString(balance)).getBytes();
					msg.setData(data);
					msg.setSafe();
					msg.addGroup(joined);
					try {
						connection.multicast(msg);
					} catch (SpreadException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} else if (info.isCausedByLeave() || info.isCausedByDisconnect()) {
			SpreadGroup left = info.getLeft();
			groupMembers.remove(left);
		}
		this.numberOfMembers = length;
	}

	@Override
	public void Connect(String[] args) {
		// TODO Auto-generated method stub
		try {
			InetAddress tmp = InetAddress.getByName(args[0]);
			connection = new SpreadConnection();
			connection.connect(InetAddress.getByName(args[0]), port, privateName.toString(), false, true);
			connection.add(this);
			group = new SpreadGroup();
			group.join(connection, args[1]);
			int nReplicas = Integer.parseInt(args[2]);
			// Do nothing before start
			while (numberOfMembers < nReplicas)
				;
		} catch (SpreadException se) {
			System.out.println("Error on Spread connection: " + se.getMessage());
			se.printStackTrace(System.out);
		} catch (UnknownHostException uhe) {
			System.out.println("Error on group join: " + uhe.getMessage());
			uhe.printStackTrace(System.out);
		} catch (NumberFormatException nfe) {
			System.out.println("Number of replicas from args is not an Integer: " + nfe.getMessage());
			nfe.printStackTrace(System.out);
		}
	}

	@Override
	public void Disconnect() {
		try {
			connection.remove(this);
			connection.disconnect();
		} catch (SpreadException e) {
			e.printStackTrace();
		}
		System.out.println("Disconnection complete");

	}

	@Override
	public void Input() {
		InputStreamReader fileInputStream = new InputStreamReader(System.in);
		BufferedReader bufferedReader = new BufferedReader(fileInputStream);
		int time = (int) System.currentTimeMillis() + 10000; // set to + 10 seconds
		try {
			while ((int) System.currentTimeMillis() < time) {
				if (bufferedReader.ready()) {
					String input = bufferedReader.readLine().toLowerCase();
					if (input.contains("deposit") || input.contains("addinterest")) {
						String[] split = input.split(" ");
						if (split.length == 2 && (split[0].equals("deposit") || split[0].equals("addinterest"))
								&& isNumber(split[1]))
							addTransaction(new Transaction(input, privateName.toString() + outstanding_counter));
					} else {
						localCommands(input);
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean isNumber(String string) {
		boolean check = true;
		for (char c : string.toCharArray()) {
			if (!Character.isDigit(c) && c != '-' && c != '.')
				check = false;
		}
		return check;
	}
	public void showCommands() {
		System.out.println("AVAILABLE COMMANDS:");
		System.out.println("deposit <number>");
		System.out.println("addinterest <number>");
		System.out.println("balance");
		System.out.println("gethistory");
		System.out.println("cleanhistory");
		System.out.println("memberinfo");
		System.out.println("sleep <seconds>");
		System.out.println("exit");
	}
	private void localCommands(String input) {
		if (input.equals("balance"))
			System.out.println(balance());
		else if (input.equals("gethistory")) {
			System.out.println("Executed list:");
			int counter = order_counter - getExecutedList().size();
			for (Transaction trans : getExecutedList()) {
				System.out.println(counter + ". " +trans.getCommand());
			}
			System.out.println("Outstanding list:");
			for (Transaction trans : getOutstandingCollection()) {
				System.out.println(trans.getCommand());
			}
		} else if (input.equals("cleanhistory"))
			cleanHistory();
		else if (input.equals("memberinfo")) {
			System.out.println("Members of this group");
			for (SpreadGroup group : memberInfo()) {
				System.out.println(group.toString());
			}
		} else if (input.contains("sleep")) {
			String[] split = input.split(" ");
			sleep(Integer.parseInt(split[1]) * 1000);
		} else if (input.equals("exit"))
			exit();
		else
			System.out.println("Command not valid");
	}

	@Override
	public void Send() {
		String data = "";
		SpreadMessage msg = new SpreadMessage();
		msg.setServiceType(0x00000020);
		msg.addGroup(this.group);
		try {
			for (Transaction e : outstanding_collection) {
				data = data + e.toString() + ",";
			}
			msg.setData(data.getBytes());
			if (!data.isEmpty()) // for test
				connection.multicast(msg);
		} catch (SpreadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
