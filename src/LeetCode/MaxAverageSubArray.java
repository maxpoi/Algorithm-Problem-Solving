package LeetCode;

/***
 * Leetcode No643
 */
public class MaxAverageSubArray {
    public double solution(int[] nums, int k) {
        double sum = 0, ret = 0;
        for(int i=0; i<k; i++) {
            sum += (double) nums[i];
        }
        ret = sum;

        for(int i=k; i<nums.length; i++) {
            sum -= nums[i-k];
            sum += (double) nums[i];
            ret = Math.max(sum, ret);
        }

        return ret / k;
    }
}
