package MyDictionary;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public interface IMyDictionary<K, V> {
    int size();

    boolean isEmpty();

    V get(K key);

    boolean containsKey(K key);

    boolean containsValue(V val);

    V put(K key, V val);

    V remove(K key);

    void clear();

    Iterator<K> iterator();

    void setContent(Map<K, V> map);

    Set<Map.Entry<K, V>> entrySet();

    IMyDictionary<K, V> cloneDict();

    Map<K, V> toMap();
}
