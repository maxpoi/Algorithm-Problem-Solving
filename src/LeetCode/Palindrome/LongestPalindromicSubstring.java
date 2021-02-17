package LeetCode.Palindrome;

/***
 * Leetcode No5 Medium Level.
 */
public class LongestPalindromicSubstring {
    /***
     * 中心扩展的思路就不写了，别的题写过了。
     */

    /***
     * DP思路
     * 让 dp[i, j] = subString是回文，
     * 那怎么利用这个信息呢？如果我们是从左往右，会发现其实没什么用。。
     * 那中心扩散？dp好像很难实现，因为dp要么从左往右，从下往上，要么就是从右往左，从下往上
     * 那按照dp这个思路，我们可以发现，xabax是回文，那aba也是回文
     * 那 dp[i, j]是不是回文，只要看 dp[i-1, j+1]是不是回文。
     * i=0，j=0是特殊情况，一定是回文
     * i=0,j=1也是特殊情况，比较charAt(i), charAt(j)
     * i=0, j>1呢？ 发现好像不太行。
     * 但如果换个方向呢？dp[i, j]是不是回文，只要看dp[i+1, j-1]是不是回文。
     * 那i==j的时候，是特殊情况，一定是回文
     * i==j-1的时候，是特殊情况，比较两个字符
     * 当i>j的时候，结束。
     * 那问题就变成了怎么提前求得dp[i+1, j-1]呢？比如求[0,2], 我们需要知道[1,1]。可是我们是从0,0开始遍历的，不可能知道1，1
     * 那可能会想，提前让对角线都变成true就好了。但是求[0,3]呢？，我们需要知道[1,2]。
     * .........(先自己想一下再往下看）
     * 。。。。。。
     * 。。。。。。
     * 。。。。。。
     * 其实不难发现，我们在求[0,3]的时候，需要知道[1,2]，其实就相当于是在求len=4的时候，需要知道len=2的subString的情况。
     * 并且当我们从左往右的时候，i=0, j=i, len=1....len=n， 然后i++不断循环，思路其实是固定起始位置，求每个长度的subString
     * 那我们完全可以换个思路，固定长度，求所有是这个长度的subString
     * 这样就用dp的思路解决了。
     */
    public String dpSolution(String s) {
        int n = s.length();
        boolean[][] dp = new boolean[n][n];

        int start = 0, end = 0;
        for(int len = 0; len<n; len++) {
            for(int i=0; i+len<n; i++) {
                int j=i+len;

                if (len==0) {
                    dp[i][j] = true;
                }

                else if (len==1) {
                    dp[i][j] = s.charAt(i) == s.charAt(j);
                }

                else {
                    dp[i][j] = s.charAt(i) == s.charAt(j) && dp[i+1][j-1];
                }

                if (dp[i][j]) {
                    if (j-i > end-start) {
                        start = i;
                        end = j;
                    }
                }
            }
        }

        return s.substring(start, end+1);
    }

    /***
     * Manacher Algorithm
     * 首先给几个提示：
     *      1) 插入字符，让len永远是奇数。
     *      2) 如果一个回文的长度是2n+1, 定义臂长为n，请问这个信息有用吗？
     *      3）时间复杂度为 O(n)， 空间复杂度为 O(n)
     *      4) 回文对称性！
     *
     *               0  1  2  3  4  5  6  7  8  9  10 11 12 13 14 15 16 17 18
     *  ”ababebaba": #  a  #  b  #  a  #  b  #  e  #  b  #  a  #  b  #  a  #
     *               0  1  0  3  0  3  0  1  0  9  0  1  0  3  0  3  0  1  0
     *                              j           x           i
     *   Let's look at e, which is centered at index 9.
     *   Take a look at i=13, a is centered at index 13, and is a palindromic of size 3 (bab)
     *   Since a & bab is part of the longer palindromic, and palindromic is symmetric,
     *   so a & bab must appear on the left hand side as well! which is at j position.
     *   How to find j? well, i-x = x-j -> j = 2x-i
     *
     *   There are, however, some special situations.
     *   1) Note that the length of the palindromic centered at i >= arm[j], not == arm[j]
     *      For example, take a look at index 1 and index 5.
     *   2) arm[i] will only >= arm[j] iff i-arm[j] & i + arm[j] is within the center arm range.
     *      for example,
     *                  0  1  2  3  4  5  6  7  8  9  10 11
     *                  #  c  #  b  #  c  #  d  #  c  #  b  #  e  #
     *                  0  1  0  3  0  1  0  5  0  1  0  1  0  1  0
     *                           j           c           i
     *      because 11 + arm[3] = 11 + 3 = 14 > c + arm[c] = 12.
     *   3) i > arm[c] + c
     *      if i is outside the range, then a common approach (expand from middle) can be used.
     *
     *   https://leetcode-cn.com/problems/longest-palindromic-substring/solution/zhong-xin-kuo-san-dong-tai-gui-hua-by-liweiwei1419/
     *   This solution combines case 1 + 2 + 3 in a neat way.
     *   The basic idea is to adjust arm before expanding.
     *   case 1 & 2 can be shortened into arm[i] = Math.min(arm[j], c+arm[c]-i)
     *
     *
     *
     */
    public String ManacherAlgorithm(String s) {
        if (s==null || s.length() == 0) {
            return "";
        }

        StringBuilder str = new StringBuilder("#");
        for(int i=0; i<s.length(); i++) {
            str.append(s.charAt(i));
            str.append("#");
        }

        int[] arms = new int[str.length()];
        int lastCenter = 0, maxCenter = 0;
        for(int i=0; i<str.length(); i++) {
            if (lastCenter+arms[lastCenter] > i) {
                int mirror = 2*lastCenter-i;

                // https://leetcode-cn.com/problems/longest-palindromic-substring/solution/zhong-xin-kuo-san-dong-tai-gui-hua-by-liweiwei1419/
                arms[i] = Math.min(lastCenter+arms[lastCenter]-i, arms[mirror]);

                // case 1 & common cases
                if (i + arms[mirror] <= lastCenter + arms[lastCenter]) {
                    arms[i] += expandMiddle(i, str, arms[i]);
                } else {
                    // case 2
                    arms[i] += expandMiddle(i, str, arms[i]);

                    if (arms[i]+i > lastCenter+arms[lastCenter]) {
                        lastCenter = i;
                    }
                }
            } else {
                // case 3
                arms[i] = expandMiddle(i, str, arms[i]);
                lastCenter = i;
            }

            maxCenter = arms[i] > arms[maxCenter] ? i : maxCenter;
        }
        return s.substring((maxCenter-arms[maxCenter])/2, (maxCenter+arms[maxCenter])/2);
    }

    public int expandMiddle(int index, StringBuilder str, int arm) {
        int len = 0;
        int left = index-arm-1, right = index+arm+1;
        while(left > -1 && right < str.length()) {
            if (str.charAt(left) != str.charAt(right)) {
                break;
            }

            left--;
            right++;

            len++;
        }

        return len;
    }

}
