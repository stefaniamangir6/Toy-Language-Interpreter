package Model.adt;

import java.util.List;

public interface IList<TElem> {
    void add(TElem v);
    String toString();
    boolean empty();
    void clear();
    int size();
    boolean remove(TElem v);
    TElem pop() throws Exception;
   TElem get(int pos) throws Exception;
   List getContent();
}
