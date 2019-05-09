package Heap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import Exception.*;
public class Heap<V> implements IHeap<V> {

    private int firstAddress;
    private Map<Integer, V> heap;
    public Heap(){
        firstAddress = 1;
        heap = new HashMap<>();
    }

    public boolean isEmpty(){
        return heap.isEmpty();
    }
    public V get(int key){
        return heap.get(key);
    }
    public boolean containsKey(int key){
        return heap.containsKey(key);
    }
    public int put(V val){
        heap.put(firstAddress, val);
        firstAddress++;
        return firstAddress-1;
    }
    public void remove(int key){
        heap.remove(key);
    }

    public int put(int key, V val){
        if(heap.containsKey(key)) {
            heap.put(key, val);
            return key;
        }
        throw new ADTException("Heap key(pointer) " + key + " not allocated");
    }

    public void setContent(Map<Integer, V> newHeap){
        this.heap = newHeap;
    }
    public Set<Map.Entry<Integer, V>> entrySet(){
        return heap.entrySet();
    }
    public Iterator<Integer> iterator(){
        return heap.keySet().iterator();
    }
    public Map<Integer, V> toMap(){return heap;}
}
