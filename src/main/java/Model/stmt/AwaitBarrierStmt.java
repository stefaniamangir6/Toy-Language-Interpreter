package Model.stmt;

import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IStack;
import Model.types.IType;
import Model.types.IntType;
import Model.value.IValue;
import Model.value.IntValue;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import Exception.UndefinedVariableException;
import Exception.InvalidTypeException;

public class AwaitBarrierStmt implements IStmt {
    private final String indexVarName; // "cnt"
    private static Lock lock = new ReentrantLock();

    public AwaitBarrierStmt(String indexVarName) {
        this.indexVarName = indexVarName;
    }

    @Override
    public PrgState execute(PrgState crtState) throws Exception {
        IDict<String, IValue> symbolTable = crtState.getSymTable();

        IStack<IStmt> stack = crtState.getExecutionStack();

        if (!symbolTable.isDefined(this.indexVarName)) {
            throw new UndefinedVariableException("Variable " + this.indexVarName + " is not defined in the symbolTable");
        }

        int barrierIndex = ((IntValue)(symbolTable.lookup(this.indexVarName))).getValue();

        lock.lock();
        IDict<Integer, Pair<Integer, ArrayList<Integer>>> barrierTable = crtState.getBarrierTable();
        if (!barrierTable.isDefined(barrierIndex)) {
            throw new UndefinedVariableException("Variable " + this.indexVarName + " is not a valid index in the barrier table");
        }

        Pair<Integer, ArrayList<Integer>> barrierValue = barrierTable.lookup(barrierIndex);
        Integer capacity = barrierValue.getKey();
        ArrayList<Integer> currentWaitingThreads = barrierValue.getValue();

        if (currentWaitingThreads.size() < capacity) { // the thread will have to wait for the barrier to "fill"
            if (!currentWaitingThreads.contains(crtState.getThreadID())) {
                currentWaitingThreads.add(crtState.getThreadID());
                barrierTable.update(barrierIndex, new Pair<Integer, ArrayList<Integer>>(capacity, currentWaitingThreads));
            }

            stack.push(this); // will wait one more step for the barrier to "fill"
        }
        lock.unlock();

        return null;
    }

    @Override
    public String toString() {
        String representation = "";
        representation += ("awaitBarrier(" + this.indexVarName + ");\n");
        return representation;
    }

    @Override
    public IDict<String, IType> getTypeEnvironment(
            IDict<String,IType> initialTypeEnvironment) throws Exception {
        if (!initialTypeEnvironment.isDefined(this.indexVarName)) {
            throw new UndefinedVariableException("Variable " + this.indexVarName + " is not defined in the typeEnvironment");
        }
        if (!initialTypeEnvironment.lookup(this.indexVarName).equals(new IntType())) {
            throw new InvalidTypeException("Variable " + this.indexVarName + " is not an integer");
        }
        return initialTypeEnvironment;
    }
}
