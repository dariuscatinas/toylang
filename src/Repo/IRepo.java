package Repo;

import Statements.ProgramState;
import MyList.IMyList;

public interface IRepo {
    IMyList<ProgramState> getPrg();
    ProgramState getPrgIndex(int index);
    void setPrg(IMyList<ProgramState> data);
    void logPrgState(ProgramState prg);
}
