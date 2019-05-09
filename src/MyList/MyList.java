package MyList;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Exception.*;

public class MyList<E> implements IMyList<E>{
    private List<E> lst;

    public MyList() { lst = new ArrayList<>();}
    public MyList(List<E> el) { lst = el; }
    public boolean add(E elem){
        return lst.add(elem);
    }
    public List<E> toList(){
        return lst;
    }
    public int size(){
        return lst.size();
    }
    public void add(int idx, E elem){
        if (idx < 0 || idx > lst.size() - 1){
            throw new ADTException("Cannot add on specified position.");
        }
        lst.add(idx, elem);
    }
    public void clear(){
        lst.clear();
    }
    public boolean isEmpty(){
        return lst.isEmpty();
    }
    public E get(int idx){
        if (idx < 0 || idx > lst.size() - 1){
            throw new ADTException("Cannot get element on specified out-of-range index.");
        }
        return lst.get(idx);
    }
    public int indexOf(E elem){
        return lst.indexOf(elem);
    }
    public E remove(int idx){
        if (idx < 0 || idx > lst.size() - 1) {
            throw new ADTException("Cannot remove element on specified out-of-range index.");
        }
        return lst.remove(idx);
    }
    public boolean remove(E elem){
        return lst.remove(elem);
    }
    public E set(int idx, E elem) {
        return lst.set(idx, elem);

    }
    public Iterator<E> iterator(){ return lst.iterator();}
}
