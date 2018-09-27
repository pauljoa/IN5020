package Implementations;

import TasteProfile.Song;
import TasteProfile.UserProfile;

@SuppressWarnings("serial")
public class UserProfileImpl extends UserProfile implements Comparable<UserProfileImpl> {
	public UserProfileImpl() {
		this.id = "";
		this.total_play_count = 0;
		this.songs = new Song[0];
	}
	public void Init(String id,int playCount,Song[] songs) {
		this.id = id;
		this.total_play_count = playCount;
		this.songs = songs; 
	}
	@Override
	public int compareTo(UserProfileImpl e2) {
		if(this.total_play_count > e2.total_play_count) {
			return 1;
		}
		else if(this.total_play_count < e2.total_play_count) {
			return -1;
		}
		return 0;
	}

}
