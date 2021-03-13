package LeetCode;

import java.util.Stack;

/***
 * Leetcode No 503. Medium
 */

public class NextGreaterElement {


    /***
     *x
     * 53241 -> -14455。
     * 54321 -> -15555
     *
     * 尝试的时候会遇到的问题就是，
     * 你可能需要一直往右遍历m个字符才能找到对应的数
     * 但也有可能需要一直往左遍历n个字符才能找到对应的数
     * 那很自然就会去想，有没有办法同时往左往右看？
     * 但是就算同时往左往右看，如果不想办法利用起"已经看过的num"这样的信息，
     * 仍然还是会是O(n^2)
     * 那可以分两次看，第一次往右边看，第二次从0开始往这看
     * 或者loop 2n而不是n，这样就是2n, O(n)
     * 但是不能在loop index的时候就开始左右看，因为这样仍然是n^2
     *
     * 其实想简单一点，loop 2n就可以了，从index loop到n，然后从0 loop到index
     *
     */
    public int[] solution(int[] nums) {
        int[] ret = new int[nums.length];

        Stack<Integer> waiting = new Stack<>();
        for(int i=0; i<nums.length*2-1; i++) {
            int index = i % nums.length;
            while(!waiting.isEmpty() && nums[waiting.peek()] < nums[index]) {
                ret[waiting.pop()] = nums[index];
            }
            waiting.push(index);
        }

        while(!waiting.isEmpty()) {
            ret[waiting.pop()] = -1;
        }

        return ret;
    }
}
