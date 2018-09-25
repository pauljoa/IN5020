package Cache;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import Implementations.UserProfileImpl;

public class UserCache implements ICache<UserProfileImpl> {
	
	public Map<String,UserProfileImpl> users;
	public UserProfileImpl lowest;
	public UserCache() {
		users = new HashMap<>();
		lowest = null;
	}

	@Override
	public UserProfileImpl Get(String Id) {
		return users.get(Id);
	}

	@Override
	//Puts a new UserProfile Object into the Cache, pop lowest user if size exceeds 1000
	public UserProfileImpl Put(String Id, UserProfileImpl user) {
		//Check if exists
		if(users.size() < 1000) {
			if(lowest == null || lowest.total_play_count > user.total_play_count) {
				lowest = user;
			}
			return users.put(Id, user);
		}
		else {
			if(lowest.total_play_count > user.total_play_count) {
				return user;
			}
			else {
				Delete(lowest.id);
				users.put(Id, user);
				FindLowest();
			}
			return user;
		}
	}
	private void FindLowest() {
		UserProfileImpl lowTmp = this.FilterMap(this.users);
		if(lowTmp == null) {
		}
		else {
			this.lowest = lowTmp;
		}
		
	}
	//Filter the HashMap for the lowest total play count
	private UserProfileImpl FilterMap(Map<String,UserProfileImpl> m) {
		Comparator<? super Entry<String, UserProfileImpl>> comp = (e1,e2) -> e1.getValue().compareTo(e2.getValue());
	    UserProfileImpl tmp = (UserProfileImpl) m.entrySet().stream().min(comp).get();
		return tmp;
		
	}

	@Override
	public UserProfileImpl Delete(String Id) {
		return this.users.remove(Id);
	}

}
