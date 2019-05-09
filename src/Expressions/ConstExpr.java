package Expressions;

import Heap.IHeap;
import MyDictionary.IMyDictionary;

public class ConstExpr extends Expression {

    private int nr;

    public ConstExpr(int nr){
        this.nr = nr;
    }
    public int eval(IMyDictionary<String, Integer> lookupTbl, IHeap<Integer> heap){
        return nr;
    }
    public String toString(){
        return nr + "";
    }
}
