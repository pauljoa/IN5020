package Implementations;

import TasteProfile.SongProfile;

public class SongProfileImpl extends SongProfile {

	public SongProfileImpl() {
		// TODO Auto-generated constructor stub
	}
	public void init(int count, TopThreeImpl top) {
		this.total_play_count = count;
		this.top_three_users = top;
	}
	public void setPlayCount(int count) {
		this.total_play_count = count;
	}
	public void setTopThree(TopThreeImpl top) {
		this.top_three_users = top;
	}
	public void addPlayCount(int count) {
		this.total_play_count += count;
	}
	public void updateTopThree(UserCounterImpl topThree) {
		TopThreeImpl top = (TopThreeImpl) this.top_three_users;
		top.updateTopThreeUsers(topThree);
		this.top_three_users = top;
	}
}
