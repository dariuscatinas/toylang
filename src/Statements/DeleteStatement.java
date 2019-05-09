package Statements;

import Heap.IHeap;
import MyDictionary.IMyDictionary;
import Exception.*;
public class DeleteStatement implements IStatement{
    private String pointer;

    public DeleteStatement(String pointer){
        this.pointer = pointer;
    }

    public ProgramState execute(ProgramState prg){
        IMyDictionary<String, Integer> lookupTable = prg.getLookupTable();
        IHeap<Integer> heap = prg.getHeap();
        if(!lookupTable.containsKey(pointer)){
            throw new PointerException("Cannot delete pointer: " + pointer);
        }
        int ptrVal = lookupTable.get(pointer);
        if(!heap.containsKey(ptrVal)){
            throw new PointerException("Pointer invalid: " + pointer);
        }
        heap.remove(ptrVal);
        return null;
    }
    public IStatement deepCopy(){
        return new DeleteStatement(pointer);
    }
}
