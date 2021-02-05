package LeetCode;

/***
 * Leetcode no26
 */
public class RemoveDuplicatesFromSortedArray {
    public int removeDuplicates(int[] nums) {
        int len = nums.length;
        if (len <= 1) return len;

        /***
         * 因为nums是sorted，所以可以保留两个指针
         *  1.指出我们现在在查看的num是什么
         *  2. 一个遍历指针
         * 这样我们可以用遍历指针遍历整个array，如果指向的num和现在在查看的num不一样，
         *      1. 则把指针2的数字保存到指针1的下一位
         *      2. 两个指针都++
         * 否则就遍历指针++
         */
        int currUniqueInt = 0, arrayIndex = 1;
        while(arrayIndex < nums.length) {
            if (nums[currUniqueInt] != nums[arrayIndex]) {
                nums[++currUniqueInt] = nums[arrayIndex++];
            } else {
                arrayIndex++;
                len--;
            }
        }

        return len;
    }
}
