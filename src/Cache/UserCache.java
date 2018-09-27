package Cache;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import Implementations.UserProfileImpl;

/**
 * 	The UserCache class implements the ICache interface to provide GET PUT DELETE methods for caching of UserProfiles
 *
 */
public class UserCache implements ICache<UserProfileImpl> {
	
	public Map<String,UserProfileImpl> users;
	public UserProfileImpl lowest;
	public UserCache() {
		users = new HashMap<>();
		lowest = null;
	}

	/* (non-Javadoc)
	 * @see Cache.ICache#Get(java.lang.String)
	 */
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
	/**
	 * Finds the lowest amount of playCounts in the user cache and sets the new lowest user
	 */
	private void FindLowest() {
		UserProfileImpl lowTmp = this.FilterMap(this.users);
		if(lowTmp == null) {
		}
		else {
			this.lowest = lowTmp;
		}
		
	}

	/**
	 * @param m the map to be filtered
	 * @return Returns the lowest playCount user in the cache, else returns null
	 */
	private UserProfileImpl FilterMap(Map<String,UserProfileImpl> m) {
		Comparator<? super Entry<String, UserProfileImpl>> comp = (e1,e2) -> e1.getValue().compareTo(e2.getValue());
	    UserProfileImpl tmp = (UserProfileImpl) m.entrySet().stream().min(comp).get().getValue();
		return tmp;
		
	}

	/* (non-Javadoc)
	 * @see Cache.ICache#Delete(java.lang.String)
	 */
	@Override
	public UserProfileImpl Delete(String Id) {
		return this.users.remove(Id);
	}

}
