package Model.exp;

import Model.adt.IDict;
import Model.adt.IHeap;
import Model.types.IType;
import Model.value.IValue;

public class ValExpr implements IExp{
    private final IValue value;

    public ValExpr(IValue value) {
        this.value = value;
    }

    @Override
    public IValue eval(IDict<String, IValue> symbolTable, IHeap<Integer, IValue> heap) throws Exception {
        return this.value;
    }

    @Override
    public String toString() {
        String representation = "";
        representation += (this.value.toString());
        return representation;
    }

    @Override
    public IType typeCheck(IDict<String, IType> typeEnvironment) throws Exception {
        return this.value.getType();
    }

    public IType getType(IDict<String, IValue> symbolTable) throws Exception {
        return this.value.getType();
    }
}
