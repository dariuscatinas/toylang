package Controller;
import Controller.Controller;
import Expressions.*;
import Repo.IRepo;
import Repo.Repo;
import Statements.*;
import View.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

// a = 9; b = 6; if ( a ) then b = b + 6, else b = a / 3; print b;
// a = 3; a = a / (3 - 3); print a;

public class Creator {

    public static List<Controller> createCommands(){

        List<Controller> commands = new ArrayList<>();

        ProgramState prg = testFileState1();
        IRepo r1 = new Repo(prg, "logFile1.txt");
        Controller c1 = new Controller(r1);

        commands.add(c1);

        ProgramState prg2 = stateGenerator();
        IRepo r2 = new Repo(prg2, "logFile2.txt");
        Controller c2 = new Controller(r2);
        commands.add(c2);

        ProgramState prg3 = stateZeroDiv();
        IRepo r3 = new Repo(prg3, "logFile3.txt");
        Controller c3 = new Controller(r3);
        commands.add(c3);

        ProgramState prg4 = testFileState2();
        IRepo r4 = new Repo(prg4, "logFile4.txt");
        Controller c4 = new Controller(r4);
        commands.add(c4);

        ProgramState prg5 = testHeap1();
        IRepo r5 = new Repo(prg5, "logFile5.txt");
        Controller c5 = new Controller(r5);
        commands.add(c5);

        ProgramState prg6 = stateHeapAlloc();
        IRepo r6 = new Repo(prg6, "logFile6.txt");
        Controller c6 = new Controller(r6);
        commands.add(c6);

        ProgramState prg7 = stateHeapRead();
        IRepo r7 = new Repo(prg7, "logFile7.txt");
        Controller c7 = new Controller(r7);
        commands.add(c7);

        ProgramState prg8 = stateHeapWrite();
        IRepo r8 = new Repo(prg8, "logFile8.txt");
        Controller c8 = new Controller(r8);
        commands.add(c8);

        ProgramState prg9 = testWhile1();
        IRepo r9 = new Repo(prg9, "logFile9.txt");
        Controller c9 = new Controller(r9);
        commands.add(c9);

        ProgramState prg10 = testFork();
        IRepo r10 = new Repo(prg10, "logFile10.txt");
        Controller c10 = new Controller(r10);
        commands.add(c10);

        ProgramState prg11 = testLock();
        IRepo r11 = new Repo(prg11, "logFile11.txt");
        Controller c11 = new Controller(r11);
        commands.add(c11);

        return commands;
    }

    private static ProgramState stateHeapWrite(){
        IStatement assV = new AssignmentStatement("v", new ConstExpr(10));
        IStatement newV = new NewStatement("v", new ConstExpr(20));
        IStatement newA = new NewStatement("a", new ConstExpr(22));
        IStatement writeH =  new WriteHeapStatement("a", new ConstExpr(30));
        IStatement printA = new PrintStatement(new VarExpr("a"));
        IStatement printRHA = new PrintStatement(new ReadHeapExpr("a"));
        IStatement prg = new ComposedStatement(assV, new ComposedStatement(newV,
                new ComposedStatement(newA, new ComposedStatement(writeH,
                        new ComposedStatement(printA, printRHA)))));
        return new ProgramState(prg);

    }

    private static ProgramState stateHeapAlloc(){
        IStatement assV = new AssignmentStatement("v", new ConstExpr(10));
        IStatement newV = new NewStatement("v", new ConstExpr(20));
        IStatement newA = new NewStatement("a", new ConstExpr(22));
        IStatement printV = new PrintStatement(new VarExpr("v"));
        IStatement prg = new ComposedStatement(assV, new ComposedStatement(
                newV, new ComposedStatement(newA, printV)));
        return new ProgramState(prg);
    }
    private static ProgramState stateHeapRead(){
        IStatement assV = new AssignmentStatement("v", new ConstExpr(10));
        IStatement newV = new NewStatement("v", new ConstExpr(20));
        IStatement newA = new NewStatement("a", new ConstExpr(22));
        IStatement printRhV = new PrintStatement(new ArithExpr(new ConstExpr(100), ArithOperation.ADD, new ReadHeapExpr("v")));
        IStatement printRhA = new PrintStatement(new ArithExpr(new ConstExpr(100), ArithOperation.ADD, new ReadHeapExpr("a")));
        IStatement prg = new ComposedStatement(assV, new ComposedStatement(newV,
                new ComposedStatement(newA, new ComposedStatement(printRhV,
                        printRhA))));
        return new ProgramState(prg);
    }

    private static ProgramState stateGenerator(){
        IStatement lastPrint = new PrintStatement(new VarExpr("b"));
        IStatement ifA = new IfStatement(new VarExpr("a"),
                    new AssignmentStatement("b", new ArithExpr(new VarExpr("b"), ArithOperation.ADD, new ConstExpr(6))),
                    new AssignmentStatement("b", new ArithExpr(new VarExpr("a"), ArithOperation.DIV, new ConstExpr(3))));
        IStatement assB = new AssignmentStatement("b", new ConstExpr(6));
        IStatement assA = new AssignmentStatement("a", new ConstExpr(9));
        IStatement problem = new ComposedStatement(assA,
                new ComposedStatement(assB,
                new ComposedStatement(ifA, lastPrint)));
        return new ProgramState(problem);
    }
    private static ProgramState stateZeroDiv(){
        IStatement printA = new PrintStatement(new VarExpr("a"));
        IStatement assignA = new AssignmentStatement("a",
                              new ArithExpr(new VarExpr("a"), ArithOperation.DIV,
                                      new ArithExpr(new ConstExpr(3), ArithOperation.SUB, new ConstExpr(3))));
        IStatement originA = new AssignmentStatement("a", new ConstExpr(3));
        IStatement prbl = new ComposedStatement(originA,
                            new ComposedStatement(assignA, printA) );
        return new ProgramState(prbl);
    }

