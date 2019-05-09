package MyStack;
import java.util.Iterator;
import java.util.Stack;
import Exception.*;
public class MyStack<T> implements IMyStack<T>{

    private Stack<T> stack;

    public MyStack(){
        this.stack = new Stack<T>();
    }
    public T pop(){
        if (stack.size() == 0){
            throw new ADTException("Stack underflow");
        }
        return stack.pop();
    }
    public T push(T elem){
        return stack.push(elem);
    }
    public T peek(){
        return stack.peek();
    }
    public boolean empty(){
        return stack.empty();
    }
    public int search(T elem){
        return stack.search(elem) - 1;
    }
    public Iterator<T> iterator() {return stack.iterator();}
    public Iterator<T> iterator(int index){
        return stack.listIterator(index);
    }
    public int size(){
        return stack.size();
    }
}
