package LeetCode;

import java.util.Arrays;

/***
 * Leetcode No 303 Easy level
 */
public class RangeQuery {


    /***
     * 用dp会有点太费了，因为 sum(i,j) = sum(0, j) - sum (0, i)
     */

    int[] sums;

    public RangeQuery(int[] nums) {
        int n = nums.length;
        sums = new int[n + 1];
        for (int i = 0; i < n; i++) {
            sums[i + 1] = sums[i] + nums[i];
        }
    }

    public int sumRange(int i, int j) {
        return sums[j + 1] - sums[i];
    }

}
