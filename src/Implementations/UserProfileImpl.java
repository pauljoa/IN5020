package Implementations;

import java.util.ArrayList;
import java.util.List;

import TasteProfile.Song;
import TasteProfile.UserProfile;

public class UserProfileImpl extends UserProfile implements Comparable<UserProfileImpl> {

	
	
	public UserProfileImpl() {

		songs = new Song[200];
	}
	//Inserts new songs that are not updated 
	public Boolean UpdateSongs(Song s[]) {
		Boolean newSong = false;
		
		
		
		
		
		return null;
		
	}
	public Boolean UpdateSong(Song s) {
		
		
		
		
		
		
		return null;
		
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
