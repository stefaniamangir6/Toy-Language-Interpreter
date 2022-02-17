package Model.adt;
import java.util.Collection;
import java.util.Map;

public interface IDict<TKey,TValue>{

    void add(TKey v1, TValue v2);
    void update(TKey v1, TValue v2);
    TValue remove(TKey v1);
    int size();
    boolean existsValue(TValue v2); // verifies if the map contains the value
    boolean isDefined(TKey v1); // verifies if the map contains the key
    TValue lookup(TKey v1); // get a value mapped to a key
    boolean isEmpty();
    public IDict<TKey, TValue> clone();
    String toString();
    String toString1();
    Collection<TKey> getAllKeys();
    Collection<TValue> getAllValues();
    Map getContent();


}
