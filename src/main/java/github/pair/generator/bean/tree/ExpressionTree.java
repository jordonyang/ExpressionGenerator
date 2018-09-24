package github.pair.generator.bean.tree;

import github.pair.generator.bean.Fraction;
import github.pair.generator.bean.Symbol;
import github.pair.generator.util.Calculator;
import github.pair.generator.util.RandomUtil;
import lombok.Getter;
import lombok.Setter;

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

    /**
     * 中序递归遍历二叉树，根据二叉树结构及操作符优先级添加括号
     * 生成并记录每一个操作符结点对应的子表达式，同时计算并记录该子表达式对应的结果
     * @param node 待处理结点
     * @return 每个结点的遍历结果，包括表达式及计算结果
     */
    private TraverseResult inorderTraverse(Node node) {
        if (node == null)
            return new TraverseResult("", null);

        if (!node.hasChild()) {
            // 数据结点
            return new TraverseResult(node.getData().toString(), (Fraction) node.getData());
        }

        // 获取左右子表达式及其计算结果
        TraverseResult leftResult = inorderTraverse(node.getLeft());
        TraverseResult rightResult = inorderTraverse(node.getRight());

        // 获取父结点的操作符，将之与左右子表达式组合计算得到新的更长的子表达式
        Node parent = node.getParent();
        Fraction resultData = Calculator.calculate(node.getData().toString(),
                leftResult.getData(), rightResult.getData());

        String rawExpression = leftResult.getSubExpression() + " " + node.getData() + " " +
                rightResult.getSubExpression();

        /*
         * 如果当前结点操作符为'-'，且得到的左右子表达式计算结果为负数，则对该计算结果取绝对值
         * 同时将左右表达式位置交换，对于原二叉树中的数据结构则不作处理
         */
        if (node.getData() == Symbol.SUBTRACT && resultData.getNumerator() < 0) {
            resultData = new Fraction(Math.abs(resultData.getNumerator()), resultData.getDenominator());
            rawExpression = rightResult.getSubExpression() + " " + node.getData() + " " +
                    leftResult.getSubExpression();
        }

        TraverseResult finalResult = new TraverseResult();
        finalResult.setData(resultData);

        // 与父结点操作符的优先级比较，如果比之小或与之同级，则必须加上括号
        if (parent != null && ((Symbol)parent.getData()).priGreaterThan((Symbol)node.getData())) {
            finalResult.setSubExpression(Symbol.LEFT_BRACKET + rawExpression + Symbol.RIGHT_BRACKET);
        }else {
            finalResult.setSubExpression(rawExpression);
        }

        return finalResult;
    }

    /**
     * 获取完整的表达式字符串
     * @return 表达式字符串
     */
    @Override
    public String toString() {
        TraverseResult result = inorderTraverse(root);
        return result.getSubExpression() + " = " + result.getData();
    }

    /**
     * 获取根结点的遍历结果
     * @return 遍历结果
     */
    public TraverseResult getResult() {
        return inorderTraverse(root);
    }

    /**
     * 静态内部类，用于记录每个操作符结点的遍历结果
     */
    @Getter
    @Setter
    public static class TraverseResult {
        // 子表达式
        private String subExpression;
        // 当前结点的下的左右子树的计算结果
        private Fraction data;

        private TraverseResult() {
        }

        private TraverseResult(String subExpression, Fraction data) {
            this.subExpression = subExpression;
            this.data = data;
        }

        @Override
        public String toString() {
            return subExpression + " = " + data;
        }
    }
}
