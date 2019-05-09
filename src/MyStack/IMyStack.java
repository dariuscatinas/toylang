package MyStack;

import java.util.Iterator;

public interface IMyStack<E> {
    E pop();
    E push(E elem);
    E peek();
    boolean empty();
    int search(E elem);
    Iterator<E> iterator();
    Iterator<E> iterator(int index);
    int size();
}
