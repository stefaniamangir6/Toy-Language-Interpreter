package Model.adt;

public class MyLockTable<TKey, TValue> extends MyDict<TKey, TValue> {
    private int firstAvailablePosition = 1;

    public MyLockTable() {
        super();
    }

    private synchronized int setNextAvailablePosition() {
        return this.firstAvailablePosition + 1;
    }

    public synchronized int getFirstAvailablePosition() {
        int positionCopy = this.firstAvailablePosition;
        this.firstAvailablePosition = this.setNextAvailablePosition();
        return positionCopy;
    }

    public void clear() {
        this.dictionary.clear();
        this.firstAvailablePosition = 1;
    }
}
