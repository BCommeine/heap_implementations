package heap.binomial;

import heap.Element;
import heap.EmptyHeapException;

import java.util.TreeSet;

public class BinomialElement<T extends Comparable<T>> implements Element {
    private Comparable<T> value;
    private BinomialHeap<T> heap;
    private Reference<T> reference;

    public BinomialElement(Comparable<T> value, BinomialHeap<T> heap) {
        this.value = value;
        this.heap = heap;
        this.reference = new Reference<>(this);
    }

    @Override
    public Comparable value() {
        return value;
    }

    @Override
    public void remove() {
        try {
            this.heap.remove(this);
        } catch (EmptyHeapException e) {
        }
    }

    @Override
    public void update(Comparable value) {
        if (this.value.compareTo((T) value) == 0) {
            return;
        } else if (this.value.compareTo((T) value) < 0) {
            this.value = (T) value;
            this.heap.percolateDown(this.getReference());
        } else {
            this.value = (T) value;
            this.heap.percolateUp(this.getReference(), false);
        }
    }

    public Reference<T> getReference() {
        return reference;
    }

    public void setReference(Reference reference) {
        this.reference = reference;
    }
}
