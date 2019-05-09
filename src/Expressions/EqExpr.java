package Expressions;

import Heap.IHeap;
import MyDictionary.IMyDictionary;

public class EqExpr extends Expression{

    private Expression ex1, ex2;
    public EqExpr(Expression e1, Expression e2){
        this.ex1 = e1;
        this.ex2 = e2;
    }

    public int eval(IMyDictionary<String, Integer> lookupTable, IHeap<Integer> heap){
        if(ex1.eval(lookupTable, heap) == ex2.eval(lookupTable, heap)){
            return 1;
        }
        return 0;
    }
    public String toString(){
        return ex1.toString()  + " == " + ex2.toString();
    }
}