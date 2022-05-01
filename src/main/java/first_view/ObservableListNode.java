package first_view;

import javafx.collections.ObservableListBase;
import javafx.scene.Node;

import java.util.ArrayList;
import java.util.Collections;

public class ObservableListNode extends ObservableListBase<Node> {

    private ArrayList<Node> list ;
    public ObservableListNode(Node...arrayParam) {
        list = new ArrayList<>() ;
        Collections.addAll(list, arrayParam);
    }

    @Override
    public Node get(int index) {
        return list.get(index);
    }

    @Override
    public int size() {
        return list.size();
    }

    public void removeItem(Node removeNode) {
        list.remove(removeNode) ;
    }
}
