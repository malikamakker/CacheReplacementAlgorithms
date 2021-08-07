import java.util.*;
class cacheElement {
    int value;
    int key;
    cacheElement next;
    cacheElement prev;
    
    public cacheElement(int key, int value) {
        this.value = value;
        this.key = key;
        this.next = this.prev = this;
    }å
}

class LRUCache {
    int capacity;
    int size;
    cacheElement cacheStart;
    HashMap<Integer,cacheElement> hash;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.hash = new HashMap<Integer, cacheElement>();
        this.cacheStart = null;
        this.size = 0;
    }
    
    public int get(int key) {
        cacheElement cacheValue = this.hash.get(key);
        if (cacheValue != null) {
            this.removeNode(cacheValue);
            this.hash.put(key, cacheValue);
            this.insertStart(cacheValue);
            return cacheValue.value;
        }
        return -1;
    }
    
    public void put(int key, int value) {
        cacheElement element = this.hash.get(key);
        if (element != null) {
            element.value = value;
            this.removeNode(element);
            this.insertStart(element);
            this.hash.put(key, element);
        } else if (this.size >= this.capacity) {
            element = new cacheElement(key, value);
            this.removeLastNode();
            this.insertStart(element);
            this.hash.put(key, element);
        } else {
            element = new cacheElement(key, value);
            this.hash.put(key, element);
            this.insertStart(element);
        }
    }
    
    public void insertStart(cacheElement node)
    {
        this.size ++;
        if (this.cacheStart == null) {
            this.cacheStart = node;
            return;
        }
        node.next = this.cacheStart;
        node.prev = this.cacheStart.prev;
        cacheElement lastNode = this.cacheStart.prev;
        lastNode.next = node;
        this.cacheStart.prev = node;
        this.cacheStart = node;
    }
    
    public void removeNode(cacheElement node)
    {
        if (this.cacheStart == node) {
            this.cacheStart = node.next;
        }
        cacheElement prev = node.prev;
        cacheElement next = node.next;
        prev.next = next;
        next.prev = prev;
        node.next = node.prev = node;
        this.size--;
        if (this.size == 0) {
            this.cacheStart = null;
        }
        this.hash.remove(node.key);
    }
    
    public void removeLastNode()
    {
        if (this.cacheStart == null) {
            return;
        }
        this.hash.remove(this.cacheStart.prev.key);
        this.cacheStart.prev.prev.next = this.cacheStart;
        this.cacheStart.prev = this.cacheStart.prev.prev;
        this.size--;
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */