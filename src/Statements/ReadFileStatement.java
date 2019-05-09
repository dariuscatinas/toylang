package Statements;

import Expressions.Expression;
import MyDictionary.IMyDictionary;
import Tuple.Tuple;

import java.io.BufferedReader;
import java.io.IOException;

import Exception.FileException;

public class ReadFileStatement implements IStatement{
    private Expression exp;
    private String var;

    public ReadFileStatement(Expression exp, String var){
        this.exp = exp;
        this.var = var;
    }

    public IStatement deepCopy(){
        return new ReadFileStatement(exp, var);
    }

    public String toString(){
        return "read(" + exp.toString() + ", " + var + ")";
    }

    public ProgramState execute(ProgramState prg){

        int fd = exp.eval(prg.getLookupTable(), prg.getHeap());
        IMyDictionary<Integer, Tuple<String, BufferedReader>> fileTable = prg.getFileTable();
        IMyDictionary<String, Integer> lookupTable = prg.getLookupTable();
        BufferedReader reader = fileTable.get(fd).y;

        try {
            if(! fileTable.containsKey(fd)){
                throw new FileException("Cannot find file from file descriptor: " + fd);
            }
            String line = reader.readLine();
            int readValue = 0;
            if (!line.equals("")) {
                readValue = Integer.parseInt(line);
            }
            lookupTable.put(var, readValue);
            return null;
        }
        catch (IOException ex){
            throw new FileException("Cannot read from file!");
        }
    }
}
