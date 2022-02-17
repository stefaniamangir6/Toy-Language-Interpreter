package Model.adt;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Stack;

public class MyStack<TElem> implements IStack<TElem> {
    private Stack<TElem> stack;

    public MyStack() {
        this.stack = new Stack<TElem>();
    }

    @Override
    public void clear() {
        this.stack.clear();
    }

    @Override
    public TElem top() {
        return this.stack.peek();
    }

    @Override
    public TElem pop() {
        return this.stack.pop();
    }

    @Override
    public void push(TElem v) {
        this.stack.push(v);
    }

    @Override
    public boolean isEmpty() {
        return this.stack.empty();
    }

    @Override
    public int size() {
        return this.stack.size();
    }

    @Override
    public String toString() {
        String representation = "";
        ArrayList<TElem> a = new ArrayList<>(this.stack);
        ListIterator<TElem> li = a.listIterator(this.stack.size());
        while(li.hasPrevious())
        {
            representation += (li.previous().toString());
        }
        /*for (TElem elem : this.stack) {
                representation += (elem.toString());
            }*/
        return representation;
    }
    @Override
    public List getValues() {
        return stack.subList(0,stack.size());
    }
}

