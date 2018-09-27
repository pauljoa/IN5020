package Implementations;

import TasteProfile.TopThree;

public class TopThreeImpl extends TopThree {

	public TopThreeImpl() {
		
	}
	
	public void setTopThreeUsers(UserCounterImpl[] topThreeUsers) {
		this.topThreeUsers = topThreeUsers;
	}
	
	public void updateTopThreeUsers(UserCounterImpl ucl) {
		if(topThreeUsers[0] == null || topThreeUsers[0].songid_play_time < ucl.songid_play_time) {
			topThreeUsers[2] = topThreeUsers[1];
			topThreeUsers[1] = topThreeUsers[0];
			topThreeUsers[0] = ucl;
		} else if(topThreeUsers[1] == null || topThreeUsers[1].songid_play_time < ucl.songid_play_time) {
			topThreeUsers[2] = topThreeUsers[1];
			topThreeUsers[1] = ucl;
		} else if(topThreeUsers[2] == null || topThreeUsers[2].songid_play_time < ucl.songid_play_time) {
			topThreeUsers[2] = ucl;
		}
	}
}
