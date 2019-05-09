package Expressions;

import Heap.IHeap;
import MyDictionary.IMyDictionary;
import Exception.ExpressionException;
public class ArithExpr extends Expression{

    private Expression e1;
    private Expression e2;
    private ArithOperation op;

    public ArithExpr(Expression ex1, ArithOperation op, Expression ex2){
        this.e1 = ex1;
        this.e2 = ex2;
        this.op = op;
    }

    public int eval(IMyDictionary<String, Integer> lookupTbl, IHeap<Integer> heap){
        switch (op){
            case ADD:
                return e1.eval(lookupTbl, heap) + e2.eval(lookupTbl, heap);
            case SUB:
                return e1.eval(lookupTbl, heap) - e2.eval(lookupTbl, heap);
            case MUL:
                return e1.eval(lookupTbl, heap) * e2.eval(lookupTbl, heap);
            case DIV:
                if (e2.eval(lookupTbl, heap) == 0)
                    throw new ExpressionException("Division by 0 in ArithExpr: " + toString());
                return e1.eval(lookupTbl, heap) / e1.eval(lookupTbl, heap);
                default:
                    return 0;
        }
    }

    public String toString(){
        return e1.toString() + " " + op.toString() + " " + e2.toString();
    }
}
