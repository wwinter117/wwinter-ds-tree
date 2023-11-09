package cn.wwinter.dataStruct;


import cn.wwinter.lang.IterableTree;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author: zhangdd
 * @date: 2023/08/21
 */
abstract class AbstractTree<T extends Comparable<T>> implements Tree<T>, IterableTree<T> {
    transient int size;
    transient int high;
    transient TreeNode<T> root;

    transient boneSketcher sketcher;

    public TreeNode<T> root() {
        return root;
    }

    public int size() {
        return size;
    }

    public int high() {
        boneSketcher boneSketcher = new boneSketcher();
        Map<T, Integer> levelMap = boneSketcher.nodeLevel;
        Queue<TreeNode<T>> queue = new LinkedList<>();
        queue.add(root());
        TreeNode<T> r, rl, rr;
        int n, h = 0;
        while (!queue.isEmpty()) {
            n = queue.size();
            for (int i = 0; i < n; i++) {
                r = queue.remove();
                levelMap.put(r.val, h + 1);
                if ((rl = r.left) != null) {
                    queue.add(rl);
                }
                if ((rr = r.right) != null) {
                    queue.add(rr);
                }
            }
            ++h;
        }
        this.sketcher = boneSketcher;
        return high = h;
    }

    public TreeNode<T> treeify(T[] values) {
        throw new UnsupportedOperationException();
    }


    /* <----------- TreeMethods -----------> */

    /**
     * 左旋操作
     *
     * @param root 根节点
     * @param x    旋转节点
     */
    protected TreeNode<T> leftRotate(TreeNode<T> root, TreeNode<T> x) {
        TreeNode<T> r, rl, xp;
        if (x != null && (r = x.right) != null) {
            if ((x.right = rl = r.left) != null) {
                rl.parent = x;
            }
            if ((r.parent = xp = x.parent) == null) {
                root = r;
            } else if (x == xp.left) {
                xp.left = r;
            } else {
                xp.right = r;
            }
            r.left = x;
            x.parent = r;
        }
        return root;
    }

    /**
     * 右旋操作
     *
     * @param root 根节点
     * @param x    旋转节点
     */
    protected TreeNode<T> rightRotate(TreeNode<T> root, TreeNode<T> x) {
        TreeNode<T> l, lr, xp;
        if (x != null && (l = x.left) != null) {
            if ((x.left = lr = l.right) != null) {
                lr.parent = x;
            }
            if ((l.parent = xp = x.parent) == null) {
                root = l;
            } else if (x == xp.left) {
                xp.left = l;
            } else {
                xp.right = l;
            }
            l.right = x;
            x.parent = l;
        }
        return root;
    }


    /* <----------- Iterator -----------> */
    public Iterator<T> levelIterator() {
        return new LevelItr();
    }

    public Iterator<T> PreIterator() {
        return new PreItr();
    }

    public Iterator<T> InIterator() {
        return new InItr();
    }

    public Iterator<T> PostIterator() {
        return new PostItr();
    }

    /**
     * 层次遍历迭代器
     */
    class LevelItr implements Iterator<T> {
        private TreeNode<T> next;
        private TreeNode<T> current;
        private final Queue<TreeNode<T>> queue = new LinkedList<>();

        public LevelItr() {
            TreeNode<T> r = root(), rl, rr;
            next = current = null;
            if (r != null) {
                next = r;
                if ((rl = r.left) != null) {
                    queue.add(rl);
                }
                if ((rr = r.right) != null) {
                    queue.add(rr);
                }
            }
        }

        public boolean hasNext() {
            return next != null;
        }

        public T next() {
            return nextNode().val;
        }

        public TreeNode<T> nextNode() {
            TreeNode<T> e = next, r = null, rl, rr;

            if (e == null) {
                throw new NoSuchElementException();
            }
            current = e;
            next = queue.isEmpty() ? null : (r = queue.remove());
            if (r != null && (rl = r.left) != null) {
                queue.add(rl);
            }
            if (r != null && (rr = r.right) != null) {
                queue.add(rr);
            }
            return e;
        }
    }

    /**
     * 先序遍历迭代器（DLR）
     */
    class PreItr implements Iterator<T> {
        private TreeNode<T> next;
        private TreeNode<T> current;
        private final Stack<TreeNode<T>> stack = new Stack<>();

        public PreItr() {
            TreeNode<T> r = root(), rl, rr;
            next = current = null;
            if (r != null) {
                next = r;
                if ((rr = r.right) != null) {
                    stack.push(rr);
                }
                if ((rl = r.left) != null) {
                    stack.push(rl);
                }
            }
        }

        public boolean hasNext() {
            return next != null;
        }

        public T next() {
            return nextNode().val;
        }

