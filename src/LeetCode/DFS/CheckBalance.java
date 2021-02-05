package LeetCode.DFS;

import LeetCode.TreeNode;

/***
 * Leetcode 面试题04.04
 */
public class CheckBalance {

    public boolean solution(TreeNode root) {
        int height = checkHeight(root);
        return height != -1;
    }

    public int checkHeight(TreeNode root) {
        if (root == null) return 0;

        int leftHeight = checkHeight(root.left);
        int rightHeight = checkHeight(root.right);

        if (leftHeight == -1 || rightHeight == -1) {
            return -1;
        }

        if (Math.abs(leftHeight-rightHeight) <= 1) {
            return Math.max(leftHeight, rightHeight) + 1;
        } else {
            return -1;
        }
    }
}
