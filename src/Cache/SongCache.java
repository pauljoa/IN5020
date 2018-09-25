package Cache;

import java.util.HashMap;
import java.util.Map;

import Implementations.SongProfileImpl;

public class SongCache implements ICache<SongProfileImpl> {
	
	public Map<String,SongProfileImpl> songs;
	public SongCache() {
		songs = new HashMap<>();
	}

	@Override
	public SongProfileImpl Get(String Id) {
		return songs.get(Id);
	}

	@Override
	public SongProfileImpl Put(String Id, SongProfileImpl obj) {
		return songs.put(Id, obj);
	}

	@Override
	public SongProfileImpl Delete(String Id) {
		return songs.remove(Id);
	}

}
