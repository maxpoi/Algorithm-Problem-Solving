package LeetCode;

import java.util.Arrays;

/***
 * LeetCode No259 Medium Level
 */
public class ThreeSumSmaller {
    public int threeSumSmaller(int[] nums, int target) {
        int ret = 0;
        if (nums.length < 3) return ret;

        Arrays.sort(nums);

        for(int i=0; i<nums.length-2; i++) {

            int restSum = target - nums[i];
            int head = i+1, tail = nums.length-1;

            while(head < tail) {
                int sum = nums[head] + nums[tail];

                if (sum < restSum) {

                    /***
                     * This is the key of optimization.
                     * Since head + tail stasify the condition, every comination with head and j,
                     * where head < j < tail will also stasify the condition.
                     ***/
                    ret += tail - head;
                    head++;

                } else {
                    tail--;
                }
            }
        }

        return ret;
    }
}
