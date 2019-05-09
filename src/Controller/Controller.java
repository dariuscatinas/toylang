package Controller;

import Heap.IHeap;
import MyDictionary.IMyDictionary;
import MyList.*;
import Statements.IStatement;
import Statements.ProgramState;
import Repo.IRepo;
import Exception.*;
import Tuple.Tuple;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class Controller {
    private IRepo r;
    private ExecutorService executor;
    private String originalProgram;
    public Controller(IRepo repo){
        r = repo;
        originalProgram = originalProgram();
        executor = Executors.newFixedThreadPool(4);

    }

    private ProgramState executeOnce (ProgramState prg){
        IStatement st = prg.getExeStack().pop();
        st.execute(prg);
        return prg;
    }

    private Map<Integer, Integer> garbageCollector(IMyDictionary<String, Integer> lookupTable, IHeap<Integer> heap){
        return heap.entrySet().stream().
                filter(e->lookupTable.containsValue(e.getKey()))
                .collect(Collectors
                        .toMap(Map.Entry::getKey,
                            Map.Entry::getValue));
    }

    private List<ProgramState> removeCompletedPrg(List<ProgramState> inList){
        return inList.stream().filter(entry-> { return !entry.isCompleted(); }).collect(Collectors.toList());
    }

    public void oneStepForAll(List<ProgramState> programStates){

        programStates.forEach(prg ->r.logPrgState(prg));
        List<Callable<ProgramState>> callList = programStates.stream()
                .map((ProgramState p) -> (Callable<ProgramState>)(() -> {return p.oneStep();}))
                .collect(Collectors.toList());
        List<ProgramState> newPrgList = null;
        try{
        newPrgList = executor.invokeAll(callList).stream()
                . map(future -> {
                                try {
                                    return future.get();
                                }
                                catch (InterruptedException | ExecutionException ex ) {
                                    throw new ControllerException("An error has occured at threading");
                                }
                        }
                ).filter(p -> p!= null).collect(Collectors.toList());
        }
        catch (InterruptedException exception){
            throw new ControllerException("An error has occured at threading");
        }
        programStates.addAll(newPrgList);
        programStates.forEach(prg ->{r.logPrgState(prg);
            prg.getHeap().setContent(garbageCollector(prg.getLookupTable(), prg.getHeap()));
        });
        r.setPrg(new MyList<ProgramState>(programStates));

    }

    public void allStep(){


            List<ProgramState> prgList=removeCompletedPrg(r.getPrg().toList());
            IMyDictionary<Integer, Tuple<String, BufferedReader>> fileTable = prgList.get(0).getFileTable();
            try {
                while (prgList.size() > 0) {
                    oneStepForAll(prgList);
                    prgList = removeCompletedPrg(r.getPrg().toList());

                }
            }
            catch (RepoException rex){
                throw new ControllerException("Exception caught in Repo: " + rex.toString());
            }
            catch (ADTException adtex){
                throw new ControllerException("Exception caught as ADTEx: " + adtex.toString());
            }
            catch (FileException fex){
                throw new ControllerException("Exception caught as FileEx: " + fex.toString());
            }
            catch (ExpressionException exprex){
                throw new ControllerException("Exception caught as an ExpresionEx: " + exprex.toString());
            }
            catch (PointerException ptrex){
                throw new ControllerException("Exception caught as PointerException: " + ptrex.toString());
            }

            executor.shutdownNow();
            fileTable.entrySet().forEach(tup -> {
            try {
                tup.getValue().y.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            });
            fileTable.clear();
            r.setPrg(new MyList<>(prgList));

    }
    public void executeAllStep(){
        ProgramState prg = r.getPrgIndex(0);
        try {
            while (!prg.isFinished()) {
                this.executeOnce(prg);
                prg.getHeap().setContent(garbageCollector(prg.getLookupTable(), prg.getHeap()));
                r.logPrgState(prg);
            }
        }
        catch (RepoException rex){
            throw new ControllerException("Exception caught in Repo: " + rex.toString());
        }
        catch (ADTException adtex){
            throw new ControllerException("Exception caught as ADTEx: " + adtex.toString());
        }
        catch (FileException fex){
            throw new ControllerException("Exception caught as FileEx: " + fex.toString());
        }
        catch (ExpressionException exprex){
            throw new ControllerException("Exception caught as an ExpresionEx: " + exprex.toString());
        }
        catch (PointerException ptrex){
            throw new ControllerException("Exception caught as PointerException: " + ptrex.toString());
        }
        finally {
            IMyDictionary<Integer, Tuple<String, BufferedReader>> fileTable =  prg.getFileTable();
            IMyDictionary<String, Integer> lookupTable = prg.getLookupTable();
            fileTable.entrySet().forEach(tup -> {
                try {
                    tup.getValue().y.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            fileTable.clear();
            r.logPrgState(prg);
        }
    }

    public String originalProgram(){
        originalProgram = r.getPrg().get(0).toString();
        return originalProgram;
    }
    public String savedOriginalProblem(){
        return originalProgram;
    }
    public int getNrProgrammes(){
        return r.getPrg().size();
    }
    public List<ProgramState> getProgrammes(){
        return r.getPrg().toList();
    }
    public ProgramState getProgramStateId(int id){
        Optional<ProgramState> prg = r.getPrg().toList().stream().filter(p-> p.getID()==id).findFirst();
        if(prg.isPresent()){
            return prg.get();
        }
        return null;
    }
}

