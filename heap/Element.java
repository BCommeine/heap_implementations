package heap;

public interface Element<T extends Comparable<T>> {
    T value();
    void remove() throws EmptyHeapException;
    void update(T value) throws EmptyHeapException;
}
