package heap.skew;

import heap.Element;
import heap.EmptyHeapException;

public class SkewElement <T extends Comparable<T>> implements Element {
    private Comparable<T> value;
    private SkewHeap<T> heap;
    private SkewElement<T> parent;
    private SkewElement<T> leftChild;
    private SkewElement<T> rightChild;

    public SkewElement(Comparable<T> value, SkewHeap<T> heap){
        this.value = value;
        this.heap = heap;
    }

    @Override
    public Comparable value() {
        return value;
    }

    @Override
    public void remove() throws EmptyHeapException {
        heap.remove(this);
    }

    @Override
    public void update(Comparable value) {

    }

    public SkewElement<T> getRightChild() {
        return rightChild;
    }

    public void setRightChild(SkewElement<T> rightChild) {
        this.rightChild = rightChild;
    }

    public void setParent(SkewElement<T> parent) {
        this.parent = parent;
    }

    public SkewElement<T> getParent() {
        return parent;
    }

    public SkewElement<T> getLeftChild() {
        return leftChild;
    }
}
