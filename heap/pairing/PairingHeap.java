package heap.pairing;

import heap.Element;
import heap.EmptyHeapException;
import heap.Heap;

import java.lang.reflect.Parameter;

public class PairingHeap<T extends Comparable<T>> implements Heap {
    private PairingElement<T> root;

    @Override
    public Element insert(Comparable value) {
        PairingElement element = new PairingElement(value, this);
        root = mergeTwoHeaps(root, element);
        return element;
    }

    @Override
    public Element findMin() throws EmptyHeapException {
        return root;
    }

    @Override
    public Comparable removeMin() throws EmptyHeapException {
        Comparable value = root.value();
        remove(root);
        return value;
    }

    public PairingElement mergeTwoHeaps(PairingElement root1, PairingElement root2){
        if(root1 == null){
            return root2;
        } else if (root2 == null){
            return root1;
        } else if(root1.value().compareTo(root2.value()) < 0){

            // root1 is de kleinste
            PairingElement tmp = root1.getChild();
            root1.setChild(root2);
            if(root1.getParent() != null){
                root1.setRightSibling(root1.getParent().getRightSibling());
                root1.setLeftSibling(root1.getParent().getLeftSibling());
            } else {
                root1.setRightSibling(null);
                root1.setLeftSibling(null);
            }
            root2.setLeftSibling(null);
            root2.setRightSibling(tmp);
            if(tmp != null){
                tmp.setLeftSibling(root2);
            }
            root2.setParent(root1);
            return root1;
        } else {

            // root2 is de kleinste
            PairingElement tmp = root2.getChild();
            root2.setChild(root1);
            if(root2.getParent() != null){
                root2.setRightSibling(root2.getParent().getRightSibling());
                root2.setLeftSibling(root2.getParent().getLeftSibling());
            } else {
                root2.setRightSibling(null);
                root2.setLeftSibling(null);
            }
            root1.setLeftSibling(null);
            root1.setRightSibling(tmp);
            if(tmp != null){
                tmp.setLeftSibling(root1);
            }
            root1.setParent(root2);
            return root2;
        }
    }

    public void remove(PairingElement element){
        // Get first elements to merge
        PairingElement root1 = element.getChild();
        if(root1 != null) {
            if(root1.getRightSibling() != null) {
                PairingElement root2 = root1.getRightSibling();
                PairingElement lastMerged = null;
                PairingElement merged;
                PairingElement next = null;

                while (root2 != null) {
                    // We mergen de twee roots
                    next = root2.getRightSibling();
                    merged = mergeTwoHeaps(root1, root2);
                    merged.setRightSibling(lastMerged);
                    lastMerged = merged;

                    // Als de tweede root geen siblings meer heeft zetten we onze gemergde toppen in de rij
                    if (next == null) {
                        next = lastMerged;
                        lastMerged = null;
                    } else if (next.getRightSibling() == null) {
                        next.setRightSibling(lastMerged);
                        lastMerged = null;
                    }

                    // We gaan door naar de volgende twee roots die gemerged moeten worden
                    root1 = next ;
                    if(root1 != null) {
                        root2 = root1.getRightSibling();
                    } else {
                        root2 = null;
                    }
                }
            }

            // We mergen onze nieuwe root aan zijn mogelijke ouders
            root1.setParent(element.getParent());
            root1.setLeftSibling(element.getLeftSibling());
            root1.setRightSibling(element.getRightSibling());
            if(element.getLeftSibling() != null){
                element.getLeftSibling().setRightSibling(root1);
            }
            if(element.getRightSibling() != null){
                element.getRightSibling().setLeftSibling(root1);
            }
            if(element.getParent() != null ){
                if(element.getParent().getChild() == element) {
                    element.getParent().setChild(root1);
                }
            }
        } else {
            if(element.getLeftSibling() != null){
                element.getLeftSibling().setRightSibling(element.getRightSibling());
            }
            if(element.getRightSibling() != null){
                element.getRightSibling().setLeftSibling(element.getLeftSibling());
            }
            if(element.getParent() != null ){
                if(element.getParent().getChild() == element) {
                    element.getParent().setChild(element.getRightSibling());
                }
            }
        }
        
        if(element == root){
            root = root1;
        }
    }

    public void print(){
    }
}