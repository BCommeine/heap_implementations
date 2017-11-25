package heap.pairing;

import heap.Element;
import heap.EmptyHeapException;

public class PairingElement <T extends Comparable<T>> implements Element{
    private Comparable<T> value;
    private PairingHeap<T> heap;
    private PairingElement<T> parent;
    private PairingElement<T> rightSibling;
    private PairingElement<T> leftSibling;
    private PairingElement<T> child;

    public PairingElement(Comparable value, PairingHeap<T> heap) {
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

    public PairingElement<T> getChild() {
        return child;
    }

    public void setChild(PairingElement child) {
        this.child = child;
    }

    public void setRightSibling(PairingElement rightSibling) {
        this.rightSibling = rightSibling;
    }

    public void setParent(PairingElement parent) {
        this.parent = parent;
    }

    public PairingElement getRightSibling() {
        return rightSibling;
    }

    public PairingElement<T> getParent() {
        return parent;
    }

    public void setLeftSibling(PairingElement leftSibling) {
        this.leftSibling = leftSibling;
    }

    public PairingElement<T> getLeftSibling() {
        return leftSibling;
    }
}
