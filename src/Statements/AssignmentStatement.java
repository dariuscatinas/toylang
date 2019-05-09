package Statements;

import Expressions.Expression;
import MyDictionary.IMyDictionary;

public class AssignmentStatement implements IStatement{

    private String var;
    private Expression exp;

    public AssignmentStatement(String var, Expression ex){
        this.exp = ex;
        this.var = var;
    }
    public IStatement deepCopy(){
        return new AssignmentStatement(var, exp);
    }
    public String toString(){
        return  var + " = " + exp.toString()+ ";";
    }
    public ProgramState execute(ProgramState prg){
        int rez = exp.eval(prg.getLookupTable(), prg.getHeap());
        IMyDictionary<String, Integer> lookTbl = prg.getLookupTable();
        if (lookTbl.containsKey(var)) {
            lookTbl.remove(var);
            lookTbl.put(var, rez);
        }
        lookTbl.put(var, rez);
        return null;
    }
}
