package Statements;

import Expressions.Expression;
import Heap.IHeap;
import MyDictionary.IMyDictionary;
import Exception.*;
public class WriteHeapStatement implements IStatement{
    private String pointer;
    private Expression ex;

    public WriteHeapStatement(String pointer, Expression ex){
        this.pointer = pointer;
        this.ex = ex;
    }

    public ProgramState execute(ProgramState prg){
        IMyDictionary<String, Integer> lookupTable = prg.getLookupTable();
        IHeap<Integer> heap = prg.getHeap();
        int memVal = ex.eval(lookupTable, heap);
        if(!lookupTable.containsKey(pointer)){
            throw new PointerException("Cannot write to pointer: " + pointer);
        }
        int ptrVal = lookupTable.get(pointer);
        if(!heap.containsKey(ptrVal)){
            throw new PointerException("Pointer space not allocated: " + pointer);
        }
        heap.put(ptrVal, memVal);
        return null;
    }
    public IStatement deepCopy(){
        return new WriteHeapStatement(pointer, ex);
    }
    public String toString(){
        return "*" + pointer + " = " + ex.toString()+ ";";
    }
}
