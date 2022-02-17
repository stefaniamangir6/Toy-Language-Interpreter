package Repo;

import Model.PrgState;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repo implements IRepo {

    private final String logFilePath;
    List<PrgState> myPrgStates;

    public Repo(String logFilePath) {
        myPrgStates = new ArrayList<PrgState>();
        this.logFilePath = logFilePath;
    }

    @Override
    public void addPrg(PrgState newPrg) {
        myPrgStates.add(newPrg);
    }

    @Override
    public void logPrgStateExec(PrgState state) throws Exception {
        try {
            PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        } catch (IOException e) {
            throw new Exception(e.getMessage());
        }

        PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(logFilePath, true)));
        logFile.append(state.toString());
        logFile.append("\n");
        logFile.close();

       /*PrintWriter logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)));

        this.myPrgStates.forEach(thread -> logFile.append(thread.toString()));
        logFile.close();*/
    }

    @Override
    public List<PrgState> getPrgList() {
        return this.myPrgStates;
    }

    @Override
    public void setPrgList(List<PrgState> newList) {
        this.myPrgStates.clear();
        this.myPrgStates.addAll(newList);
    }

    public void clearFile() throws IOException {
        PrintWriter logFile;
        logFile = new PrintWriter(new FileWriter(logFilePath));
        logFile.close();
    }

    @Override
    public PrgState getProgramWithId(int id) {
        for (PrgState p: myPrgStates)
            if(p.getThreadID()==id)
                return p;
        return null;
    }
}



