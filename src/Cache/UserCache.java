package Cache;
import java.util.HashMap;
import java.util.Map;

import TasteProfile.UserProfile;

public class UserCache implements ICache<UserProfile> {
	
	public Map<String,UserProfile> users;
	public UserCache() {
		users = new HashMap<String,UserProfile>();
	}

	@Override
	public UserProfile Get(String Id) {
		return users.get(Id);
	}

	@Override
	//Puts a new UserProfile Object into the Cache, determine if songs that exis
	public UserProfile Put(String Id, UserProfile obj) {
		//Check if exists
		if(!users.containsKey(Id)) {
			
		}
		return null;
	}

	@Override
	public UserProfile Delete(String Id) {
		// TODO Auto-generated method stub
		return null;
	}

}
