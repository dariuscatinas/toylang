package Expressions;

import Heap.IHeap;
import MyDictionary.IMyDictionary;

public abstract class Expression {
    public abstract int eval(IMyDictionary<String, Integer> symTbl, IHeap<Integer> heap);
    public abstract String toString();
}
