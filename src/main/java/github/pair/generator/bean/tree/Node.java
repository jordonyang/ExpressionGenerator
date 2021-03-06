package github.pair.generator.bean.tree;

import lombok.Getter;
import lombok.Setter;

/**
 * 二叉树中的结点，用来存储操作符进或操作数信息
 * @param <T> 结点中存储数据类型，可选值为Symbol和Fraction
 */
@Getter
@Setter
class Node<T> {
    private T data;
    private Node left;
    private Node right;
    private Node parent;

    Node(T data) {
        this.data = data;
    }

    boolean hasChild(){
        return left != null || right != null;
    }
}
