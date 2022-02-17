package Model.adt;

import Exception.EmptyADTException;

import java.util.ArrayList;
import java.util.List;

public class MyList<TElem> implements IList<TElem> {
    private List<TElem> list;

    public MyList() {
        this.list = new ArrayList<TElem>();
    }

    @Override
    public void add(TElem newElem) {
        this.list.add(newElem);
    }

    @Override
    public boolean remove(TElem elem) {
        return this.list.remove(elem);
    }

    @Override
    public String toString() {
        String representation = "";
        for(TElem elem : this.list) {
            representation += (elem.toString() + "\n");
        }
        return representation;
    }

    @Override
    public int size() {
        return this.list.size();
    }

    @Override
    public void clear() {
        this.list.clear();
    }

    @Override
    public boolean empty() {
        return this.list.isEmpty();
    }

    @Override
    public TElem pop() throws Exception{
        int size = this.list.size();
        if (size == 0) {
            throw new EmptyADTException("Empty list");
        }
        return this.list.remove(size - 1);
    }

   @Override
    public TElem get(int pos) throws Exception{
        int size = this.list.size();
        if (size == 0 || pos > size) {
            throw new EmptyADTException("Empty list");
        }
        return this.list.get(pos);
    }

    @Override
    public List getContent() {
        return this.list;
    }


}
