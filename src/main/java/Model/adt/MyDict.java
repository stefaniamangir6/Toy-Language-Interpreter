package Model.adt;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


public class MyDict<TKey,TValue> implements IDict<TKey,TValue> {
    protected Map<TKey, TValue> dictionary;
    private static final Lock lock = new ReentrantLock();


    public MyDict() {
        dictionary = new HashMap<TKey,TValue>();

    }


    @Override
    public void add(TKey v1, TValue v2) {
        lock.lock();
        this.dictionary.put(v1, v2);
        lock.unlock();
    }

    @Override
    public void update(TKey v1, TValue v2) {
        lock.lock();
        this.dictionary.replace(v1,  v2);
        lock.unlock();
    }

    @Override
    public TValue remove(TKey v1) {
        lock.lock();
        TValue returnValue = this.dictionary.remove(v1);
        lock.unlock();
        return returnValue;

    }

    @Override
    public int size() {
        lock.lock();
        int returnValue = this.dictionary.size();
        lock.unlock();
        return returnValue;

    }

    @Override
    public boolean existsValue(TValue v2) {
        lock.lock();
        boolean returnValue = this.dictionary.containsValue(v2);
        lock.unlock();
        return returnValue;
    }

    @Override
    public boolean isDefined(TKey v1) {
        lock.lock();
        boolean returnValue = this.dictionary.containsKey(v1);
        lock.unlock();
        return returnValue;
    }

    @Override
    public TValue lookup(TKey v1) {
        lock.lock();
        TValue returnValue = this.dictionary.get(v1);
        lock.unlock();
        return returnValue;
    }

    @Override
    public boolean isEmpty() {
        lock.lock();
        boolean returnValue = this.dictionary.isEmpty();
        lock.unlock();
        return returnValue;
    }

    @Override
    public Collection<TKey> getAllKeys() {
        lock.lock();
        Collection<TKey> returnValue = this.dictionary.keySet();
        lock.unlock();
        return returnValue;
    }

    @Override
    public Collection<TValue> getAllValues() {
        lock.lock();
        Collection<TValue> returnValue = this.dictionary.values();
        lock.unlock();
        return returnValue;
    }

    @Override
    public IDict<TKey, TValue> clone() {
        lock.lock();
        IDict<TKey, TValue> newDictionary = new MyDict<TKey, TValue>();
        // this will only work if the types are immutable
        // in this case, all Types and Values (and the java String) are immutable
        this.dictionary.entrySet().stream().forEach(pair -> newDictionary.add(pair.getKey(), pair.getValue()));
        lock.unlock();
        return newDictionary;
    }

    @Override
    public String toString() {
        String representation = "";

        lock.lock();
        Collection<TKey> allKeys = this.dictionary.keySet();
        for (TKey key : allKeys) {
            representation += (key.toString() + " -> " + this.dictionary.get(key).toString() + "\n");
        }
        lock.unlock();

        return representation;
    }

    public String toString1() {

        String representation = "";

        lock.lock();
        Collection<TKey> allKeys = this.dictionary.keySet();
        for (TKey key : allKeys) {
            representation += (key.toString() + "\n");
        }
        lock.unlock();

        return representation;
    }

    @Override
    public void insertL(TKey key, TValue newValue) {
        lock.lock();
        this.dictionary.put(key, newValue);
        lock.unlock();
    }

    @Override
    public Map getContent() {
        lock.lock();
        Map<TKey, TValue> returnValue = this.dictionary;
        lock.unlock();
        return returnValue;
    }
}
