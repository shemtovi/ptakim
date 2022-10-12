package com.example.ptakim;

import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {
    @Override
    public int compare(Node firstNode, Node secondNode) {
        return  firstNode.getNum() - secondNode.getNum();
        /*if (firstNode.getNum() > secondNode.getNum())
            return 1;
        else
            return -1;*/
    }


}
