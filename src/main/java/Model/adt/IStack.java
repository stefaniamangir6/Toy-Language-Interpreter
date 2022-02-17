package Model.adt;

import java.util.List;

public interface IStack<TElem> {

    TElem pop();
    void push(TElem v);
    TElem top();
    void clear();
    boolean isEmpty();
    int size();
    String toString();
    List getValues();
}

