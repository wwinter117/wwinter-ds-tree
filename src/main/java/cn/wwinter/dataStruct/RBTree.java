package cn.wwinter.dataStruct;

/**
 * @author: zhangdd
 * @date: 2023/08/17
 */
public class RBTree extends AbstractTree<Integer> implements Tree<Integer> {
    public RBTree() {
        root = null;
        size = 0;
        high = high();
    }

    public RBTree(Integer... values) {
        root = treeify(values);
        size = values.length;
        high = high();
    }

    public TreeNode<Integer> treeify(Integer[] values) {
        RBTreeNode<Integer> r = null;
        for (Integer val : values) {
            RBTreeNode<Integer> x = new RBTreeNode<>(val);
            if (r == null) {
                x.red = false;
                r = x;
            } else {
                int dir;
                for (RBTreeNode<Integer> p = r, xp; ; ) {
                    if (x.val <= p.val) {
                        dir = -1;
                    } else {
                        dir = 1;
                    }
                    xp = p;
                    p = (RBTreeNode<Integer>) (dir < 0 ? p.left : p.right);
                    if (p == null) {
                        x.parent = xp;
                        if (dir <= 0) {
                            xp.left = x;
                        } else {
                            xp.right = x;
                        }
                        r = balanceInsertion(r, x);
                        break;
                    }
                }
            }
        }
        return r;
    }

    private RBTreeNode<Integer> balanceInsertion(RBTreeNode<Integer> root, RBTreeNode<Integer> x) {
        x.red = true;
        for (RBTreeNode<Integer> xp, xpp, xppl, xppr; ; ) {
            if ((xp = (RBTreeNode<Integer>) x.parent) == null) {
                x.red = false;
                return x;
            }
            if ((xpp = (RBTreeNode<Integer>) xp.parent) == null || !xp.red) {
                return root;
            }
            if (xp == xpp.left) {
                if ((xppr = (RBTreeNode<Integer>) xpp.right) != null && xppr.red) {
                    xp.red = xppr.red = false;
                    xpp.red = true;
                    x = xpp;
                } else {
                    if (x == xp.right) {
                        root = (RBTreeNode<Integer>) leftRotate(root, xp);
                        x = xp;
                        xp = (RBTreeNode<Integer>) x.parent;
                        xpp = xp == null ? null : (RBTreeNode<Integer>) xp.parent;
                    }
                    if (xp != null) {
                        xp.red = false;
                        if (xpp != null) {
                            xpp.red = true;
                            root = (RBTreeNode<Integer>) rightRotate(root, xpp);
                        }
                    }
                }
            } else {
                if ((xppl = (RBTreeNode<Integer>) xpp.left) != null && xppl.red) {
                    xp.red = xppl.red = false;
                    xpp.red = true;
                    x = xpp;
                } else {
                    if (x == xp.left) {
                        root = (RBTreeNode<Integer>) rightRotate(root, xp);
                        x = xp;
                        xp = (RBTreeNode<Integer>) x.parent;
                        xpp = xp == null ? null : (RBTreeNode<Integer>) xp.parent;
                    }
                    if (xp != null) {
                        xp.red = false;
                        if (xpp != null) {
                            xpp.red = true;
                            root = (RBTreeNode<Integer>) leftRotate(root, xpp);
                        }
                    }
                }
            }
        }
    }
//
//    protected TreeNode<Integer> leftRotate(TreeNode<Integer> root, TreeNode<Integer> x) {
//        RBTreeNode<Integer> r, rl, xp;
//        if (x != null && (r = (RBTreeNode<Integer>) x.right) != null) {
//            if ((x.right = rl = (RBTreeNode<Integer>) r.left) != null) {
//                rl.parent = x;
//            }
//            if ((r.parent = xp = (RBTreeNode<Integer>) x.parent) == null) {
//                r.red = false;
//                root = r;
//            } else if (x == xp.left) {
//                xp.left = r;
//            } else {
//                xp.right = r;
//            }
//            r.left = x;
//            x.parent = r;
//        }
//        return root;
//    }
//
//    protected TreeNode<Integer> rightRotate(TreeNode<Integer> root, TreeNode<Integer> x) {
//        RBTreeNode<Integer> l, lr, xp;
//        if (x != null && (l = (RBTreeNode<Integer>) x.left) != null) {
//            if ((x.left = lr = (RBTreeNode<Integer>) l.right) != null) {
//                lr.parent = x;
//            }
//            if ((l.parent = xp = (RBTreeNode<Integer>) x.parent) == null) {
//                l.red = false;
//                root = l;
//            } else if (x == xp.left) {
//                xp.left = l;
//            } else {
//                xp.right = l;
//            }
//            l.right = x;
//            x.parent = l;
//        }
//        return root;
//    }

    static class RBTreeNode<T extends Comparable<T>> extends TreeNode<T> {
        boolean red;

        public RBTreeNode() {
        }

        public RBTreeNode(T val) {
            this.val = val;
        }

        public RBTreeNode(T val, RBTreeNode<T> left, RBTreeNode<T> right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

}

