package com.example.ptakim

class NodeComparator : Comparator<Node> {
    override fun compare(firstNode: Node, secondNode: Node): Int {
        return firstNode.num - secondNode.num
        /*if (firstNode.getNum() > secondNode.getNum())
            return 1;
        else
            return -1;*/
    }
}