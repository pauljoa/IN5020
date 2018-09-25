package Implementations;

import TasteProfile.Song;

public class SongImpl extends Song {

	public SongImpl() {
		// TODO Auto-generated constructor stub
	}

	public void setSongId(String id) {
		this.id = id;
	}
	public void setPlayCount(int count) {
		this.play_count = count;
	}
	public void init(String id, int count) {
		this.id = id;
		this.play_count = count;
	}
}