    private static ProgramState testFileState1(){

        IStatement openFile = new OpenRFileStatement("var_f", "test.in");
        IStatement readFile = new ReadFileStatement(new VarExpr("var_f"), "var_c");
        IStatement printC = new PrintStatement(new VarExpr("var_c"));
        IStatement ifstm = new IfStatement(new VarExpr("var_c"),
                new ComposedStatement(readFile.deepCopy(), printC.deepCopy()),
                new PrintStatement(new ConstExpr(0)));
        IStatement closeFile = new CloseFileStatement(new VarExpr("var_f"));
        IStatement prg = new ComposedStatement(openFile,
                new ComposedStatement(readFile,
                        new ComposedStatement(printC,
                                new ComposedStatement(ifstm,
                                       closeFile))));
        return new ProgramState(prg);


    }

    private static ProgramState testFileState2(){

        IStatement openFile = new OpenRFileStatement("var_f", "test.in");
        IStatement readFile = new ReadFileStatement(new ArithExpr(new VarExpr("var_f"), ArithOperation.ADD, new ConstExpr(2)), "var_c");
        IStatement printC = new PrintStatement(new VarExpr("var_c"));
        IStatement ifstm = new IfStatement(new VarExpr("var_c"),
                new ComposedStatement(readFile.deepCopy(), printC.deepCopy()),
                new PrintStatement(new ConstExpr(0)));
        IStatement closeFile = new CloseFileStatement(new VarExpr("var_f"));
        IStatement prg = new ComposedStatement(openFile,
                new ComposedStatement(readFile,
                        new ComposedStatement(printC,
                                new ComposedStatement(ifstm,
                                        closeFile))));
        return new ProgramState(prg);

    }

    private static ProgramState testHeap1(){
        IStatement assignV = new AssignmentStatement("v", new ConstExpr(10));
        IStatement newV = new NewStatement("v", new ConstExpr(20));
        IStatement newA = new NewStatement("a", new ConstExpr(22));
        IStatement writeA = new WriteHeapStatement("a", new ConstExpr(30));
        IStatement printStmtA = new PrintStatement(new VarExpr("a"));
        IStatement printStmtPtrA = new PrintStatement(new ReadHeapExpr("a"));
        IStatement nullA = new AssignmentStatement("a", new ConstExpr(0));
        IStatement prg = new ComposedStatement(assignV,
                            new ComposedStatement(newV,
                                    new ComposedStatement( newA,
                                            new ComposedStatement(writeA,
                                                    new ComposedStatement(printStmtA, new ComposedStatement(printStmtPtrA, nullA))))));
        return new ProgramState(prg);
    }

    private static ProgramState testWhile1(){
        IStatement assV = new AssignmentStatement("v", new ConstExpr(6));
        IStatement whileSt = new WhileStatement(new ArithExpr(new VarExpr("v"), ArithOperation.SUB, new ConstExpr(4)),
                new ComposedStatement(new PrintStatement(new VarExpr("v")), new AssignmentStatement("v",
                new ArithExpr(new VarExpr("v"), ArithOperation.SUB, new ConstExpr(1)))) );
        IStatement printV = new PrintStatement(new VarExpr("v"));
        IStatement prg = new ComposedStatement(assV, new ComposedStatement(whileSt, printV));
        return new ProgramState(prg);
    }

    private static ProgramState testFork(){
        IStatement assV = new AssignmentStatement("v", new ConstExpr(10));
        IStatement newA = new NewStatement("a", new ConstExpr(22));
        IStatement forkSt = new ForkStatement(new ComposedStatement(new WriteHeapStatement("a", new ConstExpr(30)),

                new ComposedStatement( new AssignmentStatement("v", new ConstExpr(32)),
                        new ComposedStatement(new PrintStatement(new VarExpr("v"))
                                , new PrintStatement(new ReadHeapExpr("a"))
                ))));
        IStatement prg = new ComposedStatement(assV, new ComposedStatement(newA, new ComposedStatement( forkSt,
                new ComposedStatement(new PrintStatement(new VarExpr("v")), new PrintStatement(new ReadHeapExpr("a"))))));
        return new ProgramState(prg);
    }

    private static ProgramState testLock(){
        IStatement assV = new AssignmentStatement("v", new ConstExpr(10));
        IStatement lockEnterMain = new LockEnterStatement(1);
        IStatement printV = new PrintStatement(new VarExpr("v"));

        //for fork
        IStatement assVFork = new AssignmentStatement("v", new ConstExpr(20));
        IStatement lockEnterFork = new LockEnterStatement(1);
        IStatement printVFork = new PrintStatement(new VarExpr("v"));
        IStatement lockExitFork = new LockExitStatement(1);
        IStatement forkBody = new ComposedStatement(assVFork, new ComposedStatement(lockEnterFork, new ComposedStatement(printVFork, lockExitFork)));
        IStatement forkSt = new ForkStatement(forkBody);
        //
        IStatement printVpl = new PrintStatement(new ArithExpr(new VarExpr("v"), ArithOperation.ADD, new ConstExpr(1)));
        IStatement lockExitMain = new LockExitStatement(1);

        IStatement prg = new ComposedStatement(assV,
                new ComposedStatement(lockEnterMain,
                new ComposedStatement(printV,
                new ComposedStatement(forkSt,
                        new ComposedStatement(printVpl, lockExitMain)))));
        return new ProgramState(prg);

    }
}
