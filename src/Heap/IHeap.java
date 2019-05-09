package Heap;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public interface IHeap<V> {
    boolean isEmpty();
    V get(int key);
    boolean containsKey(int key);
    int put(V val);
    int put(int key, V val);
    Iterator<Integer> iterator();
    void remove(int key);
    void setContent(Map<Integer, V> newHeap);
    Set<Map.Entry<Integer, V>> entrySet();
    public Map<Integer, V> toMap();
}
