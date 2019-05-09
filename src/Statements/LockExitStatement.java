package Statements;

import LockTable.ILockTable;

public class LockExitStatement implements IStatement{
    private int number;

    public LockExitStatement(int nr){
        this.number = nr;
    }

    public IStatement deepCopy(){
        return new LockExitStatement(number);
    }

    public String toString(){
        return "lockExit( " + number + ")";
    }

    public ProgramState execute(ProgramState prg){
        ILockTable lockTable = prg.getLockTable();
        if( !lockTable.isKey(number)){
            return null;
        }
        synchronized (lockTable) {
            if (lockTable.checkValue(number) == prg.getID()) {
                lockTable.modify(number, 0);
            }
        }
        return null;
    }


}
