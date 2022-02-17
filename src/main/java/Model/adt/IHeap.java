package Model.adt;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public interface IHeap<TKey,TValue>{
    int add(TValue v);
    void update(int v1, TValue v2);
    TValue remove(int v1);
    int size();
    boolean existsValue(TValue v2); // verifies if the map contains the value
    boolean isDefined(int v1); // verifies if the map contains the key
    TValue lookup(int v1); // get a value mapped to a key
    String toString();
    HashMap<Integer, TValue> getAllPairs();
    void setContent(HashMap<Integer, TValue> newContent);
    Collection<Integer> getAllKeys();
    Collection<TValue> getAllValues();
    Map getContent();
}
