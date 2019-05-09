package Statements;

import Expressions.Expression;

public class IfStatement implements IStatement{

    private Expression ex;
    private IStatement st1;
    private  IStatement st2;

    public IfStatement(Expression ex, IStatement st1, IStatement st2){
        this.ex = ex;
        this.st1 = st1;
        this.st2 = st2;
    }

    public ProgramState execute(ProgramState prg){
        if (ex.eval(prg.getLookupTable(), prg.getHeap()) != 0){
            prg.getExeStack().push(st1);
        }
        else{
            prg.getExeStack().push(st2);
        }
        return null;
    }

    public IStatement deepCopy(){
        return new IfStatement(ex, st1.deepCopy(), st2.deepCopy());
    }

    public String toString(){
        return "If ( " + ex.toString() + " )\n" + "\tthen: " + st1.toString() + "\nelse: " + st2.toString();
    }
}
