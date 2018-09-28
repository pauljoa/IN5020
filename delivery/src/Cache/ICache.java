package Cache;

/**
 * @param <T> Generic cache interface
 */
public interface ICache<T> 
{
	
	/**
	 * @param Id The Id of the item T to be retrieved from cache
	 * @return The item T identified by the Id, else returns null
	 */
	T Get(String Id);
	/**
	 * @param Id The Id of the item T
	 * @param obj the item T
	 * @return the item T on successful insertion, else returns null
	 */
	T Put(String Id, T obj);
	
	/**
	 * @param Id The Id of the item T to be deleted
	 * @return the item T that was deleted, if no item is found returns null
	 */
	T Delete(String Id);
}
