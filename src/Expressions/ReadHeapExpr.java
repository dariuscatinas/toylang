package Expressions;

import Heap.IHeap;
import MyDictionary.IMyDictionary;
import Exception.*;

public class ReadHeapExpr extends Expression {

    private String pointer;

    public ReadHeapExpr(String pointer) {
        this.pointer = pointer;
    }

    public int eval(IMyDictionary<String, Integer> lookupTable, IHeap<Integer> heap) {
        if (!lookupTable.containsKey(pointer)) {
            throw new PointerException("Pointer: " + pointer + " not present in lookupTable");
        }
        int ptrVal = lookupTable.get(pointer);
        if (!heap.containsKey(ptrVal)) {
            throw new PointerException("Pointer: " + pointer + " was garbage collected / corrupted");
        }
        return heap.get(ptrVal);
    }

    public String toString() {
        return "*" + pointer;
    }
}