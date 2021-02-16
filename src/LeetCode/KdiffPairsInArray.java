package LeetCode;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

/***
 * Leetcode No 532 Medium Level
 */
public class KdiffPairsInArray {

    /***
     * 循环一边靠hash来找pair的思路很容易想到
     * 难点主要是查重。。
     * 为了查重可能会想到要sort一遍，这样就不用额外的空间，但是这样时间复杂度增加了不少
     * 也可能会想额外添加一个hash表，来存找到过的pair，但java没有tuple这样的选项，而且需要2n的space，好像可以优化的样子
     *
     * 有个巧妙的存法，比如 a<b<c<d, a+b = b+c = c+d 如果要存pair会产生之前的问题
     * 不过仔细一看，我们只需要存买个pair中较小的那个数，就100%不会重复，也不会漏掉任何一个pair。
     */
    public int solution(int[] nums, int k) {
        HashSet<Integer> ret = new HashSet<>();
        HashSet<Integer> seen = new HashSet<>();
        for(int i : nums) {
            if (seen.contains(i-k)) {
                ret.add(i-k);
            }

            if (seen.contains(i+k)) {
                ret.add(i);
            }

            seen.add(i);
        }

        return ret.size();
    }
}
