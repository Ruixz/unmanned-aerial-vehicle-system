/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tools;

import java.util.Comparator;

/**
 *
 * @author skipper
 */
public class NodeFComparator implements Comparator<Node> {

    @Override
    public int compare(Node t1, Node t2) {
        return t1.getF()-t2.getF();
    }
    
}
