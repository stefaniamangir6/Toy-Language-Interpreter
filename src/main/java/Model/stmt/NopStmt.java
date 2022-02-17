package Model.stmt;

import Model.PrgState;
import Model.adt.IDict;
import Model.types.IType;

public class NopStmt implements IStmt {

    public PrgState execute(PrgState state){
        return null;
    }

    @Override
    public String toString(){
        return "No operation";
    }

    @Override
    public IDict<String, IType> getTypeEnvironment(IDict<String, IType> initialTypeEnvironment) throws Exception {
        return null;
    }
}
