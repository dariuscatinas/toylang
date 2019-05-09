package Statements;

import LockTable.ILockTable;
import Exception.LockException;
import MyStack.IMyStack;

public class LockEnterStatement implements IStatement{

    private int number;

    public LockEnterStatement(int nr){
        this.number = nr;
    }

    public IStatement deepCopy(){
        return new LockEnterStatement(number);
    }
    public String toString(){
        return "lockEnter( " + number  + " )";
    }
    public ProgramState execute(ProgramState prg){

        ILockTable lockTable = prg.getLockTable();
        IMyStack<IStatement> exeStack = prg.getExeStack();
        if(!lockTable.isKey(number)){
            throw new LockException("Lock number out of range");
        }
        synchronized (lockTable) {
            if (lockTable.checkValue(number) == 0) {
                lockTable.modify(number, prg.getID());
            } else {
                exeStack.push(this);
            }
        }
        return null;

    }
}
