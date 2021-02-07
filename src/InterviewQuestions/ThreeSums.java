package InterviewQuestions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/***
 * 这是一个微软面试题
 * 给一个int[], 一个target int，写一个函数，返回所有总和是target int的三个数字组合，需要去重
 * 所有数字都在2^32范围内
 */

public class ThreeSums {
    public List<List<Integer>> solution(int[] nums, int target) {
        List<List<Integer>> ret = new ArrayList<>();
        if (nums.length < 3) return ret;

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
                    if ((head == i+1 && tail == nums.length-1) ||
                            (nums[head] != nums[head-1] || nums[tail] != nums[tail+1])) {
                        List<Integer> ans = Arrays.asList(nums[i], nums[head], nums[tail]);
                        ret.add(ans);
                    }

                    head++;
                    tail--;

                } else if (sum < restSum) {
                    head++;
                } else {
                    tail--;
                }
            }
        }

        return ret;
    }
}
