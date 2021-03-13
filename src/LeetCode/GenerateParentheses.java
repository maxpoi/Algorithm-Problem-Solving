package LeetCode;

import java.util.ArrayList;
import java.util.List;

/***
 * Leetcode No 22. Medium Level
 */
public class GenerateParentheses {

    public List<String> solution(int n) {
        List<String> ret = new ArrayList<>();
        backRecursion(ret, new StringBuilder(), 0, 0, n);
        return ret;
    }

    public void backRecursion(List<String> ret, StringBuilder str, int left, int right, int n) {
        if (str.length() == n*2) {
            ret.add(str.toString());
            return ;
        }

        if (left < n) {
            str.append('(');
            backRecursion(ret, str, left+1, right, n);
            str.deleteCharAt(str.length()-1);
        }

        if (right < left) {
            str.append(')');
            backRecursion(ret, str, left, right+1, n);
            str.deleteCharAt(str.length()-1);
        }
    }
}