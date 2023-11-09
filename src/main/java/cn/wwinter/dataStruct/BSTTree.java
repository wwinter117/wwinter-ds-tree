package cn.wwinter.dataStruct;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author: zhangdd
 * @date: 2023/08/22
 */
public class BSTTree extends AbstractTree<Integer> implements Tree<Integer> {
//    private final BSTTreeNode root;

    public BSTTree() {
        this.root = null;
        this.high = high();
        this.size = 0;
    }

    public BSTTree(BSTTreeNode root) {
        this.root = root;
        this.high = high();
    }

    public BSTTree(BSTTreeNode root, int n) {
        this.root = root;
        this.high = high();
        this.size = n;
    }

    /**
     * 生成所有不同的BST树
     */
    public static List<BSTTree> gen(int n) {
        return generateTrees2(n).stream().map(s -> new BSTTree(s, n)).collect(Collectors.toList());
    }

    public static List<BSTTreeNode> generateTrees2(int n) {
        List<BSTTreeNode> pre = new ArrayList<>();
        if (n == 0) {
            return pre;
        }
        pre.add(null);
        for (int i = 1; i <= n; ++i) {
            List<BSTTreeNode> cur = new ArrayList<>();
            for (BSTTreeNode root : pre) {
                BSTTreeNode insert = new BSTTreeNode(i);
                insert.left = root;
                cur.add(insert);
                for (int j = 0; j < n; ++j) {
                    BSTTreeNode treeCopy = treeCopy(root);
                    BSTTreeNode p = treeCopy;
                    for (int k = 0; k < j; ++k) {
                        if (p == null) {
                            break;
                        }
                        p = (BSTTreeNode) p.right;
                    }
                    if (p == null) {
                        break;
                    }
                    insert = new BSTTreeNode(i);
                    TreeNode<Integer> pr = p.right;
                    p.right = insert;
                    insert.left = pr;
                    cur.add(treeCopy);
                }
            }
            pre = cur;
        }
        return pre;
    }

    private static BSTTreeNode treeCopy(BSTTreeNode root) {
        if (root == null) {
            return root;
        }
        BSTTreeNode copyRoot = new BSTTreeNode(root.val);
        copyRoot.left = treeCopy((BSTTreeNode) root.left);
        copyRoot.right = treeCopy((BSTTreeNode) root.right);
        return copyRoot;
    }

//    public static List<BSTTreeNode> generateTrees3(int n) {
//        List<BSTTreeNode>[] dp = new ArrayList[n + 1];
//        dp[0] = new ArrayList<>();
//        if (n == 0) {
//            return dp[0];
//        }
//        dp[0].add(null);
//        // 1~n迭代填充dp
//        for (int i = 1; i <= n; ++i) {
//            dp[i] = new ArrayList<>();
//            // 第i个节点中所有的可能结果
//            for (int root = 1; root <= i; ++root) {
//                int leftLen = root - 1;
//                int rightLen = i - root;
//                for (BSTTreeNode left : dp[leftLen]) {
//                    BSTTreeNode node = new BSTTreeNode(root);
//                    dp[i].add(node);
//                }
//            }
//        }
//        return dp[n];
//    }

    public static List<BSTTreeNode> generateTrees(int n) {
        List<BSTTreeNode> res = new ArrayList<>();
        if (n < 1) {
            return res;
        }
        return genItr(1, n);
    }

    private static List<BSTTreeNode> genItr(int start, int end) {
        ArrayList<BSTTreeNode> res = new ArrayList<>();
        if (start > end) {
            res.add(null);
            return res;
        }
        for (int i = start; i <= end; i++) {
            List<BSTTreeNode> leftTrees = genItr(start, i - 1);
            List<BSTTreeNode> rightTrees = genItr(i + 1, end);
            for (BSTTreeNode leftTree : leftTrees) {
                for (BSTTreeNode rightTree : rightTrees) {
                    BSTTreeNode node = new BSTTreeNode(i);
                    node.left = leftTree;
                    node.right = rightTree;
                    res.add(node);
                }
            }
        }
        return res;
    }


    public static class BSTTreeNode extends TreeNode<Integer> {
        public BSTTreeNode(Integer val) {
            super(val);
        }
    }

}
