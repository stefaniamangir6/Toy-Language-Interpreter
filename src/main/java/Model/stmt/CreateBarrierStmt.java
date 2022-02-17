package Model.stmt;

import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.adt.MyLockTable;
import Model.exp.IExp;
import Model.types.IType;
import Model.types.IntType;
import Model.value.IValue;
import Model.value.IntValue;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import Exception.InvalidTypeException;
import Exception.UndefinedVariableException;



public class CreateBarrierStmt implements IStmt {
    private final String indexVarName; // "cnt"
    private final IExp capacityExpr;
    private static Lock lock = new ReentrantLock();

    public CreateBarrierStmt(String indexVarName, IExp capacityExpr) {
        this.indexVarName = indexVarName;
        this.capacityExpr = capacityExpr;
    }

    @Override
    public PrgState execute(PrgState crtState) throws Exception {
        IDict<String, IValue> symbolTable = crtState.getSymTable();
        IHeap<Integer, IValue> heap = crtState.getHeapTable();

        IValue capacity = this.capacityExpr.eval(symbolTable, heap);

        lock.lock();
        IDict<Integer, Pair<Integer, ArrayList<Integer>>> barrierTable = crtState.getBarrierTable();
        int newPositionInBarrierTable = ((MyLockTable<Integer, Pair<Integer, ArrayList<Integer>>>) (barrierTable)).getFirstAvailablePosition();

        barrierTable.insertL(newPositionInBarrierTable, new Pair<Integer, ArrayList<Integer>>(((IntValue) capacity).getValue(), new ArrayList<Integer>()));

        if (!symbolTable.isDefined(this.indexVarName)) {
            throw new UndefinedVariableException("Variable " + this.indexVarName + " is not defined in the symbol table");
        }
        symbolTable.update(this.indexVarName, new IntValue(newPositionInBarrierTable));
        lock.unlock();

        return null;
    }

    @Override
    public String toString() {
        String representation = "";
        representation += ("createBarrier(" + this.indexVarName + ", " + this.capacityExpr.toString() + ");\n");
        return representation;
    }

    @Override
    public IDict<String, IType> getTypeEnvironment(
            IDict<String, IType> initialTypeEnvironment) throws Exception {
        if (!initialTypeEnvironment.isDefined(this.indexVarName)) {
            throw new UndefinedVariableException("Variable " + this.indexVarName + " is not defined in the type environment");
        }
        if (!initialTypeEnvironment.lookup(this.indexVarName).equals(new IntType())) {
            throw new InvalidTypeException("Variable " + this.indexVarName + " is not an integer");
        }
        if (!this.capacityExpr.typeCheck(initialTypeEnvironment).equals(new IntType())) {
            throw new InvalidTypeException("Capacity expression is not integer");
        }
        return initialTypeEnvironment;
    }
}
