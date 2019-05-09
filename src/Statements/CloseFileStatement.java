package Statements;

import Expressions.Expression;
import MyDictionary.IMyDictionary;
import Tuple.Tuple;
import Exception.FileException;
import java.io.BufferedReader;
import java.io.IOException;

public class CloseFileStatement implements IStatement{
    private Expression exp;
    public CloseFileStatement(Expression ex){
        this.exp = ex;
    }
    public IStatement deepCopy(){
        return new CloseFileStatement(exp);
    }
    public String toString(){
        return "close(" + exp.toString() + " )";
    }
    public ProgramState execute(ProgramState prg){
        IMyDictionary<Integer, Tuple<String, BufferedReader>> fileTable = prg.getFileTable();
        int fd = exp.eval(prg.getLookupTable(), prg.getHeap());
        if( !fileTable.containsKey(fd)){
            throw new FileException("Cannot close file descriptor: " + fd );
        }
        BufferedReader reader = fileTable.get(fd).y;
        try {
            reader.close();
        }
        catch (IOException ex){
            throw new FileException("Cannot close file descriptor due to internal problems: " + fd);
        }
        fileTable.remove(fd);
        return null;
    }
}