        public TreeNode<T> nextNode() {
            TreeNode<T> e = next, r = null, rl, rr;
            if (e == null) {
                throw new NoSuchElementException();
            }
            current = e;
            next = stack.isEmpty() ? null : (r = stack.pop());
            if (r != null && (rr = r.right) != null) {
                stack.push(rr);
            }
            if (r != null && (rl = r.left) != null) {
                stack.push(rl);
            }
            return e;
        }
    }

    /**
     * 中序遍历迭代器（LDR）
     */
    class InItr implements Iterator<T> {
        private TreeNode<T> next;
        private TreeNode<T> current;
        private final Stack<TreeNode<T>> stack = new Stack<>();

        public InItr() {
            TreeNode<T> r = root(), rl, rr;
            next = current = null;
            if (r != null) {
                do {
                    stack.push(r);
                } while ((r = r.left) != null);
                next = stack.pop();
            }
        }

        public boolean hasNext() {
            return next != null;
        }

        public T next() {
            return nextNode().val;
        }

        public TreeNode<T> nextNode() {
            TreeNode<T> e = next, r;
            if (e == null) {
                throw new NoSuchElementException();
            }
            current = e;
            if ((r = e.right) != null) {
                do {
                    stack.push(r);
                } while ((r = r.left) != null);
            }
            next = stack.isEmpty() ? null : stack.pop();
            return e;
        }
    }

    /**
     * 后序遍历迭代器（RLD）
     */
    class PostItr implements Iterator<T> {
        private TreeNode<T> next;
        private TreeNode<T> current;
        private final Stack<TreeNode<T>> stack1 = new Stack<>();
        private final Stack<TreeNode<T>> stack2 = new Stack<>();

        public PostItr() {
            TreeNode<T> r = root(), rl, rr;
            next = current = null;
            if (r != null) {
                stack1.push(r);
                while (!stack1.isEmpty()) {
                    stack2.push(r = stack1.pop());
                    if ((rr = r.right) != null) {
                        stack1.push(rr);
                    }
                    if ((rl = r.left) != null) {
                        stack1.push(rl);
                    }
                }
                next = stack2.pop();
            }
        }

        public boolean hasNext() {
            return next != null;
        }

        public T next() {
            return nextNode().val;
        }

        public TreeNode<T> nextNode() {
            TreeNode<T> e = next, r, rl, rr;
            if (e == null) {
                throw new NoSuchElementException();
            }
            current = e;
            next = stack2.isEmpty() ? null : stack2.pop();
            return e;
        }
    }

    /* <----------- TreeNode -----------> */

    static class TreeNode<T extends Comparable<T>> {
        T val;
        TreeNode<T> parent;
        TreeNode<T> left;
        TreeNode<T> right;

        public TreeNode() {
        }

        public TreeNode(T val) {
            this.val = val;
        }

