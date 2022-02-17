package Repo;
import Model.PrgState;

import java.util.List;

public interface IRepo {
    void addPrg(PrgState newPrg);
    List<PrgState> getPrgList();
    void setPrgList(List<PrgState> newList);
    void logPrgStateExec(PrgState state) throws Exception;
    PrgState getProgramWithId(int id);
}
