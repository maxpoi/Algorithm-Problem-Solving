package LeetCode;

import java.util.Arrays;

/***
 * Leetcode No 303 Easy level
 *      * 思路主要是别总想着存下excat solution，
 *      * 像这题就可以用sum[j] - sum[i]来算出 sum[i, j]的值，最后还是O(n)
 */
public class _RangeQuery {


    /***
     * 用dp会有点太费了，因为 sum(i,j) = sum(0, j) - sum (0, i)
     */

    int[] sums;

    public _RangeQuery(int[] nums) {
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
