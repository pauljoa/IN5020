package Server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import Implementations.TopThreeImpl;
import Implementations.UserCounterImpl;
import TasteProfile.ProfilerPOA;
import TasteProfile.Song;
import TasteProfile.TopThree;

public class Servant extends ProfilerPOA {
	
	private String filepath;

	public Servant(String filepath) {
		this.filepath = filepath;
	}

	@Override
	public int getTimesPlayed(String song_id) {
		String line = "";
		int n = 0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filepath));
			while ((line = reader.readLine()) != null) {
				String[] split = line.split("\t");
				if (split[1].equals(song_id)) {
					n += Integer.parseInt(split[2]);
				}
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("getTimesPlayed - " + song_id);
		return n;
	}

	@Override
	public int getTimesPlayedByUser(String user_id, String song_id) {
		String line = "";
		int n = 0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filepath));
			while ((line = reader.readLine()) != null) {
				String[] split = line.split("\t");
				if (split[1].equals(song_id) && split[0].equals(user_id)) {
					n = Integer.parseInt(split[2]);
					break;
				}
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("getTimesPlayedByUser - " + song_id + " - " + user_id);
		return n;
	}

	@Override
	public TopThree getTopThreeUsersBySong(String song_id) {
		String line = "";
		String firstKey, secondKey, thirdKey;
		firstKey= secondKey= thirdKey = "";
		int firstValue, secondValue, thirdValue;
		firstValue= secondValue= thirdValue = -1;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filepath));
			while ((line = reader.readLine()) != null) {
				String[] split = line.split("\t");
				int currentValue =  Integer.parseInt(split[2]);

				if (split[1].equals(song_id)) {
					if (currentValue > firstValue) {
						thirdValue = secondValue;
						thirdKey = secondKey;
						secondValue = firstValue;
						secondKey = firstKey;
						firstValue = currentValue;
						firstKey = split[0];
					} else if (currentValue > secondValue) {
						thirdValue = secondValue;
						thirdKey = secondKey;
						secondValue = currentValue;
						secondKey = split[0];
					} else if (currentValue > thirdValue) {
						thirdValue = currentValue;
						thirdKey = split[0];
					}
				}
			}
			reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		UserCounterImpl[] userCounter = new UserCounterImpl[3];
		userCounter[0] = new UserCounterImpl();
		userCounter[0].setUserId(firstKey);
		userCounter[0].setSongIdPlayTime(firstValue);
		
		userCounter[1] = new UserCounterImpl();
		userCounter[1].setUserId(secondKey);
		userCounter[1].setSongIdPlayTime(secondValue);
		
		userCounter[2] = new UserCounterImpl();
		userCounter[2].setUserId(thirdKey);
		userCounter[2].setSongIdPlayTime(thirdValue);

		System.out.println("getTopThreeUsersBySong - " + song_id);
		TopThreeImpl tt = new TopThreeImpl();
		tt.setTopThreeUsers(userCounter);
		return tt;
	}

	@Override
	public Song getFavouriteSongByUser(String user_id) {
		// TODO Auto-generated method stub
		return null;
	}
}
