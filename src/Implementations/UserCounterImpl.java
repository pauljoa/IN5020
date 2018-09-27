package Implementations;

import TasteProfile.UserCounter;

/**
 * The implementation of the abstract UserCounter class
 *
 */
public class UserCounterImpl extends UserCounter {

	/**
	 * Initial UserCounter variables
	 */
	public UserCounterImpl() {
		user_id = "";
		songid_play_time = 0;
	}
	
	/**
	 * @param user the id of the user
	 */
	public void setUserId(String user) {
		user_id = user;
	}
	
	/**
	 * @param playtime Integer, sets the number of times the user has played the song
	 */
	public void setSongIdPlayTime(int playtime) {
		songid_play_time = playtime;
	}

}
