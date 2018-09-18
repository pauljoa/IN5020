package Cache;
import java.util.HashMap;
import java.util.Map;

import Implementations.UserProfileImpl;

public class UserCache implements ICache<UserProfileImpl> {
	
	public Map<String,UserProfileImpl> users;
	public UserCache() {
		users = new HashMap<String,UserProfileImpl>();
	}

	@Override
	public UserProfileImpl Get(String Id) {
		return users.get(Id);
	}

	@Override
	//Puts a new UserProfile Object into the Cache, determine if songs that exis
	public UserProfileImpl Put(String Id, UserProfileImpl obj) {
		//Check if exists
		if(users.containsKey(Id)) {
			UserProfileImpl user = users.get(Id);
			
			
			return null;
			
		}
		else if(users.size() < 1000) {
			return users.put(Id, obj);
		}
		return obj;
	}

	@Override
	public UserProfileImpl Delete(String Id) {
		// TODO Auto-generated method stub
		return null;
	}

}
