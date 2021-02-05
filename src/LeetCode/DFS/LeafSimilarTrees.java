package LeetCode.DFS;

import LeetCode.TreeNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/***
 * Leetcode No872
 */
public class LeafSimilarTrees {

    /***
     * 唯一要提一下的思路是，本来我用的是Stack，
     * 但是结果发现List可以直接比较两个list是不是一模一样的。。。
     */
    public boolean solution(TreeNode root1, TreeNode root2) {
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();

        retriveLeaf(root1, list1);
        retriveLeaf(root2, list2);

        return list1.equals(list2);
    }

    public void retriveLeaf(TreeNode root, List<Integer> list) {
        if (root == null) return;

        if (root.left == null && root.right == null) {
            list.add(root.val);
        }

        retriveLeaf(root.left, list);
        retriveLeaf(root.right, list);
    }
}
