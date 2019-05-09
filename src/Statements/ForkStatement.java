package Statements;

import MyDictionary.IMyDictionary;

public class ForkStatement implements IStatement {
    private IStatement st;

    public ForkStatement(IStatement st){
        this.st = st;
    }

    public ProgramState execute(ProgramState prg){

        ProgramState newPrg = new ProgramState(st.deepCopy());
        newPrg.setLookupTable(prg.getLookupTable().cloneDict());
        newPrg.setHeap(prg.getHeap());
        newPrg.setOutput(prg.getOutput());
        newPrg.setFileTable(prg.getFileTable());
        return newPrg;
    }

    public IStatement deepCopy(){
        return new ForkStatement(st.deepCopy());
    }

    public String toString(){
        return "fork( " + st.toString() + " )";
    }

}
