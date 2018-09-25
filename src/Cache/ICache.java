package Cache;

import java.util.List;

public interface ICache<T> 
{
	T Get(String Id);
	T Put(String Id, T obj);
	T Delete(String Id);
}
