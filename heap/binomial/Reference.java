package heap.binomial;

public class Reference<T extends Comparable<T>> {
    public Reference(BinomialElement<T> element){
        this.element = element;
    }

    private BinomialElement<T> element;

    private int degree;
    private Reference<T> parent;
    private Reference<T> leftSibling;
    private Reference<T> rightSibling;
    private Reference<T> child;

    public Reference<T> getRightSibling() {
        return rightSibling;
    }

    public Reference<T> getParent() {
        return parent;
    }

    public Reference<T> getChild() {
        return child;
    }

    public int getDegree() {
        return degree;
    }

    public void setRightSibling(Reference<T> rightSibling) {
        this.rightSibling = rightSibling;
    }

    public void setChild(Reference<T> child) {
        this.child = child;
    }

    public void setParent(Reference<T> parent) {
        this.parent = parent;
    }

    public void setDegree(int degree) {
        this.degree = degree;
    }

    public Reference<T> getLeftSibling() {
        return leftSibling;
    }

    public void setLeftSibling(Reference<T> leftSibling) {
        this.leftSibling = leftSibling;
    }

    public BinomialElement<T> getElement() {
        return element;
    }

    public void setElement(BinomialElement<T> element){
        this.element = element;
    }
}
