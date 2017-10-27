package heap.binomial;

import heap.Element;

import java.util.TreeSet;

public class BinomialElement <T extends Comparable<T>> implements Element {
   private Comparable<T> value;
   private int degree;
   private BinomialHeap<T> heap;
   private BinomialElement<T> parent;
   private BinomialElement<T> leftSibling;
   private BinomialElement<T> rightSibling;
   private BinomialElement<T> child;


   public BinomialElement(Comparable<T> value, BinomialHeap<T> heap){
       this.value = value;
       this.heap = heap;
    }

    @Override
    public Comparable value() {
        return value;
    }

    @Override
    public void remove() {
        this.heap.remove(this);
    }

    @Override
    public void update(Comparable value) {
        if(this.value.compareTo((T) value) == 0) {
            return;
        } else if (this.value.compareTo( (T) value) < 0){
            this.value = (T) value;
            this.heap.percolateDown(this);
            //this.heap.print();
        } else {
            this.value = (T) value;
            this.heap.percolateUp(this, false);
            //this.heap.print();
        }
    }

    public void print(int level) {
        BinomialElement<T> curr = this;
        while (curr != null) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < level; i++) {
                sb.append(" ");
            }
            sb.append(curr.value().toString());
            System.out.println(sb.toString());
            if (curr.child != null) {
                curr.child.print(level + 1);
            }
            curr = curr.getRightSibling();
        }
    }

    public BinomialElement<T> getRightSibling() {
        return rightSibling;
    }

    public BinomialElement<T> getParent() {
        return parent;
    }

    public void setValue(Comparable value) {
        this.value = value;
    }

    public BinomialElement<T> getChild() {
        return child;
    }

    public int getDegree() {
        return degree;
    }

    public void setRightSibling(BinomialElement<T> rightSibling) {
        this.rightSibling = rightSibling;
    }

    public void setChild(BinomialElement<T> child) {
        this.child = child;
    }

    public void setParent(BinomialElement<T> parent) {
        this.parent = parent;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public BinomialElement<T> getLeftSibling() {
        return leftSibling;
    }

    public void setLeftSibling(BinomialElement<T> leftSibling) {
        this.leftSibling = leftSibling;
    }
}
