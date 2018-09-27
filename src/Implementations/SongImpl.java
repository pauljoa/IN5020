package Implementations;

import TasteProfile.Song;

public class SongImpl extends Song {

	public SongImpl() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param id The id of the song
	 */
	public void setSongId(String id) {
		this.id = id;
	}
	/**
	 * @param count the play count of the song
	 */
	public void setPlayCount(int count) {
		this.play_count = count;
	}
	/**
	 * @param id the id of the song
	 * @param count the playcount of the song
	 */
	public void init(String id, int count) {
		this.id = id;
		this.play_count = count;
	}
}
