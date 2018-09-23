package github.pair.generator.bean.tree;

import github.pair.generator.bean.Symbol;
import github.pair.generator.util.RandomUtil;
import java.util.Random;

/**
 * 表达式实体类，使用二叉树出具结构存储
 * 叶子节点存储数字，其他结点存储操作符
 */
public class ExpressionTree {
    // 根结点
    private Node<Symbol> root;
    // 操作符的个数
    private int operatorCount;

    /**
     * 根据操作符个数构造二叉树
     * @param operatorCount 操作符个数
     */
    public ExpressionTree(int operatorCount) {
        if (isValidOpCount(operatorCount)){
            this.operatorCount = operatorCount;
            this.root = new Node<>(RandomUtil.generateOperator());
            init();
        }else {
            throw new RuntimeException("invalid count of operators");
        }
    }

    /**
     * 根据运算符的个数构建二叉树
     * 随机插入操作符和数字结点
     */
    private void init() {
        // 随机获取(operatorCount -1)个操作符，用它们构建结点并插进二叉树
        for (int i = 0; i < operatorCount - 1; i++) {
            insertOperators(root, RandomUtil.generateOperator());
        }
        // 插入数字结点
        insertNumbers(root);
    }

    /**
     * 递归插入运算符
     * @param origin  待处理的结点
     * @param symbol 新增加结点的数据
     */
    private void insertOperators(Node origin, Symbol symbol) {
        // 随机插入左结点或右结点
        boolean traverseLeft = new Random().nextBoolean();
        if (traverseLeft) {
            if (origin.getLeft() != null) {
                insertOperators(origin.getLeft(), symbol);
            }else {
                origin.setLeft(new Node<>(symbol));
                origin.getLeft().setParent(origin);
            }
        }
        else {
            if (origin.getRight() != null) {
                insertOperators(origin.getRight(), symbol);
            }else {
                origin.setRight(new Node<>(symbol));
                origin.getRight().setParent(origin);
            }
        }
    }

    /**
     * 在构造好的二叉树中为每一个操作符结点添加数字子结点
     * @param origin  待处理结点
     * @return 处理结果标记,0代表origin结点操作结束，1代表待处理结点的父结点仍需继续递归
     */
    private int insertNumbers(Node origin) {
        if (origin == null || !(origin.getData() instanceof Symbol)) {
            // 不需递归origin的任何结点
            return 0;
        }else { // 是操作符
            // 操作符是叶子结点, 如果非root，添加两个子结点，结束; 是root，仍需继续遍历
            if (origin.getLeft() == null && origin.getRight() == null) {
                origin.setLeft(new Node<>(RandomUtil.generateFraction()));
                origin.getLeft().setParent(origin);

                origin.setRight(new Node<>(RandomUtil.generateFraction()));
                origin.getRight().setParent(origin);

                if (origin != root) {
                    return 1;
                }
            }
            // 操作符是具有一个子节点的非叶子结点, 如果非root，添加一个子结点，结束; 是root，仍需继续遍历
            if (origin.getRight() == null && origin.getLeft() != null) {
                origin.setRight(new Node<>(RandomUtil.generateFraction()));
                origin.getRight().setParent(origin);

                // 新插入的一侧一定是数字，不用递归，只需向非空的一侧（有可能是操作符）递归
                return insertNumbers(origin.getLeft());
            }
            if (origin.getLeft() == null && origin.getRight() != null) {
                origin.setLeft(new Node<>(RandomUtil.generateFraction()));
                origin.getLeft().setParent(origin);
                return insertNumbers(origin.getRight());
            }
            if (origin.getLeft() != null && origin.getRight() != null) {
                if (insertNumbers(origin.getLeft()) == 0) {
                    return 0;
                }
                return insertNumbers(origin.getRight());
            }
        }
        return 1;
    }

    /**
     * 判断操作符个数是否合法
     * @param operatorCount 操作符个数
     * @return  是否合法
     */
    private boolean isValidOpCount(int operatorCount) {
        return operatorCount > 0;
    }

}
