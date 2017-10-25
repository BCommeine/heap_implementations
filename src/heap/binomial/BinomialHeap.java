package heap.binomial;

import heap.Element;
import heap.EmptyHeapException;
import heap.Heap;
import heap.binary.BinaryElement;

import java.util.TreeSet;

public class BinomialHeap<T extends Comparable<T>> implements Heap {

    private TreeSet<BinomialElement<T>> heap;

    public BinomialHeap(){
        this.heap = new TreeSet<BinomialElement<T>>((e1, e2) -> e1.getChildren().size() - e2.getChildren().size());
    }

    @Override
    public Element insert(Comparable value) {
        BinomialElement<T> element = new BinomialElement<>(value);
        //TODO plaats in of merge met head
        return element;
    }

    @Override
    public Element findMin() throws EmptyHeapException {
        BinomialElement min = heap.first();
        for (BinomialElement<T> el: heap){
            if (el.value().compareTo(min.value()) < 0){
                min = el;
            }
        }
        return min;
    }

    @Override
    public Comparable removeMin() throws EmptyHeapException {
        BinaryElement<T> min = (BinaryElement<T>) findMin();
        min.remove();
        return min.value();
    }

    public void remove(){

    }

    public void merge(TreeSet<BinomialElement<T>> set1, TreeSet<BinomialElement<T>> set2){
        for (BinomialElement<T> el: set2) {
            if(!set1.add(el)){
                
            }

        }
    }

    public void mergeTwoTrees(BinomialElement<T> tree1,BinomialElement<T> tree2){
        if(tree1.value().compareTo(tree2.value()) < 0){
            tree1.getChildren().add(tree2);
            tree2.setParent(tree1);
        } else {
            tree2.getChildren().add(tree1);
            tree1.setParent(tree2);
        }
    }

    public void perlocateUp(BinomialElement<T> element){
        BinomialElement<T> parent = element.getParent();
        while(parent != null && element.value().compareTo(parent.value()) < 0){
            parent.getChildren().remove(element);
            for(BinomialElement<T> el: parent.getChildren()){
                el.setParent(element);
            }
            for (BinomialElement<T> el: element.getChildren()){
                el.setParent(parent);
            }
            element.getChildren().add(parent);
            element.setParent(parent.getParent());
            parent.setParent(element);
            parent = element.getParent();
        }
    }

    public void perlocateDown(){

    }
}
