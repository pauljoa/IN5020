package Implementations;

import TasteProfile.SongProfile;

public class SongProfileImpl extends SongProfile {

	public SongProfileImpl() {
		// TODO Auto-generated constructor stub
	}
	/**
	 * Initializer for SongProfileImpl
	 * @param count The total play count for the song
	 * @param top The top three users for the song
	 */
	public void init(int count, TopThreeImpl top) {
		this.total_play_count = count;
		this.top_three_users = top;
	}
	/**
	 * @param count sets the times played count to the specified value
	 */
	public void setPlayCount(int count) {
		this.total_play_count = count;
	}
	/**
	 * @param top sets the top three users to the top value
	 */
	public void setTopThree(TopThreeImpl top) {
		this.top_three_users = top;
	}
	/**
	 * @param count updates the play count 
	 */
	public void addPlayCount(int count) {
		this.total_play_count += count;
	}
	/**
	 * @param topThree Update the topThreeUsers in the songProfile
	 */
	public void updateTopThree(UserCounterImpl topThree) {
		TopThreeImpl top = (TopThreeImpl) this.top_three_users;
		top.updateTopThreeUsers(topThree);
		this.top_three_users = top;
	}
}
