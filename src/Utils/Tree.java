package Utils;

import java.nio.file.Path;
import java.util.ArrayList;

public class Tree {

    public String node;
    public ArrayList<Tree> children;

    public Tree(String node, ArrayList<Tree> children) {
        this.node = node;
        this.children = children;
    }

    public boolean clearTree() {
        boolean[] checked = new boolean[children.size()];
        ArrayList<Tree> newChildren = new ArrayList<>();
        for(int i = 0; i < children.size(); i++) {
            Tree cur = children.get(i);
            if(!cur.clearTree())
                checked[i] = false;
            else {
                checked[i] = true;
                newChildren.add(cur);
            }
        }
        if(newChildren.size() == 0) {
            return false;
        }
        children = newChildren;
        return true;
    }


}
