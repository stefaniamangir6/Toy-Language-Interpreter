package Model.adt;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MyHeap<TKey,TValue>implements IHeap<TKey,TValue> {
    private HashMap<Integer,TValue> heapTable;
    private int firstAvailablePosition = 1;

    public MyHeap() {
        heapTable = new HashMap<Integer,TValue>();
    }


    @Override
    public int add(TValue v) {
        int cop = this.firstAvailablePosition;
        this.heapTable.put(this.firstAvailablePosition, v);
        this.firstAvailablePosition++;
        return cop;
    }

    @Override
    public void update(int v1, TValue v2) {
        this.heapTable.replace(v1,  v2);
    }

    @Override
    public TValue remove(int v1) {
        return this.heapTable.remove(v1);
    }

    @Override
    public int size() {
        return this.heapTable.size();
    }

    @Override
    public boolean existsValue(TValue v2) {
        return this.heapTable.containsValue(v2);
    }

    @Override
    public boolean isDefined(int v1) {
        return this.heapTable.containsKey(v1);
    }

    @Override
    public TValue lookup(int v1) {
        return this.heapTable.get(v1);
    }

    @Override
    public String toString() {
        String representation = "";
        Collection<Integer> allKeys = this.heapTable.keySet();
        for (Integer key : allKeys) {
            representation += (key.toString() + " -> " + this.heapTable.get(key).toString() + "\n");
        }
        return representation;
    }

    public HashMap<Integer, TValue> getAllPairs() {
        HashMap<Integer, TValue> returnValue = this.heapTable;
        return returnValue;
    }

    public void setContent(HashMap<Integer, TValue> newContent) {
        this.heapTable = newContent;
    }

    @Override
    public Collection<Integer> getAllKeys() {
        return this.heapTable.keySet();
    }

    @Override
    public Collection<TValue> getAllValues() {
        return this.heapTable.values();
    }

    @Override
    public Map getContent() {
        return this.heapTable;
    }
}
