package MyDictionary;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import Exception.ADTException;

public class MyDictionary<K, V> implements IMyDictionary<K, V>{

    private Map<K, V> dict;

    public MyDictionary(){
        dict = new HashMap<K, V>(100, (float)0.75);
    }
    public int size(){
        return dict.size();
    }
    public boolean isEmpty(){
        return dict.isEmpty();
    }
    public V get(K key){
        if (!containsKey(key)){
            throw new ADTException("Key not present.");
        }
        return dict.get(key);
    }
    private void setMap(Map<K, V> map){
        dict = map;
    }
    public boolean containsKey(K key){
        return dict.containsKey(key);
    }
    public V put(K key, V val){
        return dict.put(key, val);
    }
    public V remove(K key){
        return dict.remove(key);
    }
    public boolean containsValue(V val){
        return dict.containsValue(val);
    }
    public void clear(){
        dict.clear();
    }
    public Iterator<K> iterator(){
        return dict.keySet().iterator();
    }
    public void setContent(Map<K, V> map){
        this.dict = map;
    }
    public Set<Map.Entry<K, V>> entrySet(){
        return dict.entrySet();
    }
    public IMyDictionary<K, V> cloneDict(){
        MyDictionary<K, V> newDict = new MyDictionary<K, V>();
        newDict.setMap(dict.entrySet().stream().collect(Collectors.toMap(d->d.getKey(), d->d.getValue())));
        return newDict;
    }
    public Map<K, V> toMap(){return dict;}
}
