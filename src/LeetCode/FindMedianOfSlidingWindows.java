package LeetCode;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/***
 * Leetcode no480
 * Find the median of each sliding window
 */
public class FindMedianOfSlidingWindows {

    /***
     * 思路1：sort每个滑动窗口，然后找到median -> (k + klog(k) + k/2) * (n-k+1)
     * 这个思路当然是不可取的
     */
    public double[] robustSolution(int[] nums, int k) {
        double[] ret = new double[nums.length-k+1];

        int start = 0;
        while(start + k <= nums.length) {
            int[] temp = Arrays.copyOfRange(nums, start, start+k);
            Arrays.sort(temp);
            ret[start] = k%2 == 0 ? ((double)temp[k/2-1] + (double)temp[k/2]) / 2.0 : (double) temp[k/2];
            start++;
        }

        return ret;
    }

    /***
     * 思路2：如何改善思路1呢？
     *       主要就是要怎么省掉sort的步骤，如果边线性读数据的时候，边”排序“呢？
     *       因为median是最中间的数，可以尝试记录小于pivot的所有数，以及大于pivot的所有数
     *       比如两个queue（small, big),先放入small，如果下一个数比small更大，就放入big，反之放入small
     *       但如果读取的数据是1，2，3或者3，2，1怎么办？
     *       分析一下可以发现，1，2，3这种情况只要发现size(big) > size(small), 就把big的top数放入small就好了
     *       而3，2，1这样的情况，只要size(small) + 1 > size(big)，就把small的top放入big就好了
     *       详情见官方题解
     */
    public double[] twoHeap(int[] nums, int k) {
        double[] ret = new double[nums.length-k+1];

        Queue<Integer> small = new LinkedList<>();
        Queue<Integer> big = new LinkedList<>();

        /***
         * 还有一个可以改进的点，如果每次滑动窗口变了都重新读一次数据，会有点浪费时间
         * 可以尝试每次都是删除上一个int，加入一个新int
         */
        for(int i=0; i<k; i++) {
            if (small.isEmpty() || nums[i] <= small.peek()) {
                small.add(nums[i]);
            } else {
                big.add(nums[i]);
            }
        }

        return ret;
    }

    /***
     * 思路3：思路2有点太过于复杂了，有没有办法更”聪明的“省掉sort的步骤吗？
     *       查了一下还真有，叫Median of Medians algorithm
     *       是quickselect algorithm的一个特例，把array分成若干个size为5的sub array
     *       只有这样才是O（n)
     *       https://ahuigo.github.io/b/algorithm/sort-topk#/
     */
    public double[] medianOfMedians(int[] nums, int k) {

    }

    public int pivotIndex(int[] nums) {

    }

    // This function group a list into 3 parts, <pivot, =pivot, and >pivot, in linear time
    public int partition(int[] nums, int pivotIndex) {
        int pivot = nums[pivotIndex];
        nums[pivotIndex] = nums[0];
        int left = 0, right = nums.length-1;
        while(left < right) {
            while (nums[right] < pivot) {
                right--;
            }
        }
    }
}
