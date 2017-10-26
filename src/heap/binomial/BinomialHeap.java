package heap.binomial;

import heap.Element;
import heap.EmptyHeapException;
import heap.Heap;

public class BinomialHeap<T extends Comparable<T>> implements Heap {

    private BinomialElement<T> heap;

    public BinomialHeap() {
        this.heap = new BinomialElement<>(null, this);
    }

    public BinomialHeap(BinomialElement<T> element) {
        this.heap = element;
    }

    @Override
    public Element insert(Comparable value) {
        BinomialElement<T> element = new BinomialElement<>(value, this);
        heap = heapMerge(new BinomialHeap<T>(element));
        return element;
    }

    @Override
    public Element findMin() throws EmptyHeapException {
        if (heap == null) {
            return null;
        } else {
            BinomialElement<T> min = heap;
            BinomialElement<T> next = heap.getRightSibling();
            while (next != null) {
                if (next.value().compareTo(min.value()) < 0) {
                    min = next;
                }
                next = next.getRightSibling();
            }
            return min;
        }
    }

    @Override
    public Comparable removeMin() throws EmptyHeapException {
        BinomialElement<T> min = (BinomialElement<T>) findMin();
        removeRoot(min);
        return min.value();
    }

    public void remove(BinomialElement<T> element) {
        percolateUp(element, true);
        removeRoot(element);
    }

    public void removeRoot(BinomialElement<T> element) {
        // We sluiten de boom met het element uit. Hierdoor kunnen we een heapMerge laten plaatsvinden met de rest van de heap, en de kinderen van de uitgesloten boom
        element.getRightSibling().setLeftSibling(element.getLeftSibling());
        element.getLeftSibling().setRightSibling(element.getRightSibling());

        BinomialElement<T> next = element.getChild();
        //We koppelen de kinderen los van het te-verwijderen element
        while (next != null) {
            next.setParent(null);
            next = next.getRightSibling();
        }

        //Aangezien de kinderen van een root een verzameling binomiale bomen is, kunnen we deze gewoon mergen met de overige heap.
        BinomialHeap<T> newHeap = new BinomialHeap<T>(element.getChild());
        this.heap = heapMerge(newHeap);
    }

    private BinomialElement<T> heapMerge(BinomialHeap<T> otherHeap) {
        BinomialElement<T> newHead = zip(this, otherHeap);

        if (newHead == null) {
            return null;
        }

        heap = null;
        otherHeap = null;

        BinomialElement<T> current = newHead;
        BinomialElement<T> next = newHead.getRightSibling();

        while (next != null) {
            //Hier testen we of we niet met 2 bomen zitten van gelijke graad. Als we met 3 bomen zitten met gelijke graad dan steken we de eerste in het resultaat en mergen de andere 2
            if (current.getDegree() != next.getDegree() || (next.getRightSibling() != null && next.getRightSibling().getDegree() == current.getDegree())) {
                current = next;
            } else {
                if (current.value().compareTo(next.value()) < 0) {
                    //Aangezien we de "next" tree mergen in de "current", kunnen we deze gewoon uit de rij halen.
                    current.setRightSibling(next.getRightSibling());
                    next.getRightSibling().setLeftSibling(current);
                    treeMerge(current, next);
                } else {
                    //Hier wordt alles wat ingewikkelder: Als de "current" ook de eerste is, dan willen we graag zijn parent teruggeven
                    BinomialElement<T> previous = current.getLeftSibling();
                    if (previous == null) {
                        newHead = next;
                        next.setLeftSibling(null);
                    } else {
                        //In het andere geval gaan we gewoon de "current" uit de rij van treeroots halen
                        previous.setRightSibling(next);
                        current.getRightSibling().setLeftSibling(previous);
                    }
                    treeMerge(next, current);
                    current = next;
                }
            }
            next = current.getRightSibling();
        }


        return newHead;
    }


    public void treeMerge(BinomialElement<T> min, BinomialElement<T> max) {
        max.setParent(min);
        max.setRightSibling(min.getChild());
        min.getChild().setLeftSibling(max);
        min.setChild(max);
        min.setDegree(min.getDegree() + 1);
    }

