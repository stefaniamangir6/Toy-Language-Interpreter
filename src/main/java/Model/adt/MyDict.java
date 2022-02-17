package Model.adt;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


public class MyDict<TKey,TValue> implements IDict<TKey,TValue> {
    private Map<TKey, TValue> dictionary;

    public MyDict() {
        dictionary = new HashMap<TKey,TValue>();
    }


    @Override
    public void add(TKey v1, TValue v2) {
        this.dictionary.put(v1, v2);
    }

    @Override
    public void update(TKey v1, TValue v2) {
        this.dictionary.replace(v1,  v2);
    }

    @Override
    public TValue remove(TKey v1) {
        return this.dictionary.remove(v1);

    }

    @Override
    public int size() {
        return this.dictionary.size();

    }

    @Override
    public boolean existsValue(TValue v2) {
        return this.dictionary.containsValue(v2);
    }

    @Override
    public boolean isDefined(TKey v1) {
        return this.dictionary.containsKey(v1);
    }

    @Override
    public TValue lookup(TKey v1) {
        return this.dictionary.get(v1);
    }

    @Override
    public boolean isEmpty() {
        return this.dictionary.isEmpty();
    }

    @Override
    public Collection<TKey> getAllKeys() {
        return this.dictionary.keySet();
    }

    @Override
    public Collection<TValue> getAllValues() {
        return this.dictionary.values();
    }

    @Override
    public IDict<TKey, TValue> clone() {
        //lock.lock();
        IDict<TKey, TValue> newDictionary = new MyDict<TKey, TValue>();
        // this will only work if the types are immutable
        // in this case, all Types and Values (and the java String) are immutable
        this.dictionary.entrySet().stream().forEach(pair -> newDictionary.add(pair.getKey(), pair.getValue()));
        //lock.unlock();
        return newDictionary;
    }

    @Override
    public String toString() {
        String representation = "";
        Collection<TKey> allKeys = this.dictionary.keySet();
        for (TKey key : allKeys) {
            representation += (key.toString() + " -> " + this.dictionary.get(key).toString() + "\n");
        }
        return representation;
    }

    public String toString1() {
        String representation = "";
        Collection<TKey> allKeys = this.dictionary.keySet();
        for (TKey key : allKeys) {
            representation += (key.toString() + "\n");
        }
        return representation;
    }

    @Override
    public Map getContent() {
        return this.dictionary;
    }
}
