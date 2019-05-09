package Statements;

import Expressions.Expression;
import MyList.IMyList;

public class PrintStatement implements IStatement{

    private Expression exp;

    public PrintStatement(Expression exp){
        this.exp = exp;
    }

    public IStatement deepCopy(){
        return new PrintStatement(exp);
    }
    public String toString(){
        return "Print: " + exp.toString();
    }
    public ProgramState execute(ProgramState prg){
        IMyList<String> out = prg.getOutput();
        out.add(exp.eval(prg.getLookupTable(), prg.getHeap()) + "");
        return null;
    }
}
