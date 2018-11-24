import java.util.*;

/**
 * Implementation of HashMap.
 */
public class HashMap<K, V> implements HashMapInterface<K, V> {

    private MapEntry<K, V>[] table;
    private int size;

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code INITIAL_CAPACITY}.
     *
     * Do not use magic numbers!
     *
     * Use constructor chaining.
     */
    public HashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code initialCapacity}.
     *
     * You may assume {@code initialCapacity} will always be positive.
     *
     * @param initialCapacity initial capacity of the backing array
     */
    public HashMap(int initialCapacity) {
        table = (MapEntry<K, V>[]) new MapEntry[initialCapacity];
    }

    @Override
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Cannot put null key or value.");
        }

        if (table.length * MAX_LOAD_FACTOR <= size + 1) {
            resizeBackingTable(table.length * 2 + 1);
        }

        int probe = Math.abs(key.hashCode()) % table.length,
            i = 0,
            firstRemoved = -1;

        while (i < table.length && table[probe] != null && !table[probe].getKey().equals(key)) {
            if (table[probe].isRemoved() && firstRemoved == -1) {
                firstRemoved = probe;
            }
            probe = (probe + 1) % table.length;
        }

        if (table[probe] != null && table[probe].getKey().equals(key) && !table[probe].isRemoved()) {
            V temp = table[probe].getValue();
            table[probe].setValue(value);
            return temp;
        } else if (firstRemoved != -1) {
            table[firstRemoved] = new MapEntry<>(key, value);
            size++;
            return null;
        } else {
            table[probe] = new MapEntry<>(key, value);
            size++;
            return null;
        }
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Cannot remove null key.");
        }

        int probe = Math.abs(key.hashCode()) % table.length,
                i = 0;
        while (i < table.length && table[probe] != null && !table[probe].getKey().equals(key)) {
            probe = (probe + 1) % table.length;
            i++;
        }

        if (i >= table.length || table[probe] == null || table[probe].isRemoved()) {
            throw new NoSuchElementException("Key does not exist in map.");
        }

        table[probe].setRemoved(true);

        size--;

        return table[probe].getValue();
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Cannot get null key.");
        }

        int probe = Math.abs(key.hashCode()) % table.length,
            i = 0;
        while (i < table.length && table[probe] != null && !table[probe].getKey().equals(key)) {
            probe = (probe + 1) % table.length;
            i++;
        }

        if (i >= table.length || table[probe] == null || table[probe].isRemoved()) {
            throw new NoSuchElementException("Key does not exist in map.");
        }

        return table[probe].getValue();
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Cannot find null key.");
        }

        int probe = Math.abs(key.hashCode()) % table.length,
            i = 0;
        while (i < table.length && table[probe] != null && !table[probe].getKey().equals(key)) {
            probe = (probe + 1) % table.length;
            i++;
        }

        return i < table.length && table[probe] != null && !table[probe].isRemoved();
    }

    @Override
    public void clear() {
        table = (MapEntry<K, V>[]) new MapEntry[INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>();

        for (MapEntry<K, V> entry : table) {
            if (entry != null && !entry.isRemoved()) {
                set.add(entry.getKey());
            }
        }
        return set;
    }

    @Override
    public List<V> values() {
        List<V> set = new ArrayList<>();

        for (MapEntry<K, V> entry : table) {
            if (entry != null && !entry.isRemoved()) {
                set.add(entry.getValue());
            }
        }
        return set;
    }

    @Override
    public void resizeBackingTable(int length) {
        if (length < size) {
            throw new IllegalArgumentException("Cannot reszie backing array to less than the current size.");
        }
        MapEntry<K, V>[] temp = (MapEntry<K, V>[]) new MapEntry[length];

        for (MapEntry<K, V> entry : table) {
            if (entry != null && !entry.isRemoved()) {
                int probe = Math.abs(entry.getKey().hashCode()) % length;
                while (temp[probe] != null) {
                    probe = (probe + 1) % length;
                }
                temp[probe] = entry;
            }
        }

        table = temp;
    }
    
    @Override
    public MapEntry<K, V>[] getTable() {
        return table;
    }

}
