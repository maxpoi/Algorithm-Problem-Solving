package LeetCode.DFS;

import java.util.List;

/***
 * Leetcode No339
 */

public interface NestedInteger {
    public NestedInteger();

    public NestedInteger(int value);

    public boolean isInteger();
    public Integer getInteger();

    public void setInteger(int value);

    public void add(NestedInteger ni);

    public List<NestedInteger> getList();
}

public class NestedListWeightSum {
    int sum = 0;

    public int depthSum(List<NestedInteger> nestedList) {
        for(NestedInteger ni : nestedList) {
            dfs(ni, 1);
        }

        return sum;
    }

    public void dfs(NestedInteger ni, int depth) {
        if (ni.isInteger()) {
            sum += ni.getInteger() * depth;
            return ;
        }

        for(NestedInteger nextNi : ni.getList()) {
            dfs(nextNi, depth + 1);
        }
    }
}
