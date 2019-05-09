package Statements;

import Expressions.Expression;
import Heap.IHeap;
import MyDictionary.IMyDictionary;
import MyStack.IMyStack;

public class WhileStatement implements IStatement{

    private IStatement st;
    private Expression ex;
    public WhileStatement(Expression ex, IStatement st){
        this.st = st;
        this.ex = ex;
    }

    public String toString(){
        return "While ( " + ex.toString() + " )\n" + st.toString();
    }
    public ProgramState execute(ProgramState prg){

        IMyDictionary<String, Integer> lookupTable =  prg.getLookupTable();
        IHeap<Integer> heap = prg.getHeap();
        if(ex.eval(lookupTable, heap) == 0){
            return prg;
        }
        IMyStack<IStatement> stack  = prg.getExeStack();
        stack.push(this);
        stack.push(st);
        return null;
    }

    public IStatement deepCopy(){
        return new WhileStatement(ex, st.deepCopy());
    }
}
