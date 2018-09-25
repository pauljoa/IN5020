package Implementations;

import TasteProfile.UserCounter;

public class UserCounterImpl extends UserCounter {

	public UserCounterImpl() {
		user_id = "";
		songid_play_time = 0;
	}
	
	public void setUserId(String user) {
		user_id = user;
	}
	
	public void setSongIdPlayTime(int playtime) {
		songid_play_time = playtime;
	}

}