    public BinomialElement<T> zip(BinomialHeap<T> heap1, BinomialHeap<T> heap2) {
        //TODO fix de referenties naar de juiste heap
        if (heap1.getHeap() == null) {
            return heap2.getHeap();
        } else if (heap2.getHeap() == null) {
            return heap1.getHeap();
        } else {

            BinomialElement<T> head;
            BinomialElement<T> head1 = heap1.getHeap();
            BinomialElement<T> head2 = heap2.getHeap();

            if (heap1.getHeap().getDegree() <= heap2.getHeap().getDegree()) {
                head = heap1.getHeap();
                head1 = head.getRightSibling();
            } else {
                head = heap2.getHeap();
                head2 = head.getRightSibling();
            }

            BinomialElement<T> lastHead = heap;

            while (heap1 != null && heap2 != null) {
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

    public void percolateUp(BinomialElement<T> element, boolean toRoot) {
        BinomialElement<T> parent = element.getParent();
        while (parent != null && (toRoot || element.value().compareTo(parent.value()) < 0)) {

            //Kopie om te swappen
            BinomialElement<T> tmpLeft = parent.getLeftSibling();
            BinomialElement<T> tmpRight = parent.getRightSibling();
            BinomialElement<T> tmpParent = parent.getParent();

            //Pas de from-parent-to-something referenties aan
            parent.setParent(element);
            parent.setChild(element.getChild());
            parent.setLeftSibling(element.getLeftSibling());
            parent.setRightSibling(element.getRightSibling());

            //Pas de from-something-to-parent referenties aan
            if(tmpParent != null){ tmpParent.setChild(element); }
            if(tmpLeft != null){ tmpLeft.setRightSibling(element); }
            if(tmpRight != null){ tmpRight.setLeftSibling(element); }

            //Pas de from-child-to-something referenties aan
            element.setParent(tmpParent);
            element.setChild(parent);
            element.setLeftSibling(tmpLeft);
            element.setRightSibling(tmpRight);

            //Pas de from-something-to-child referenties aan
            if(element.getChild() != null){ element.getChild().setParent(parent); }
            if(element.getLeftSibling() != null){ element.getLeftSibling().setRightSibling(parent); }
            if(element.getRightSibling() != null){ element.getRightSibling().setLeftSibling(parent); }

            //Laatste referentie
            if(parent == heap){
                heap=element;
            }
            parent = parent.getParent();
        }
    }

    public void percolateDown(BinomialElement<T> element) {
        BinomialElement<T> child = element.getChild();
        while (child != null && element.value().compareTo(child.value()) < 0) {

            //Kopie om te swappen
            BinomialElement<T> tmpLeft = child.getLeftSibling();
            BinomialElement<T> tmpRight = child.getRightSibling();
            BinomialElement<T> tmpChild = child.getChild();

            //Pas de from-child-to-something referenties aan
            child.setChild(element);
            child.setChild(element.getChild());
            child.setLeftSibling(element.getLeftSibling());
            child.setRightSibling(element.getRightSibling());

            //Pas de from-something-to-child referenties aan
            if(tmpChild != null){ tmpChild.setChild(element); }
            if(tmpLeft != null){ tmpLeft.setRightSibling(element); }
            if(tmpRight != null){ tmpRight.setLeftSibling(element); }

            //Pas de from-child-to-something referenties aan
            element.setChild(tmpChild);
            element.setChild(child);
            element.setLeftSibling(tmpLeft);
            element.setRightSibling(tmpRight);

            //Pas de from-something-to-child referenties aan
            if(element.getChild() != null){ element.getChild().setChild(child); }
            if(element.getLeftSibling() != null){ element.getLeftSibling().setRightSibling(child); }
            if(element.getRightSibling() != null){ element.getRightSibling().setLeftSibling(child); }

            //Laatste referentie
            if(element == heap){
                heap = child;
            }
            child = child.getChild();
        }
    }

    public BinomialElement<T> getHeap() {
        return heap;
    }
}
