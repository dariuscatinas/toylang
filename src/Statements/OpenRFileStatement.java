package Statements;

import MyDictionary.IMyDictionary;
import Tuple.Tuple;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import Exception.FileException;

public class OpenRFileStatement implements IStatement{

    private String filename;
    private String var;

    public OpenRFileStatement(String var, String filename){
        this.var = var;
        this.filename = filename;
    }

    public IStatement deepCopy(){
        return new OpenRFileStatement(var, filename);
    }

    public String toString(){
        return "open(" + var + " , "+ filename+")";
    }

    private boolean checkFileExistence(ProgramState prg, String file){

        Iterator<Integer> it = prg.getFileTable().iterator();
        while(it.hasNext()){
            int key = it.next();
            Tuple<String, BufferedReader> val = prg.getFileTable().get(key);
            if(val.x.equals(file)){
                return true;
            }
        }
        return false;
    }

    private int getFirstFd(ProgramState prg){

        IMyDictionary<Integer, Tuple<String, BufferedReader>> fileTable = prg.getFileTable();
        int i = 0;
        boolean foundInteger = false;
        while (!foundInteger){
            boolean present = fileTable.containsKey(i);
            i++;
            if(!present)
                foundInteger = true;
        }
        return --i;
    }

    public ProgramState execute(ProgramState prg){

        BufferedReader fileReader;
        if(checkFileExistence(prg, filename)){
            throw new FileException("File: " + filename + " already opened");
        }
        try{
            fileReader = new BufferedReader(new FileReader(filename));
        }
        catch(IOException ex){
            throw new FileException("File: " + filename + " cannot be opened");
        }
        IMyDictionary<Integer, Tuple<String, BufferedReader>> fileTable = prg.getFileTable();
        int fileD = getFirstFd(prg);
        fileTable.put(fileD, new Tuple<String, BufferedReader>(filename, fileReader));
        prg.getLookupTable().put(var, fileD);
        return null;

    }
}
