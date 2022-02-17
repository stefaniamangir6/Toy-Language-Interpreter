package Controller;

import Model.PrgState;
import Model.adt.IDict;
import Model.adt.IHeap;
import Model.value.IValue;
import Model.value.RefValue;
import Repo.IRepo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    protected IRepo repository;
    protected ExecutorService executor;

    public Controller(IRepo repository){
        this.repository = repository;
    }

    private List<Integer> getHeapAddressesFromSymbolTable(IDict<String, IValue> symbolTableValues) {
        return symbolTableValues.getAllValues()
                .stream()
                .filter(elem -> elem instanceof RefValue)
                .map(elem -> {
                    RefValue elem1 = (RefValue) elem;
                    return elem1.getHeapAddress();
                }).collect(Collectors.toList());

    }

    protected HashMap<Integer, IValue> getGarbageCollector(List<Integer> symTableAddr, IHeap<Integer, IValue> heap) {
       /* // the heap is the same for all threads, so we just pick one thread from which to get the heap
        IHeap<Integer, IValue> heap = this.repository.getPrgList().get(0).getHeapTable();

        List<Integer> symbolTableAddresses = new ArrayList<Integer>();
        threadList.forEach(thread -> symbolTableAddresses.addAll(this.getHeapAddressesFromSymbolTable(thread.getSymTable())));
        List<Integer> heapReferencedAddresses = heap.getAllValues()
                .stream()
                .filter(elem -> elem instanceof RefValue)
                .map(elem -> {RefValue elem1 = (RefValue)elem; return elem1.getHeapAddress();})
                .collect(Collectors.toList());

        return (HashMap<Integer, IValue>)heap.getAllPairs().entrySet().stream()
                .filter(elem -> symbolTableAddresses.contains(elem.getKey()) || heapReferencedAddresses.contains(elem.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));*/
        List<Integer> heapReferencedAddresses = heap.getAllValues()
                .stream()
                .filter(elem -> elem instanceof RefValue)
                .map(elem -> {RefValue elem1 = (RefValue)elem; return elem1.getHeapAddress();})
                .collect(Collectors.toList());

        return (HashMap<Integer, IValue>)heap.getAllPairs().entrySet().stream()
                .filter(elem -> symTableAddr.contains(elem.getKey()) || heapReferencedAddresses.contains(elem.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));



    }

    public void addProgram(PrgState newPrg) {
        repository.addPrg(newPrg);
    }

    public void oneStepForAllThreads(List<PrgState> programs) {
        programs.forEach(program -> {
            try {
                repository.logPrgStateExec(program);
                //System.out.println(program.toString() + '\n');
            } catch (Exception e) {
                System.out.println("One step failed" + e.toString());
            }
        });

        //prepare list of callables
        List<Callable<PrgState>> callList = programs.stream()
                .map((PrgState program) -> (Callable<PrgState>) program::oneStep)
                .collect(Collectors.toList());

        //start execution of callables
        List<PrgState> newProgramList = null;
        try {
            newProgramList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (ExecutionException | InterruptedException e) {
                            System.out.println("One step failed" + e.toString());
                        }
                        return null;
                    })
                    .filter(Objects::nonNull) // keeps only not null values
                    .collect(Collectors.toList());
        }catch (InterruptedException e){
            System.out.println(e.toString());
        }

        //add new threads to existing ones
        programs.addAll(newProgramList);

        programs.forEach(program -> {
            try {
                repository.logPrgStateExec(program);
                //System.out.println(program.toString() + '\n');
            } catch (Exception e) {
                System.out.println("One step failed" + e.toString());
            }
        });
        repository.setPrgList(programs);
    }

    public List<PrgState> removeCompletedProgram(List<PrgState> inProgress){
        return inProgress.stream()
                .filter(program -> !program.isCompleted())
                .collect(Collectors.toList());
    }

    public void allStep() throws Exception {
        /*repository.logPrgStateExec();
        PrgState prg = repository.getCrtPrg(); // repo is the controller field of type IRepo
        //System.out.println(prg.toString()+"\n");
        while (!prg.getExecutionStack().isEmpty()) {
            PrgState st = oneStep(prg);
            repository.addPrg(st);
            repository.logPrgStateExec();
            prg.getHeapTable().setContent(getGarbageCollector(getHeapAddressesFromSymbolTable(prg.getSymTable()),
                    prg.getHeapTable()));

            //System.out.println(prg.toString()+ "\n");
        }*/

        executor = Executors.newFixedThreadPool(2);

        List<PrgState> programList = removeCompletedProgram(repository.getPrgList());

        while(programList.size() > 0){
            programList.get(0).getHeapTable().setContent(getGarbageCollector(getHeapAddressesFromSymbolTable(programList.get(0).getSymTable()),
                    programList.get(0).getHeapTable()));
            oneStepForAllThreads(programList);

            programList = removeCompletedProgram(repository.getPrgList());
        }
        executor.shutdownNow();
        repository.setPrgList(programList);

    }

    public IRepo getRepository() {
        return repository;
    }

    public void executeOneStep() throws Exception {
        executor = Executors.newFixedThreadPool(2);

        List<PrgState> programList = removeCompletedProgram(repository.getPrgList());

        if(programList.size() > 0){
            programList.get(0).getHeapTable().setContent(getGarbageCollector(getHeapAddressesFromSymbolTable(programList.get(0).getSymTable()),
                    programList.get(0).getHeapTable()));
            oneStepForAllThreads(programList);

            programList = removeCompletedProgram(repository.getPrgList());
        }
        executor.shutdownNow();
        repository.setPrgList(programList);
    }
}
