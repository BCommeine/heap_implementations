package heap.leftist;

import heap.Element;
import heap.EmptyHeapException;
import heap.Heap;

import java.util.ArrayList;
import java.util.List;

public class LeftistHeap<T extends Comparable<T>> implements Heap{

    private LeftistElement<T> root;

    @Override
    public Element insert(Comparable value) {
        LeftistElement<T> element = new LeftistElement<T>(value, this);
        root = fixHeap(zip(root, element));
        return element;
    }

    @Override
    public Element findMin() throws EmptyHeapException {
        if(root == null){
            throw new EmptyHeapException();
        }
        return root;
    }

    @Override
    public Comparable removeMin() throws EmptyHeapException {
       return remove(root);
    }

    public Comparable<T> remove(LeftistElement<T> element) throws EmptyHeapException {
        //Split de boom in zijn linker en rechterboom
        LeftistElement<T> right = element.getRightChild();
        LeftistElement<T> left = element.getLeftChild();

        //We maken de nieuwe bomen los van het te-verwijderen-element
        if(left != null){
            left.setParent(null);
        }
        if(right != null){
            right.setParent(null);
        }

        if(element.getParent() != null){
            //We mergen de twee bomen tot één nieuwe boom met root "MergedRoot"
            LeftistElement<T> mergedRoot = fixHeap(zip(left, right));
            mergedRoot.setParent(element.getParent());

            //We moeten hem nu nog verbinden aan de ouder
            if(element.getParent().getRightChild() == element){
                element.setRightChild(mergedRoot);
            } else {
                element.setLeftChild(mergedRoot);
            }
        } else {
            root = fixHeap(zip(left, right));
        }

        return element.value();
    }

    public LeftistElement<T> zip(LeftistElement<T> firstRoot, LeftistElement<T> secondRoot){
        //Als één van beide null is
        if(firstRoot == null){
            return secondRoot;
        } else if(secondRoot == null){
            return firstRoot;
        }

        LeftistElement<T> lastAdded;
        LeftistElement<T> root1 = firstRoot;
        LeftistElement<T> root2 = secondRoot;

        // We bepalen de kleinste, zetten die als root, en overlopen dan de 2 rechtertakken stuk voor stuk
        if(root1.value().compareTo(root2.value()) <= 0){
            lastAdded = root1;
            root1 = root1.getRightChild();
        } else {
            lastAdded = root2;
            root2 = root2.getRightChild();
        }

        // Nu overlopen de de overige takken en plakken de kleinste knoop aan onze nieuwe boom
        while (root1 != null && root2 != null){
            if(root1.value().compareTo(root2.value()) < 0){
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
        if(root1 == null){
            lastAdded.setRightChild(root2);
            root2.setParent(lastAdded);
        } else {
            lastAdded.setRightChild(root1);
            root1.setParent(lastAdded);
        }

        return lastAdded;
    }

    public LeftistElement<T> fixHeap(LeftistElement<T> element){
        //We controleren of het meegegeven element niet null is (en de heap dus leeg is)
        if (element == null){
            return null;
        }
        //We hebben nu een rechts kind, maar niet persé het laagste
        while (element.getRightChild() != null){
            element = element.getRightChild();
        }
        element.setNpl(0); // TODO Misschien is deze toekenning het probleem

        LeftistElement<T> newRoot = element;
        element = element.getParent();
        while (element != null){
            int leftNpl;
            int rightNpl;

            if(element.getLeftChild() == null) { leftNpl = -1; }
            else { leftNpl = element.getLeftChild().getNpl(); }

            if(element.getRightChild() == null) { rightNpl = -1; }
            else { rightNpl = element.getRightChild().getNpl(); }

            element.setNpl(Math.min(leftNpl, rightNpl) + 1);

            if(leftNpl < rightNpl){
                swapChildren(element);
            }
            newRoot = element;
            element = element.getParent();
        }
        return newRoot;
    }

    public void swapChildren(LeftistElement<T> element){
        LeftistElement<T> tmp = element.getRightChild();
        element.setRightChild(element.getLeftChild());
        element.setLeftChild(tmp);
    }

}
