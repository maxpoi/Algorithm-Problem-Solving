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
    public ArrayList<int[]> solution(int[] nums, int target) {
        Arrays.sort(nums);
        ArrayList<int[]> ret = new ArrayList<>();

        int lastA = nums[0];
        for(int i=0; i<nums.length; i++) {
            if (nums[i] != lastA || i==0) {
                int restSum = target - nums[i];
                lastA = nums[i];

                int head = i+1, tail = nums.length-1;
                int lastB = nums[head], lastC = nums[tail];
                while(head != tail) {
                    if ((nums[head] != lastB || nums[tail] != lastC)
                            || (head == i+1 && tail == nums.length-1)) {

                        lastB = nums[head];
                        lastC = nums[tail];

                        int sum = nums[head] + nums[tail];
                        if (sum == restSum) {
                            int[] ans = new int[]{nums[i], nums[head], nums[tail]};
                            ret.add(ans);
                            head++;
                            tail--;
                        } else if (sum < target) {
                            head++;
                        } else {
                            tail--;
                        }

                    }

                }
            }
        }

        return ret;
    }
}
