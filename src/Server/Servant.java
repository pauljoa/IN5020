package Server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import Implementations.TopThreeImpl;
import Implementations.UserCounterImpl;
import TasteProfile.ProfilerPOA;
import TasteProfile.Song;
import TasteProfile.TopThree;

public class Servant extends ProfilerPOA {

	private int topSong;
	private int topSongByUser;
	private HashMap<String, Integer> songRecord;
	private HashMap<String, Integer> userRecord;

	public Servant() {
		userRecord = new HashMap<>();
		songRecord = new HashMap<>();
	}

	@Override
	public int getTimesPlayed(String song_id) {
		System.out.println("getTimesPlayed - " + song_id);
		return songRecord.get(song_id);
	}

	@Override
	public int getTimesPlayedByUser(String user_id, String song_id) {
		System.out.println("getTimesPlayedByUser - " + user_id + " - " + song_id);
		return userRecord.get(user_id + ";" + song_id);
	}

	@Override
	public TopThree getTopThreeUsersBySong(String song_id) {
		String firstKey, secondKey, thirdKey;
		firstKey= secondKey= thirdKey = "";
		int firstValue, secondValue, thirdValue;
		firstValue= secondValue= thirdValue = -1;
		for (Map.Entry<String, Integer> mapEntry : userRecord.entrySet()) {
			String[] split = mapEntry.getKey().split(";");
			if (split[1].equals(song_id)) {
				if (mapEntry.getValue() > firstValue) {
					thirdValue = secondValue;
					secondValue = firstValue;
					firstValue = mapEntry.getValue();
					firstKey = split[0];
				} else if (mapEntry.getValue() > secondValue) {
					thirdValue = secondValue;
					secondValue = mapEntry.getValue();
					secondKey = split[0];
				} else if (mapEntry.getValue() > thirdValue) {
					thirdValue = mapEntry.getValue();
					thirdKey = split[0];
				}
			}
		}
		UserCounterImpl[] userCounter = new UserCounterImpl[3];
		userCounter[0] = new UserCounterImpl(firstKey, firstValue);
		userCounter[1] = new UserCounterImpl(secondKey, secondValue);
		userCounter[2] = new UserCounterImpl(thirdKey, thirdValue);

		System.out.println("getTopThreeUsersBySong - " + song_id);
		return new TopThreeImpl(userCounter) {
		};
	}

	@Override
	public Song getFavouriteSongByUser(String user_id) {
		// TODO Auto-generated method stub
		return null;
	}

	public void addData(HashMap<String, Integer> record, String key, int count) {
		if (record.containsKey(key)) {
			record.put(key, record.get(key) + count);
		} else {
			record.put(key, count);
		}
	}

	public void readInputFile() {
		String filepath = "";
		String line = "";
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filepath));
			while ((line = reader.readLine()) != null) {
				String[] split = line.split("\t");

				String user = split[0];
				String song = split[1];
				int count = Integer.parseInt(split[2]);

				addData(songRecord, song, count);
				addData(userRecord, user + ";" + song, count);

				reader.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
