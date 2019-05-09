package Statements;

import Heap.Heap;
import Heap.IHeap;
import LockTable.ILockTable;
import LockTable.LockTable;
import MyDictionary.IMyDictionary;
import MyDictionary.MyDictionary;
import MyList.IMyList;
import MyList.MyList;
import MyStack.IMyStack;
import MyStack.MyStack;
import Tuple.Tuple;

import java.io.BufferedReader;
import java.util.*;

public class ProgramState {

    private ILockTable lockTable;
    private IMyDictionary<String, Integer> lookupTable;
    private IMyStack<IStatement> exeStack;
    private IMyList<String> output;
    private IMyDictionary<Integer, Tuple<String, BufferedReader>> fileTable;
    private IHeap<Integer> heap;
    private IStatement originalProblem;
    private int id;
    private static int firstId = 1;



    public ProgramState(IMyStack<IStatement> exe, IMyDictionary<String, Integer> sym, IMyList<String> out){
        exeStack = exe;
        lookupTable = sym;
        output = out;
        originalProblem = exeStack.peek().deepCopy();
        id = firstId++;
    }
    public ProgramState(IStatement originalProblem){
        this.originalProblem = originalProblem.deepCopy();
        exeStack = new MyStack<IStatement>();
        exeStack.push(originalProblem);
        lookupTable = new MyDictionary<String, Integer>();
        output = new MyList<String>();
        fileTable = new MyDictionary<Integer, Tuple<String, BufferedReader>>();
        heap = new Heap<Integer>();
        lockTable = new LockTable();
        id = firstId++;

    }
    public IHeap<Integer> getHeap(){return heap;}
    public void setHeap(IHeap<Integer> heap){
        this.heap = heap;
    }
    public void setFileTable(IMyDictionary<Integer, Tuple<String, BufferedReader>> fileTable1){
        this.fileTable = fileTable1;
    }

    public ILockTable getLockTable() {return lockTable;}
    public IMyDictionary<Integer, Tuple<String, BufferedReader>> getFileTable(){
        return fileTable;
    }

    public IMyDictionary<String, Integer> getLookupTable(){
        return lookupTable;
    }

    public void setLookupTable(IMyDictionary<String, Integer> look){
        lookupTable = look;
    }

    public IMyStack<IStatement> getExeStack(){
        return exeStack;
    }
    public void setExeStack(IMyStack<IStatement> exe){
        exeStack = exe;
    }

    public void setOutput(IMyList<String> out){
        output = out;
    }
    public IStatement getOriginalProblem(){
        return originalProblem;
    }

    public boolean isCompleted(){
        return exeStack.empty();
    }

    public ProgramState oneStep(){
        if(isCompleted()){
            return null;
        }
        IStatement st = this.getExeStack().pop();
        return st.execute(this);
    }

    public String toString(){
        String ret = "Statements: ";
        ListIterator<IStatement> it = (ListIterator<IStatement>)exeStack.iterator(exeStack.size() );
        while(it.hasPrevious()){
            IStatement st = it.previous();
            ret = ret + " " + st.toString() + "\n";
        }
        return ret;
    }

    public String logLookupTable(){
        StringBuilder repr = new StringBuilder("ID: " + id + "\nLookup Table:\n");
        Iterator<String> it = lookupTable.iterator();
        while (it.hasNext()){
            String key = it.next();
            int val = lookupTable.get(key);
            repr.append(key).append(" -> ").append(val).append("\n");
        }
        return repr.toString();
    }

    public String logHeap(){
        StringBuilder repr = new StringBuilder("Heap :\n");
        Iterator<Integer> it = heap.iterator();
        while (it.hasNext()){
            Integer key = it.next();
            Integer val = heap.get(key);
            repr.append(key).append(" -> ").append(val).append("\n");
        }
        return repr.toString();
    }

    public String logExeStack(){
        StringBuilder repr = new StringBuilder("Execution Stack:\n");
        ListIterator<IStatement> it = (ListIterator<IStatement>)exeStack.iterator(exeStack.size() );
        while(it.hasPrevious()){
            IStatement st = it.previous();
            repr.append(st.toString()).append(";").append("\n");
        }
        return repr.toString();
    }

    public String logFileTable(){
        StringBuilder repr = new StringBuilder("File table:\n");
        Iterator<Integer> it = fileTable.iterator();
        while (it.hasNext()){
            int key = it.next();
            String file = fileTable.get(key).x;
            repr.append(key).append(" -> ").append(file).append("\n");
        }
        return repr.toString();
    }

    public String logOutput(){
        StringBuilder repr= new StringBuilder("Output:\n");
        Iterator<String> it = output.iterator();
        while (it.hasNext()){
            String val = it.next();
            repr.append(val).append("\n");
        }
        return repr.toString();
    }

    public IMyList<String> getOutput(){
        return output;
    }
    public boolean isFinished(){
        if(exeStack.empty())
            return true;
        return false;
    }
    public int getID(){
        return id;
    }
}
