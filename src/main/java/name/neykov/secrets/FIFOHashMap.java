package name.neykov.secrets;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class FIFOHashMap<K, V> {

    private final Map<K, V> map;
    private final Queue<K> keyQueue;
    private int maxSize;

    public FIFOHashMap(int maxSize) {
        this.maxSize = maxSize;
        this.map = new HashMap<K, V>();
        this.keyQueue = new LinkedList<K>();
    }

    public synchronized void put(K key, V value) {
        if (map.size() >= maxSize) {
            K oldestKey = keyQueue.poll();
            map.remove(oldestKey);
        }
        map.put(key, value);
        keyQueue.add(key);
    }

    public synchronized V get(K key) {
        return map.get(key);
    }

    public synchronized void remove(K key) {
        map.remove(key);
        keyQueue.remove(key);
    }

    public synchronized void setSizeLimit(int newSize) {
        maxSize = newSize;
        while (map.size() > maxSize) {
            K oldestKey = keyQueue.poll();
            map.remove(oldestKey);
        }
    }

    public synchronized int getSizeLimit() {
        return maxSize;
    }

    public synchronized int getSize() {
        return map.size();
    }
}