        public TreeNode(T val, TreeNode<T> left, TreeNode<T> right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    /* <----------- Sketch -----------> */

    class boneSketcher {
        private final Queue<TreeNode<T>> queue = new LinkedList<>();
        private final Map<T, Integer> nodeLevel = new HashMap<>();
        private void sketch() {
            List<NodeInfo> locateInfo = getInfo();
            String[][] fillDoubleArr = fillDoubleArr(locateInfo);

            int width = fillDoubleArr[0].length;// 宽
            int high = fillDoubleArr.length;// 树的高度
            int g = (width + 1) / 4;// g
            String[] fill1 = new String[width];
            String[] fill2 = new String[width];
            Arrays.fill(fill1, "---");
            Arrays.fill(fill2, "   ");
            String top = Arrays.toString(fill1).replaceAll(",", "-");
            String topLine = " " + top.substring(1, top.length() - 1);
            String blank1 = Arrays.toString(fill2).replaceAll(",", " ");
            String blank = ' ' + blank1.substring(1, blank1.length() - 1) + ' ';
            System.out.println(topLine);
            System.out.println(blank);
            int level = 0;// 当前所在层数
            for (int i = 0; i < high; i++) {
                String[] hriz1 = fillDoubleArr[i];
                String[] hriz2 = null;
                if (i + 1 < high) {
                    hriz2 = fillDoubleArr[i + 1];
                }
                // 打印当前层的所有节点
                String s = Arrays.toString(hriz1).replaceAll(",", " ");
                String levelLine = ' ' + s.substring(1, s.length() - 1) + ' ';
                System.out.println(levelLine);
                // 打印线条
                printLine(width, g, level, hriz1, hriz2, root());
                ++level;
            }
            System.out.println(blank);
            System.out.println(topLine);
        }

        private List<NodeInfo> getInfo() {
            TreeNode<T> root = root(), r, rl, rr;
            int l,count = 0;
            List<NodeInfo> result = new ArrayList<>();
            queue.add(root);
            while (!queue.isEmpty() && !allAppendNode(queue)) {
                r = queue.remove();
                l = r.val == null ? -1 : nodeLevel.get(r.val);
                ++count;
                int locate = (int) (count - Math.pow(2, l - 1)) + 1; // 当前节点在满二叉树中对应层的位置 root-1
                result.add(new NodeInfo(r.val, l, locate));
                if ((rl = r.left) != null) {
                    queue.add(rl);
                } else {
                    queue.add(new TreeNode<>());
                }
                if ((rr = r.right) != null) {
                    queue.add(rr);
                } else {
                    queue.add(new TreeNode<>());
                }
            }
            return result.stream().filter(s -> s.val != null).collect(Collectors.toList());
        }

        private boolean allAppendNode(Queue<TreeNode<T>> queue) {
            return queue.size() == queue.stream().filter(s -> s.val == null).count();
        }

        private String[][] fillDoubleArr(List<NodeInfo> infos) {
            int horiz = 2 * (int) Math.pow(2, (high - 1)) - 1;
            int vertical = high;
            String[][] dArray = new String[vertical][horiz];
            NodeInfo locateInfo;
            int s, q;
            for (int i = 0; i < vertical; i++) {
                s = (int) Math.pow(2, (high - i)); // 步幅:S=2^(H-h+1)
                q = (int) Math.pow(2, (high - i - 1)) - 1; // 每层首节点x位置：Q= 2^(H-h)-1
                for (int j = 0; j < horiz; j++) {
                    locateInfo = getBy(infos, i, j, q, s);
                    if (locateInfo == null) {
                        dArray[i][j] = "   ";
                    } else {
                        dArray[i][j] = "[" + locateInfo.val + "]";
                    }
                }
            }
            return dArray;
        }

        private NodeInfo getBy(List<NodeInfo> locateInfos, int i, int j, int q, int s) {
            NodeInfo locateInfo = null;
            try {
                locateInfo = locateInfos.stream()
                        .filter(t -> (t.level - 1) == i && (q + (s * (t.locate - 1))) == j) // 节点位置：P=(h-1, Q+S*(n-1))
                        .collect(Collectors.toList()).get(0);
            } catch (Exception e) {
                return null;
            }
            return locateInfo;
        }

        private void printLine(int width, int g, int level, String[] hriz1, String[] hriz2, TreeNode<T> tree) {
            if (hriz2 == null) {
                return;
            }
            int l = (int) (g / Math.pow(2, level));
            if (l <= 0) {
                return;
            }
            List<String[]> fillList = new ArrayList<>();
            for (int j = 0; j < l - 1; j++) {
                String[] fill = new String[width];
                Arrays.fill(fill, "   ");
                fillList.add(fill);
            }
            Map<Integer, String> nodeMap = new HashMap<>();
            for (int i = 0; i < hriz1.length; i++) {
                if (hriz1[i].startsWith("[")) {
                    nodeMap.put(i, hriz1[i]);
                }
            }
            for (Integer s : nodeMap.keySet()) {
                String value = nodeMap.get(s);
                TreeNode<T> subTree = getSubTree(tree, value);
                if (subTree == null) {
                    continue;
                }
                int point;
                if (subTree.left != null) {
                    point = s;
                    for (String[] fill : fillList) {
                        fill[--point] = " ^ ";
                    }
                }
                if (subTree.right != null) {
                    point = s;
                    for (String[] fill : fillList) {
                        fill[++point] = " ^ ";
                    }
                }
            }
            if (!fillList.isEmpty()) {
                fillList.forEach(s -> {
                    String s1 = Arrays.toString(s).replaceAll(",", " ");
                    String s2 = ' ' + s1.substring(1, s1.length() - 1) + ' ';
                    System.out.println(s2);
                });
            }
        }

        private TreeNode<T> getSubTree(TreeNode<T> root, String data) {
            return deepRecursion(root, t -> ("[" + t.val + "]").equals(data));
        }

        private TreeNode<T> deepRecursion(TreeNode<T> root, Predicate<TreeNode<T>> predicate) {
            if (root == null) {
                return null;
            }
            if (predicate.test(root)) {
                return root;
            }
            TreeNode<T> leftResult = deepRecursion(root.left, predicate);
            TreeNode<T> rightResult = deepRecursion(root.right, predicate);
            return leftResult != null ? leftResult : rightResult;
        }

        class NodeInfo {
            T val;
            int level;
            int locate;

            public NodeInfo(T val, int level, int locate) {
                this.val = val;
                this.level = level;
                this.locate = locate;
            }
        }
    }
    public void sketch() {
        this.sketcher.sketch();
    }

}
