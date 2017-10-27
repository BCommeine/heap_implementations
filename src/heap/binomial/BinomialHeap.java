package heap.binomial;

import heap.Element;
import heap.EmptyHeapException;
import heap.Heap;

public class BinomialHeap<T extends Comparable<T>> implements Heap {

    private BinomialElement<T> heap;

    public BinomialHeap() {
        this.heap = null;
    }

    public BinomialHeap(BinomialElement<T> element) {
        this.heap = element;
    }

    @Override
    public Element insert(Comparable value) {
        BinomialElement<T> element = new BinomialElement<>(value, this);
        heap = heapMerge(element);
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

    public void print(){
        heap.print(0);
        System.out.println("-------------------");
    }

    public void remove(BinomialElement<T> element) {
        percolateUp(element, true);
        removeRoot(element);
        System.out.println("succes");
        print();
    }

    public void removeRoot(BinomialElement<T> root) {
        // We sluiten de boom met het element uit. Hierdoor kunnen we een heapMerge laten plaatsvinden met de rest van de heap, en de kinderen van de uitgesloten boom
        if(root.getRightSibling() != null){
            root.getRightSibling().setLeftSibling(root.getLeftSibling());
        }
        if(root.getLeftSibling() != null){
            root.getLeftSibling().setRightSibling(root.getRightSibling());
        }
        if(root == heap){
            heap = root.getRightSibling();
        }

        BinomialElement<T> next = root.getChild();
        //We koppelen de kinderen los van het te-verwijderen element
        while (next != null) {
            next.setParent(null);
            BinomialElement<T> tmp = next.getRightSibling();
            next.setRightSibling(next.getLeftSibling());
            next.setLeftSibling(tmp);
            root = next;
            next = tmp;
        }

        //Aangezien de kinderen van een root een verzameling binomiale bomen is, kunnen we deze gewoon mergen met de overige heap.
        this.heap = heapMerge(root);
    }

    private BinomialElement<T> heapMerge(BinomialElement<T> otherHeapHead) {
        BinomialElement<T> newHead = zip(this, otherHeapHead);

        if (newHead == null) {
            return null;
        }

        heap = null;

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
                    next.setLeftSibling(null);
                    if(next.getRightSibling() != null){
                        next.getRightSibling().setLeftSibling(current);
                    }
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


    public void treeMerge(BinomialElement<T> min, BinomialElement<T> max) {
        max.setParent(min);
        max.setLeftSibling(null);
        max.setRightSibling(min.getChild());
        if(min.getChild() != null){
            min.getChild().setLeftSibling(max);
        }
        min.setChild(max);
        min.setDegree(min.getDegree() + 1);
    }

    public BinomialElement<T> zip(BinomialHeap<T> heap1, BinomialElement<T> heap2) {
        if (heap1.getHeap() == null) {
            return heap2;
        } else if (heap2 == null) {
            return heap1.getHeap();
        } else {

            BinomialElement<T> head;
            BinomialElement<T> head1 = heap1.getHeap();
            BinomialElement<T> head2 = heap2;

            if (heap1.getHeap().getDegree() <= head2.getDegree()) {
                head = heap1.getHeap();
                head1 = head.getRightSibling();
            } else {
                head = heap2;
                head2 = head.getRightSibling();
            }

            BinomialElement<T> lastHead = head;

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

    public void percolateUp(BinomialElement<T> element, boolean toRoot) {
        BinomialElement<T> parent = element.getParent();
        while (parent != null && (toRoot || element.value().compareTo(parent.value()) < 0)) {

            //Kopie om te swappen
            BinomialElement<T> tmpLeft = element.getLeftSibling();
            BinomialElement<T> tmpRight = element.getRightSibling();
            BinomialElement<T> tmpChild = element.getChild();
            int tmpDegree = element.getDegree();

            //Pas de from-element-to-something referenties aan
            element.setParent(parent.getParent());
            if(parent.getChild() == element){
                element.setChild(parent);
            } else {
                element.setChild(parent.getChild()); //Wut
            }
            element.setLeftSibling(parent.getLeftSibling());
            element.setRightSibling(parent.getRightSibling());
            element.setDegree(parent.getDegree());

            //Pas de from-something-to-child referenties aan
            if(parent.getParent() != null){ parent.getParent().setChild(element); }
            if(parent.getLeftSibling() != null){ parent.getLeftSibling().setRightSibling(element); }
            if(parent.getRightSibling() != null){ parent.getRightSibling().setLeftSibling(element); }
            if(parent.getChild() != element){ parent.getChild().setParent(element); }

            //Pas de from-child-to-something referenties aan
            parent.setParent(element);
            parent.setChild(tmpChild);
            parent.setLeftSibling(tmpLeft);
            parent.setRightSibling(tmpRight);
            parent.setDegree(tmpDegree);


            //Pas de from-something-to-parent referenties aan
            if(tmpLeft != null){
                tmpLeft.setRightSibling(parent);
            }
            if(tmpRight != null){
                tmpRight.setLeftSibling(parent);
            }
            if(tmpChild != null){
                tmpChild.setParent(parent);
            }

            //Laatste referentie
            if(parent == heap){
                heap=element;
            }
            parent = element.getParent();
        }
    }

    public void percolateDown(BinomialElement<T> element) {
        BinomialElement<T> child = element.getChild();
        while (child != null && element.value().compareTo(child.value()) < 0) {

            //Kopie om te swappen
            BinomialElement<T> tmpLeft = element.getLeftSibling();
            BinomialElement<T> tmpRight = element.getRightSibling();
            BinomialElement<T> tmpParent = element.getParent();

            //Pas de from-element-to-something referenties aan
            element.setParent(child.getParent());
            element.setChild(child.getChild());
            element.setLeftSibling(child.getLeftSibling());
            element.setRightSibling(child.getRightSibling());

            //Pas de from-something-to-child referenties aan
            if(child.getChild() != null){ child.getChild().setParent(element); }
            if(child.getLeftSibling() != null){ child.getLeftSibling().setRightSibling(element); }
            if(child.getRightSibling() != null){ child.getRightSibling().setLeftSibling(element); }
            if(child.getParent() != element){ child.getParent().setChild(element); }

            //Pas de from-child-to-something referenties aan
            child.setChild(element);
            child.setParent(tmpParent);
            child.setLeftSibling(tmpLeft);
            child.setRightSibling(tmpRight);


            //Pas de from-something-to-parent referenties aan
            if(tmpLeft != null){
                tmpLeft.setRightSibling(child);
            }
            if(tmpRight != null){
                tmpRight.setLeftSibling(child);
            }
            if(tmpParent != null){
                tmpParent.setParent(child);
            }
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
