package heap.binomial;

import heap.Element;
import heap.EmptyHeapException;
import heap.Heap;

import java.util.EmptyStackException;

public class BinomialHeap<T extends Comparable<T>> implements Heap {

    private Reference<T> root;

    public BinomialHeap() {
        this.root = null;
    }

    @Override
    public Element insert(Comparable value) {
        BinomialElement<T> element = new BinomialElement<>(value, this);
        root = heapMerge(element.getReference());
        return element;
    }

    public Element insert(BinomialElement element) {
        root = heapMerge(element.getReference());
        return element;
    }

    @Override
    public Element findMin() throws EmptyHeapException {
        if (root == null) {
            throw new EmptyHeapException();
        } else {
            return findMinSibling(root).getElement();
        }
    }

    public Reference<T> findMinSibling(Reference<T> element) {
        Reference<T> min = element;
        while (element != null) {
            if (element.getElement().value().compareTo(min.getElement().value()) < 0) {
                min = element;
            }
            element = element.getRightSibling();
        }
        return min;
    }

    @Override
    public Comparable removeMin() throws EmptyHeapException {
        BinomialElement<T> min = (BinomialElement<T>) findMin();
        //Omdat een minimum altijd op de root van zijn tree gaat staan hoeven we niet te percolaten
        removeRoot(min.getReference());
        return min.value();
    }

    public void remove(BinomialElement<T> element) throws EmptyHeapException {
        percolateUp(element.getReference(), true);
        removeRoot(element.getReference());
    }

    public void removeRoot(Reference<T> treeRoot) throws EmptyHeapException {
        // We sluiten de boom met het element uit. Hierdoor kunnen we een heapMerge laten plaatsvinden met de rest van de heap, en de kinderen van de uitgesloten boom
        if(treeRoot == root){
            root = treeRoot.getRightSibling();
        }
        if(treeRoot.getRightSibling() != null){
            treeRoot.getRightSibling().setLeftSibling(treeRoot.getLeftSibling());
        }
        if(treeRoot.getLeftSibling() != null){
            treeRoot.getLeftSibling().setRightSibling(treeRoot.getRightSibling());
        }

        Reference<T> removing = treeRoot;

        if(treeRoot.getChild() != null){
            Reference<T> next = treeRoot.getChild();
            //We koppelen de kinderen los van het te-verwijderen element
            while (next != null) {
                next.setParent(null);
                // OPMERKING: Check of de graad van elke boom aangepast moet worden, vermoedelijk niet maar je weet nooit
                Reference<T> tmp = next.getRightSibling();
                next.setRightSibling(next.getLeftSibling());
                next.setLeftSibling(tmp);
                treeRoot = next;
                next = tmp;
            }
            //Aangezien de kinderen van een root een verzameling binomiale bomen is, kunnen we deze gewoon mergen met de overige heap.
            this.root = heapMerge(treeRoot);
        }
    }

    public Reference<T> zip(Reference<T> heap1, Reference<T> heap2) {
        if (heap1 == null) {
            return heap2;
        } else if (heap2 == null) {
            return heap1;
        } else {

            Reference<T> head;
            Reference<T> head1 = heap1;
            Reference<T> head2 = heap2;

            if (heap1.getDegree() <= head2.getDegree()) {
                head = heap1;
                head1 = head.getRightSibling();
            } else {
                head = heap2;
                head2 = head.getRightSibling();
            }

            Reference<T> lastHead = head;

            while (head1 != null && head2 != null) {
                if (head1.getDegree() <= head2.getDegree()) {
                    lastHead.setRightSibling(head1);
                    head1.setLeftSibling(lastHead);
                    head1 = head1.getRightSibling();
                } else {
                    lastHead.setRightSibling(head2);
                    head2.setLeftSibling(lastHead);
                    head2 = head2.getRightSibling();
                }
                lastHead = lastHead.getRightSibling();
            }

            if (head1 != null) {
                lastHead.setRightSibling(head1);
                head1.setLeftSibling(lastHead);
            } else {
                lastHead.setRightSibling(head2);
                head2.setLeftSibling(lastHead);
            }
        return head;
        }
    }

    private void treeMerge(Reference<T> min, Reference<T> max) {
        max.setParent(min);
        max.setLeftSibling(null);
        max.setRightSibling(min.getChild());
        if(min.getChild() != null){
            min.getChild().setLeftSibling(max);
        }
        min.setChild(max);
        min.setDegree(min.getDegree() + 1);
    }

    private Reference<T> heapMerge(Reference<T> otherHeapHead) {
        Reference<T> newHead = zip(root, otherHeapHead);
        if (newHead == null) {
            return null;
        }

        Reference<T> current = newHead;
        Reference<T> next = newHead.getRightSibling();

        while (next != null) {
            //Hier testen we of we niet met 2 bomen zitten van gelijke graad. Als we met 3 bomen zitten met gelijke graad dan steken we de eerste in het resultaat en mergen de andere 2
            if (current.getDegree() != next.getDegree() || (next.getRightSibling() != null && next.getRightSibling().getDegree() == current.getDegree())) {
                current = next;
            } else {
                if (current.getElement().value().compareTo(next.getElement().value()) < 0) {
                    //Aangezien we de "next" tree mergen in de "current", kunnen we deze gewoon uit de rij halen.
                    current.setRightSibling(next.getRightSibling());
                    next.setLeftSibling(null);
                    if(next.getRightSibling() != null){
                        next.getRightSibling().setLeftSibling(current);
                    }
                    treeMerge(current, next);
                } else {
                    //Hier wordt alles wat ingewikkelder: Als de "current" ook de eerste is, dan willen we graag zijn parent teruggeven
                    Reference<T> previous = current.getLeftSibling();
                    if (previous == null) {
                        newHead = next;
                        next.setLeftSibling(null);
                    } else {
                        //In het andere geval gaan we gewoon de "current" uit de rij van treeroots halen
                        previous.setRightSibling(next);
                        next.setLeftSibling(previous);
                    }
                    treeMerge(next, current);
                    current = next;
                }
            }
            next = current.getRightSibling();
        }
        return newHead;
    }

    private void swapElements(Reference<T> child, Reference<T> parent){
        BinomialElement<T> element = child.getElement();
        child.getElement().setReference(parent);
        parent.getElement().setReference(child);
        child.setElement(parent.getElement());
        parent.setElement(element);
    }

    public void percolateUp(Reference<T> element, boolean toRoot) {
        Reference<T> parent = element.getParent();
        while (parent != null && (toRoot || element.getElement().value().compareTo(parent.getElement().value()) < 0)) {
            swapElements(element, parent);
            element = parent;
            parent = element.getParent();
        }
    }

    public void percolateDown(Reference<T> element) {
        Reference<T> child = findMinSibling(element.getChild());
        while (child != null && child.getElement().value().compareTo(element.getElement().value()) < 0) {
            swapElements(child, element);
            element = child;
            child = findMinSibling(element.getChild());
        }
    }
    public void print(){
    }
}
