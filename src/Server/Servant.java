package Server;

import TasteProfile.ProfilerPOA;
import TasteProfile.Song;
import TasteProfile.TopThree;

public class Servant extends ProfilerPOA {

	public Servant() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getTimesPlayed(String song_id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getTimesPlayedByUser(String user_id, String song_id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TopThree getTopThreeUsersBySong(String song_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Song getFavouriteSongByUser(String user_id) {
		// TODO Auto-generated method stub
		return null;
	}

}
