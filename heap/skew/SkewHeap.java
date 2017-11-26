package heap.skew;

import heap.Element;
import heap.EmptyHeapException;
import heap.Heap;

import java.util.ArrayList;
import java.util.List;

public class SkewHeap<T extends Comparable<T>>  implements Heap {

    private SkewElement<T> root;

    private void swapChildren(SkewElement<T> element) {
        SkewElement<T> temp = element.getRightChild();
        element.setRightChild(element.getLeftChild());
        element.setLeftChild(temp);
    }

    @Override
    public Element insert(Comparable value) {
        SkewElement<T> element = new SkewElement<>(value,this);
        root = fixHeap(zip(root, element));
        return element;
    }

    public Element insert(SkewElement<T> element) {
        root = fixHeap(zip(root, element));
        return element;
    }

    @Override
    public Element findMin() throws EmptyHeapException {
        return root;
    }

    @Override
    public Comparable removeMin() throws EmptyHeapException {
        return remove(root);
    }

    @Override
    public void print() {
    }

    public Comparable<T> remove(SkewElement<T> element) {
        //Split de boom in zijn linker en rechterboom
        SkewElement<T> right = element.getRightChild();
        SkewElement<T> left = element.getLeftChild();

        //Kleine voorwaarde in specifiek geval
        if(left == null && right == null && element.getParent() != null ){
            if(element.getParent().getLeftChild() == element) {
                element.getParent().setLeftChild(element.getParent().getRightChild());
                element.getParent().setRightChild(null);
                return element.value();
            }
        }

        //We maken de nieuwe bomen los van het te-verwijderen-element
        if (left != null) {
            left.setParent(null);
        }
        if (right != null) {
            right.setParent(null);
        }

        if (element.getParent() != null) {
            //We mergen de twee bomen tot één nieuwe boom met root "MergedRoot"
            SkewElement<T> mergedRoot = fixHeap(zip(left, right));
            if (mergedRoot != null) {
                mergedRoot.setParent(element.getParent());
            }
            //We moeten hem nu nog verbinden aan de ouder
            if (element.getParent().getRightChild() == element) {
                element.getParent().setRightChild(mergedRoot);
            } else {
                element.getParent().setLeftChild(mergedRoot);
            }

        } else {
            SkewElement<T> mergedRoot = fixHeap(zip(left, right));
            if(mergedRoot != null) {
                mergedRoot.setParent(null);
            }
            root = mergedRoot;
        }
        return element.value();
    }

    public SkewElement<T> zip(SkewElement<T> firstRoot, SkewElement<T> secondRoot){
        //Als één van beide null is
        if (firstRoot == null) {
            return secondRoot;
        } else if (secondRoot == null) {
            return firstRoot;
        }

        SkewElement<T> lastAdded;
        SkewElement<T> root1 = firstRoot;
        SkewElement<T> root2 = secondRoot;

        // We bepalen de kleinste, zetten die als root, en overlopen dan de 2 rechtertakken stuk voor stuk
        if (root1.value().compareTo(root2.value()) <= 0) {
            lastAdded = root1;
            root1 = root1.getRightChild();
        } else {
            lastAdded = root2;
            root2 = root2.getRightChild();
        }

        // Nu overlopen de de overige takken en plakken de kleinste knoop aan onze nieuwe boom
        while (root1 != null && root2 != null) {
            if (root1.value().compareTo(root2.value()) < 0) {
                lastAdded.setRightChild(root1);
                root1.setParent(lastAdded);
                lastAdded = root1;
                root1 = root1.getRightChild();
            } else {
                lastAdded.setRightChild(root2);
                root2.setParent(lastAdded);
                lastAdded = root2;
                root2 = root2.getRightChild();
            }
        }

        // De overige tak plakken we integraal aan onze nieuwe boom
        if (root1 == null) {
            lastAdded.setRightChild(root2);
            root2.setParent(lastAdded);
        } else {
            lastAdded.setRightChild(root1);
            root1.setParent(lastAdded);
        }

        return lastAdded;
    }

    public SkewElement<T> fixHeap(SkewElement<T> element){
        //We controleren of het meegegeven element niet null is (en de heap dus leeg is)
        if (element == null) {
            return null;
        }
        //We hebben nu een rechts kind, maar niet persé het laagste
        while (element.getRightChild() != null) {
            element = element.getRightChild();
        }

        SkewElement<T> newRoot = element;
        element = element.getParent();
        while (element != null) {
            swapChildren(element);
            newRoot = element;
            element = element.getParent();
        }
        return newRoot;
    }
}
