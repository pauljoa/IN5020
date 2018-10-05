package Cache;

import java.util.HashMap;
import java.util.Map;

import Implementations.SongProfileImpl;

/**
 * The SongCache class implements the ICache interface to provide GET PUT DELETE methods for caching of SongProfiles
 */
public class SongCache implements ICache<SongProfileImpl> {
	
	/**
	 * The Cache of songProfiles
	 */
	public Map<String,SongProfileImpl> songs;
	/**
	 * Initiate the cache
	 */
	public SongCache() {
		songs = new HashMap<>();
	}

	/* (non-Javadoc) 
	 * @see Cache.ICache#Get(java.lang.String)
	 */
	@Override
	public SongProfileImpl Get(String Id) {
		return songs.get(Id);
	}

	/* (non-Javadoc)
	 * @see Cache.ICache#Put(java.lang.String, java.lang.Object)
	 */
	@Override
	public SongProfileImpl Put(String Id, SongProfileImpl obj) {
		return songs.put(Id, obj);
	}

	/* (non-Javadoc)
	 * @see Cache.ICache#Delete(java.lang.String)
	 */
	@Override
	public SongProfileImpl Delete(String Id) {
		return songs.remove(Id);
	}

}
