package LeetCode;

import java.util.Arrays;

/***
 * LeetCode No16. Medium Level
 */
public class ClosestThreeSum {
    public int threeSumClosest(int[] nums, int target) {
        int best = nums[0]+nums[1]+nums[2];

        Arrays.sort(nums);

        for(int i=0; i<nums.length-2; i++) {
            if (i > 0 && nums[i] == nums[i-1]) {
                continue;
            }

            int restSum = target - nums[i];
            int head = i+1, tail = nums.length-1;

            while(head < tail) {
                int sum = nums[head] + nums[tail];

                if (sum == restSum) {
                    return target;
                } else if (sum < restSum) {
                    head++;
                } else {
                    tail--;
                }

                if (Math.abs(sum+nums[i] - target) < Math.abs(best - target)) {
                    best = sum+nums[i];
                }
            }
        }

        return best;
    }
}
