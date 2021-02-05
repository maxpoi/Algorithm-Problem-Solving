package LeetCode.DFS;

import LeetCode.TreeNode;

/***
 * Leetcode 剑指offer 55 I
 */
public class BinaryTreeDepth {
    public int maxDepth(TreeNode root) {
        return dfs(root, 0);
    }

    public int dfs(TreeNode root, int depth) {
        if (root == null) return depth;

        return Math.max(dfs(root.left, depth+1), dfs(root.right, depth+1));
    }
}
