package LockTable;

import java.util.HashMap;
import java.util.Map;


public class LockTable implements ILockTable{

    private Map<Integer, Integer> lockMap;

    public LockTable(){
        lockMap = new HashMap<Integer, Integer>();
        lockMap.put(0, 0); lockMap.put(1,0); lockMap.put(2,0);

    }

    public boolean isKey(int nr){
        return lockMap.containsKey(nr);
    }

    public int  checkValue(int nr){
        return lockMap.get(nr);
    }

    public void modify(int nr, int prgID){
        lockMap.remove(nr);
        lockMap.put(nr, prgID);
    }

    @Override
    public Map<Integer, Integer> toMap() {
        return lockMap;
    }
}
