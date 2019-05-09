package MyList;

import java.util.Iterator;
import java.util.List;

public interface IMyList<E> {
    boolean add(E elem);
    void add(int idx, E elem);
    void clear();
    boolean isEmpty();
    E get(int idx);
    E set(int idx, E elem);
    int indexOf(E elem);
    E remove(int idx);
    boolean remove(E elem);
    int size();
    Iterator<E> iterator();
    List<E> toList();

}


