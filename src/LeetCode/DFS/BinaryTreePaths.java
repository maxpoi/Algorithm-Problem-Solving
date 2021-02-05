package LeetCode.DFS;

import LeetCode.TreeNode;

import java.util.ArrayList;
import java.util.List;

/***
 * Leetcode No257
 */
public class BinaryTreePaths {

    public List<String> solution(TreeNode root) {
        List<String> ret = new ArrayList<>();
        dfs(root, ret, "");

        return ret;
    }

    public void dfs(TreeNode root, List<String> ret, String string) {
        if (root == null) return ;

        if (string.equals("")) {
            string += root.val;
        } else {
            string += "->" + root.val;
        }

        if (root.left == null && root.right == null) {
            ret.add(string);
        }

        String left = string;
        String right = string;

        dfs(root.left, ret, left);
        dfs(root.right, ret, right);
    }
}
