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
        List<List<String>> lines = new ArrayList<List<String>>();
        List<SkewElement<T>> level = new ArrayList<>();
        List<SkewElement<T>> next = new ArrayList<>();
        level.add(root);
        int nn = 1;
        int widest = 0;
        while (nn != 0) {
            List<String> line = new ArrayList<String>();
            nn = 0;
            for (SkewElement<T> n : level) {
                if (n == null) {
                    line.add(null);

                    next.add(null);
                    next.add(null);
                } else {
                    String aa = n.value().toString();
                    line.add(aa);
                    if (aa.length() > widest) widest = aa.length();

                    next.add(n.getLeftChild());
                    next.add(n.getRightChild());

                    if (n.getLeftChild() != null) nn++;
                    if (n.getRightChild() != null) nn++;
                }
            }
            if (widest % 2 == 1) widest++;
            lines.add(line);
            List<SkewElement<T>> tmp = level;
            level = next;
            next = tmp;
            next.clear();
        }
        int perpiece = lines.get(lines.size() - 1).size() * (widest + 4);
        for (int i = 0; i < lines.size(); i++) {
            List<String> line = lines.get(i);
            int hpw = (int) Math.floor(perpiece / 2f) - 1;
            if (i > 0) {
                for (int j = 0; j < line.size(); j++) {
                    // split node
                    char c = ' ';
                    if (j % 2 == 1) {
                        if (line.get(j - 1) != null) {
                            c = (line.get(j) != null) ? '┴' : '┘';
                        } else {
                            if (j < line.size() && line.get(j) != null) c = '└';
                        }
                    }
                    System.out.print(c);
                    // lines and spaces
                    if (line.get(j) == null) {
                        for (int k = 0; k < perpiece - 1; k++) {
                            System.out.print(" ");
                        }
                    } else {
                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? " " : "─");
                        }
                        System.out.print(j % 2 == 0 ? "┌" : "┐");
                        for (int k = 0; k < hpw; k++) {
                            System.out.print(j % 2 == 0 ? "─" : " ");
                        }
                    }
                }
                System.out.println();
            }

            // print line of numbers
            for (int j = 0; j < line.size(); j++) {

                String f = line.get(j);
                if (f == null) f = "";
                int gap1 = (int) Math.ceil(perpiece / 2f - f.length() / 2f);
                int gap2 = (int) Math.floor(perpiece / 2f - f.length() / 2f);

                // a number
                for (int k = 0; k < gap1; k++) {
                    System.out.print(" ");
                }
                System.out.print(f);
                for (int k = 0; k < gap2; k++) {
                    System.out.print(" ");
                }
            }
            System.out.println();
            perpiece /= 2;
        }
    }

    public Comparable<T> remove(SkewElement<T> element) {
        //Split de boom in zijn linker en rechterboom
        SkewElement<T> right = element.getRightChild();
        SkewElement<T> left = element.getLeftChild();

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
