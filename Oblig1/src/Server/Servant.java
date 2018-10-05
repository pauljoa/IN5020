package Server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import Cache.SongCache;
import Cache.UserCache;
import Implementations.SongImpl;
import Implementations.SongProfileImpl;
import Implementations.TopThreeImpl;
import Implementations.UserCounterImpl;
import Implementations.UserProfileImpl;
import TasteProfile.ProfilerPOA;
import TasteProfile.Song;
import TasteProfile.TopThree;
import TasteProfile.UserProfile;

public class Servant extends ProfilerPOA {

	private String filepath;
	private SongCache songCache;
	private UserCache userCache;
	private boolean cache;

	/**
	 * @param filepath Path to file with data
	 * @param cache boolean value to set if cache is to be used
	 */
	public Servant(String filepath, boolean cache) {
		this.filepath = filepath;
		if (cache) {
			initCache();
		}
		this.cache = cache;
		System.out.println("Init finished");
	}

	/**
	 * Initiating all caches used by the servant
	 */
	private void initCache() {
		this.songCache = new SongCache();
		this.userCache = new UserCache();
		
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filepath));
			String line = reader.readLine();
			String[] split = line.split("\t");
			
			ArrayList<SongImpl> currentSongs = new ArrayList<SongImpl>();
			String currentUser = split[0];
			int currentCount = Integer.parseInt(split[2]);
			String currentSong = split[1];
			int songCount = Integer.parseInt(split[2]);
			
			//Add song to list of songs
			SongImpl song = new SongImpl();
			song.init(currentSong, songCount);
			currentSongs.add(song);
			
			//Update SongCache
			UserCounterImpl[] userCounters = new UserCounterImpl[3];
			userCounters[0] = new UserCounterImpl();
			userCounters[0].setUserId(currentUser);
			userCounters[0].setSongIdPlayTime(songCount);
			TopThreeImpl ttl = new TopThreeImpl();
			ttl.setTopThreeUsers(userCounters);
			SongProfileImpl spi = new SongProfileImpl();
			spi.init(songCount, ttl);
			songCache.Put(currentSong, spi);
			
			UserProfileImpl profile = new UserProfileImpl();
			//init only userCache
			while ((line = reader.readLine()) != null) {
				split = line.split("\t");
				String nextUser = split[0];
				currentSong = split[1];
				songCount = Integer.parseInt(split[2]);

				if (!currentUser.equals(nextUser)) {
					profile.Init(currentUser, currentCount, currentSongs.toArray(new SongImpl[0]));
					userCache.Put(currentUser, profile);
					currentUser = nextUser;
					currentSongs = new ArrayList<SongImpl>();
					currentCount = 0;
					profile = new UserProfileImpl();					
				}
				
				song = new SongImpl();
				song.init(currentSong, songCount);
				currentSongs.add(song);
				currentCount += Integer.parseInt(split[2]);
				
				//Update SongCache
				spi = songCache.Get(currentSong);
				if(spi != null) {
					spi.addPlayCount(songCount);
					UserCounterImpl userCounter = new UserCounterImpl();
					userCounter.setUserId(currentUser);
					userCounter.setSongIdPlayTime(songCount);
					spi.updateTopThree(userCounter);
				} else {
					userCounters = new UserCounterImpl[3];
					userCounters[0] = new UserCounterImpl();
					userCounters[0].setUserId(currentUser);
					userCounters[0].setSongIdPlayTime(songCount);
					ttl = new TopThreeImpl();
					ttl.setTopThreeUsers(userCounters);
					spi = new SongProfileImpl();
					spi.init(songCount, ttl);
				}
				songCache.Put(currentSong, spi);
			}
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/* 
	 * @see TasteProfile.ProfilerOperations#getTimesPlayed(java.lang.String)
	 */
	@Override
	public int getTimesPlayed(String song_id) {
		if(cache) {
			SongProfileImpl spi = songCache.Get(song_id);
			if(spi != null) {
				return spi.total_play_count;
			}
			
			return 0;
		} else {
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
			return n;
		}
	}

	/* (non-Javadoc)
	 * @see TasteProfile.ProfilerOperations#getTimesPlayedByUser(java.lang.String, java.lang.String)
	 */
	@Override
	public int getTimesPlayedByUser(String user_id, String song_id) {		
		if(cache && userCache.Get(user_id) != null) {
			UserProfileImpl upi = userCache.Get(user_id);
			Song[] songs = upi.songs;
			for(int i = 0; i < songs.length; i++) {
				if(songs[i].id.equals(song_id)) {
					return songs[i].play_count;
				}
			}
			
			return 0;
		} else {
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
	
			return n;
		}
	}

	/* (non-Javadoc)
	 * @see TasteProfile.ProfilerOperations#getTopThreeUsersBySong(java.lang.String)
	 */
	@Override
	public TopThree getTopThreeUsersBySong(String song_id) {
		if(cache) {
			SongProfileImpl spi = songCache.Get(song_id);
			TopThreeImpl tt = new TopThreeImpl();
			if(spi != null) {
				tt = (TopThreeImpl) spi.top_three_users;
			} else {
				UserCounterImpl[] userCounter = new UserCounterImpl[3];
				tt.setTopThreeUsers(userCounter);
			}
			return tt;
		} else {
			String line = "";
			String firstKey, secondKey, thirdKey;
			firstKey = secondKey = thirdKey = "";
			int firstValue, secondValue, thirdValue;
			firstValue = secondValue = thirdValue = -1;
			try {
				BufferedReader reader = new BufferedReader(new FileReader(filepath));
				while ((line = reader.readLine()) != null) {
					String[] split = line.split("\t");
					int currentValue = Integer.parseInt(split[2]);
	
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
	
			TopThreeImpl tt = new TopThreeImpl();
			tt.setTopThreeUsers(userCounter);
			return tt;
		}
	}

	/* (non-Javadoc)
	 * @see TasteProfile.ProfilerOperations#getFavouriteSongByUser(java.lang.String)
	 */
	@Override
	public Song getFavouriteSongByUser(String user_id) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see TasteProfile.ProfilerOperations#getUserProfile(java.lang.String)
	 */
	@Override
	public UserProfile getUserProfile(String user_id) {
		if(cache && userCache.Get(user_id) != null) {
			return userCache.Get(user_id);
		} else {
			String line = "";
			boolean userFound = false;
			UserProfileImpl upl = new UserProfileImpl();
			int playCount = 0;
			ArrayList<SongImpl> songs = new ArrayList<SongImpl>();
			
			try {				
				BufferedReader reader = new BufferedReader(new FileReader(filepath));
				while ((line = reader.readLine()) != null) {
					String[] split = line.split("\t");
					if(split[0].equals(user_id)) {
						userFound = true;
						playCount += Integer.parseInt(split[2]);
						SongImpl si = new SongImpl();
						si.init(split[1], Integer.parseInt(split[2]));
						songs.add(si);
					} else if(userFound) {
						upl.Init(user_id, playCount, songs.toArray(new SongImpl[0]));
						break;
					}
				}
				reader.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return upl;
		}
	}
}
