package Statements;

import Heap.IHeap;
import Expressions.Expression;
import MyDictionary.IMyDictionary;

public class NewStatement implements IStatement{

    private String pointer;
    private Expression ex;

    public NewStatement(String pointer, Expression ex){
        this.pointer = pointer;
        this.ex = ex;
    }
    public ProgramState execute(ProgramState prg){
        IHeap<Integer> heap = prg.getHeap();
        IMyDictionary<String, Integer> lookupTable = prg.getLookupTable();
        int memValue = ex.eval(lookupTable, heap);
        int memPtr = heap.put(memValue);
        lookupTable.put(pointer, memPtr);
        return null;
    }
    public IStatement deepCopy(){
        return new NewStatement(pointer, ex);
    }
    public String toString(){
        return "new( " + pointer+ " ," + ex.toString() + " );";
    }
}
