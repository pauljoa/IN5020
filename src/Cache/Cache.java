package Cache;

public interface Cache<T> 
{
	
	T Get(String Id);
	T Put(String Id, T obj);
	T Delete(String Id);
}
