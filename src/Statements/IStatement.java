package Statements;

public interface IStatement {

    IStatement deepCopy();
    String toString();
    ProgramState execute(ProgramState prg);
}
