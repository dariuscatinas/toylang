package LockTable;

import java.util.Map;

public interface ILockTable {

    boolean isKey(int nr);
    void modify(int nr, int prgID);
    int checkValue(int nr);
    Map<Integer, Integer> toMap();
}

