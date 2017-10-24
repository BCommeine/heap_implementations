package heap.binomial;

import heap.Element;

import java.util.TreeSet;

public class BinomialElement <T extends Comparable<T>> implements Element {
    private Comparable value;
    private int degree;
    private BinomialElement<T> parent;
    private TreeSet<BinomialElement<T>> children;

    public BinomialElement(Comparable value){
        this.value = value;
    }

    public TreeSet<BinomialElement<T>> getChildren(){
        return children;
    }

    public BinomialElement<T> getParent() {
        return parent;
    }

    @Override
    public Comparable value() {
        return value;
    }

    @Override
    public void remove() {

    }

    @Override
    public void update(Comparable value) {

    }
}
