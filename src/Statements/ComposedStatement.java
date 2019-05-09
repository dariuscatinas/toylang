package Statements;

import MyStack.IMyStack;

public class ComposedStatement implements  IStatement{

    private IStatement st1;
    private IStatement st2;

    public ComposedStatement(IStatement s1, IStatement s2){
        st1 = s1;
        st2 = s2;
    }
    public ProgramState execute(ProgramState prg){
        IMyStack<IStatement> stack = prg.getExeStack();
        stack.push(st2);
        stack.push(st1);
        return null;
    }
    public String toString(){
        return  st1.toString()+"\n" + st2.toString()+"\n";
    }
    public IStatement deepCopy(){
        return new ComposedStatement(st1.deepCopy(), st2.deepCopy());
    }
}
