package Model.exp;

import Exception.InvalidTypeException;
import Exception.UndefinedVariableException;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.types.IType;
import Model.types.RefType;
import Model.value.IValue;
import Model.value.RefValue;

public class HeapReadingExpression implements IExp{
    private final IExp expression;

    public HeapReadingExpression(IExp expression) {
        this.expression = expression;
    }

    @Override
    public IValue eval(IDict<String, IValue> symbolTable, IHeap<Integer, IValue> heap) throws Exception {
        IValue expressionValue = this.expression.eval(symbolTable, heap);

        int heapAddress = ((RefValue)expressionValue).getHeapAddress();
        if (!heap.isDefined(heapAddress)) {
            throw new UndefinedVariableException("HeapReadingExpression: Undefined variable at address " + Integer.toHexString(heapAddress));
        }
        return heap.lookup(heapAddress);
    }

    @Override
    public String toString() {
        String representation = "";
        representation += ("rH(" + this.expression.toString() + ")");
        return representation;
    }

    @Override
    public IType typeCheck(IDict<String, IType> typeEnvironment) throws Exception {
        IType expressionType = this.expression.typeCheck(typeEnvironment);
        if (!(expressionType instanceof RefType)) {
            throw new InvalidTypeException("Expression is not a reference");
        }
        return ((RefType)expressionType).getInnerType();
    }
}
