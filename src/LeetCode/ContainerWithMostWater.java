package LeetCode;

/***
 * LeetCode No11 Medium Level
 */
public class ContainerWithMostWater {
    public int solution(int[] heights) {
        /***
         * 核心思路就是：如果 left<=right,
         *            无论怎么样改变right（注意right只能往左移），面积都只会小于等于当前面积
         *            所以不再需要再遍历整个（left<heights.length), 直接left++，查看下一个组合就好了
         *            同理，如果right<=left,直接right++,
         *            这样就O（n)了
         *
         *            可以配合这个图解来理解：
         *            https://leetcode-cn.com/problems/container-with-most-water/solution/on-shuang-zhi-zhen-jie-fa-li-jie-zheng-que-xing-tu/
         *
         */
        int ret = 0;
        int left=0, right = heights.length-1;
        while(left < right) {
            int area = (right - left) * Math.min(heights[left], heights[right]);
            ret = Math.max(ret, area);

            if (heights[left] <= heights[right])
                left++;
            else
                right--;
        }

        return ret;
    }
}
