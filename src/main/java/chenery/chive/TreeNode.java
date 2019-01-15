package chenery.chive;

import java.util.List;

/**
 *  Generic tree node
 */
public interface TreeNode<T> {

    boolean isLeaf();

    List<T> getChildren();
}
