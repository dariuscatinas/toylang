package Expressions;

import Heap.IHeap;
import MyDictionary.IMyDictionary;
import Exception.ExpressionException;

public class VarExpr extends Expression {
    private String var;
    public VarExpr(String var){
        this.var = var;
    }
    public int eval(IMyDictionary<String, Integer> lookupTbl, IHeap<Integer> heap){
        if (! lookupTbl.containsKey(var)){
            throw new ExpressionException("Variable " + var + " not present in lookupTable");
        }
        return lookupTbl.get(var);
    }
    public String toString(){
        return var;
    }
}
