package Repo;


import Statements.ProgramState;
import Exception.RepoException;
import MyList.*;

import java.io.*;

public class Repo implements IRepo {
    private IMyList<ProgramState> programmes;
    private String logFile;
    private PrintWriter file;

    public Repo(ProgramState prg) {
        programmes = new MyList<ProgramState>();
        programmes.add(prg);

    }

    public Repo(ProgramState prg, String logFile) {
        this(prg);
        this.logFile = logFile;
        try {
            FileWriter fw = new FileWriter(logFile, true);
            BufferedWriter bw = new BufferedWriter(fw);
            file = new PrintWriter(bw);
        } catch (IOException ex) {
            throw new RepoException("Cannot open file " + logFile);
        }
    }

    public void setPrg(IMyList<ProgramState> data) {
        programmes = data;
    }

    public IMyList<ProgramState> getPrg() {
        return programmes;
    }

    public ProgramState getPrgIndex(int idx) {
        if (idx > programmes.size()) {
            throw new RepoException("Index of program count out of range");
        }
        return programmes.get(idx);
    }

    public void logPrgState(ProgramState prg) {

        try {
            file = new PrintWriter(new BufferedWriter(new FileWriter(logFile, true)));
        } catch (IOException ex) {
            throw new RepoException("");
        }
        file.print("-------------------------\n");
        file.print(prg.logExeStack());
        file.print(prg.logLookupTable());
        file.print(prg.logHeap());
        file.print(prg.logOutput());
        file.print(prg.logFileTable());
        file.print("-------------------------\n");
        file.flush();
        file.close();
    }
}


