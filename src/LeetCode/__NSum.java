package LeetCode;

import java.util.*;

/***
 * Leetcode No170
 * 一个思路
 */

public class __NSum {

    public class TwoSums {

        private HashMap<Integer, Integer> container;

        /** Initialize your data structure here. */
        public TwoSums() {
            this.container = new HashMap<>();
        }

        /** Add the number to an internal data structure.. */
        public void add(int number) {
            if (this.container.containsKey(number)) {
                this.container.replace(number, this.container.get(number)+1);
            } else {
                this.container.put(number, 1);
            }
        }

        /** Find if there exists any pair of numbers which sum is equal to the value. */
        public boolean find(int value) {
            for(Map.Entry<Integer, Integer> entry : this.container.entrySet()) {
                int rest = value - entry.getValue();
                if (rest != entry.getValue()) {
                    if (this.container.containsKey(rest)) {
                        return true;
                    }
                } else {
                    if (entry.getValue() > 1) {
                        return true;
                    }
                }
            }

            return false;
        }
    }


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

}
