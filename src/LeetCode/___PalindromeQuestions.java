package LeetCode;

import java.util.HashSet;

public class ___PalindromeQuestions {
    /***
     * Leetcode No647 Medium Level
     * 主要是学KMP和中心扩展和马拉车
     */
    public class PalindromicSubStrings {

        /***
         * Okay...因为回文的题一直都不太擅长，这次思路会写得清楚一点
         *
         * 要找出所有的是回文的subString，正常的思路就会知道直接穷举所有的subString，然后看看是不是回文，
         * 但这会导致 O(n^3) 的时间复杂度，O(n^2)是生成所有的subString，额外的一次n是检查是不是回文
         * 继续按照这个思路想下去，会发现我们是从左至右生成所有的subString，没有办法写出O(1)的检查回文的方法，
         * 而且也没有办法利用起 “从i~j是一个回文” 的这个明显就很有用的信息。
         * 那要怎么利用起这个信息呢？很自然就会想到：如果我知道 “1~3"是回文，那只要判断 "0~3（偶数）, 0~4（奇数）, 1~4（偶数）"是不是回文
         * 但这样明显有一个很严重的问题，一个就是很乱，没有足够明显的规律，代码不好写，还有一个就是还是会重复
         * 比如我们检查完了0~3,0~4,1~4之后，接下来检查什么呢？是以1~3为基础继续扩大呢，还是从新的三个subString继续扩展呢？ 明显都是不可行的。
         *
         * 然后可能就会陷入一个思路死胡同，比如我怎么生成下一个subString？我需要分开判断偶数和奇数的情况吗？1~3可以拓展成0~3；0~4；1~4，
         * 到底选哪个展开呢？我到底是left++ & right++，还是只left++或者right++呢？？等等等等。。。别慌！
         *
         * 接下来回文题的核心点就来了，这一点一定要想明白！
         * 一个subString是不是回文，它的中心位置才是关键！
         * 比如1~3是回文，中心就是2；0~3 -> 中心是1&2；0~4的中心是2，1~4的中心是2&3
         *
         * 这样的会发现，其实上一个思路是对了一半的。我们之所以会陷入那样的混乱，是因为中心在不断的变化。
         * 而且不难发现，奇数的回文中心是一个位置，偶数的回文中心是两个位置，
         * 并且！不管是奇数还是偶数的中心，往外拓展的时候都是基于现在的中心开始 left++ && right++！
         * 所以如果我们固定中心是2，那么就可以用两个指针分别指向两边，然后不断地拓展，知道左指针和右指针不相等。
         *
         * 诶好像还挺靠谱，那时间复杂度是多少呢？
         * 问题就来到一个String有多少个回文中心。答案是2*n-1个。
         * 比如 str = abcd.
         * 它的中心（注意，是所有subString的中心，不是所有的subString）分别是：
         *      a（”a")
         *      ab (”ab")
         *      b ("b", "abc")
         *      bc ("bc", "abcd")
         *      c ("c", "bcd")
         *      cd ("cd")
         *      d ("d")
         * 对于每一个中心，我们都会往外不断的扩展，所以总时间复杂度是 O(n^2)， 而且check的时间是 o(1)!!
         */
        public int solution(String s) {
            int ret = 0;

            for(int i=0; i<s.length()-1; i++) {
                ret += findAllSubPalindromicStrings(i, i, s);
                ret += findAllSubPalindromicStrings(i, i+1, s);
            }

            ret += findAllSubPalindromicStrings(s.length()-1, s.length()-1, s);

            return ret;
        }

        public int findAllSubPalindromicStrings(int middleLeft, int middleRight, String s) {
            int count = 0;

            while(middleLeft > -1 && middleRight < s.length()) {
                if (s.charAt(middleLeft) != s.charAt(middleRight)) {
                    return count;
                }

                middleLeft--;
                middleRight++;

                count++;
            }

            return count;
        }
    }


    /***
     * Leetcode No266 Easy Level
     */
    public class PalindromePermutation {
        public boolean solution(String s) {
            if (s.length() == 0) return false;

            HashSet<Character> container = new HashSet<>();
            for(Character c : s.toCharArray()) {
                if (container.contains(c)) {
                    container.remove(c);
                } else {
                    container.add(c);
                }
            }

            return s.length() % 2 == 0 ? container.size() == 0 : container.size() == 1;
        }
    }

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

    /***
     * Leetcode No 214 Hard Level
     */
    public class ShortestPalindrome {

        /***
         * This solution is slow, O(n^2), n for generate center, n for check if the subString qualifies
         */
        public String brutalForceSolution(String s) {
            if (s.length() == 0) return s;
            String ret = s, sub = null;

            int c = s.length() / 2;
            while(c > 0) {

                sub = validPalindromeSubString(c-1, c, s);
                if (sub != null && sub.length() < ret.length()) {
                    ret = sub;
                }

                // 回文长度为单数的情况要额外考虑，因为要保证右边长度始终大于等于左边长度
                if (s.length() % 2 == 0) {
                    sub = validPalindromeSubString(c - 1, c - 1, s);
                } else {
                    sub = validPalindromeSubString(c, c, s);
                }

                if (sub != null && sub.length() < ret.length()) {
                    ret = sub;
                }

                if (ret.length() < s.length()) {
                    break;
                }

                c--;
            }

            if (c == 0) {
                StringBuilder retStr = new StringBuilder();
                retStr.append(s.charAt(0));
                for(int i=1; i<s.length(); i++) {
                    retStr.insert(0, s.charAt(i));
                    retStr.append(s.charAt(i));
                }
                return  retStr.toString();
            }

            return ret+s;
        }

        /***
         * @return Queue of characters. There are 2 different return situations:
         *              1) null -> current center is not qualified
         *              2) queue with size >= 0;
         */
        public String validPalindromeSubString(int middleLeft, int middleRight, String s) {
            StringBuilder str = new StringBuilder();

            while(middleLeft > -1 && middleRight < s.length()) {
                if (s.charAt(middleLeft) != s.charAt(middleRight)) {
                    return null;
                }

                middleLeft--;
                middleRight++;
            }

            while(middleRight < s.length()) {
                str.insert(0, s.charAt(middleRight));
                middleRight++;
            }

            return str.toString();
        }

        /***
         * One thing for sure is that we cannot reduce the cost n for generating centers,
         * (in other words, we have to loop through all characters).
         * Is there anyway to simplify the checking to O(1)?
         * To do this, we cannot check characters one by one.
         * Then of course we will need to store some auxiliary information.
         *
         * Our previous idea is:
         *     to find the largest subString such that it starts at 0, end at i, and is a palindrome.
         * Because a palindrome is symmetric, so s == s.reverse(). This is essential to this idea!
         *
         * There is a famous pattern matching algorithm called KMP Algorithm.
         * The center of its idea is storing information which indicates the longest proper prefix which is also suffix.
         * And in our problem, the largest palindrome is the longest prefix in s, which is also suffix of s.reverse()
         * (Note, proper prefix means all the subStrings, but the whole string. E.g., "AB" -> "", "A" (not "AB")
         *
         * Remember, we want to find the largest palindrome, which will occur in both s and s.reverse().
         * So if we combine s & s.reverse(), then we can use the KMP algorithm to find the longest palindrome,
         * which must start at 0, in linear time!.
         * Also, in order to avoid situations like s = "aaa", (s+s.reverse() = "aaaaaa", which is also a palindrome),
         * we can manually insert a special character in the middle of concatenation, ("aaa@aaa").
         *
         * So how does KMP works?
         *
         * Let i be the index of current checking character in the patten, p.
         *     j be the index of the current checking character in the string, s.
         * Then think of the following situations:
         *  0. s = "ABCD", p = " "
         *     In this situation, we just shift character 1 by 1 until we realize no matching.
         *
         *  1. s = "ABCDA", p = "ABD".
         *     After we scan AB, now i = 2, j = 2, and there is a mismatch.
         *     To be linear, we need to somehow:
         *                       j                 j                    j
         *                   A B C D A    ->   A B C D A    or    A B C D A
         *                   A B D        ->       A B D                A B D
         *                       i                 i                    i
         *
         *  2. s = "AAABA", p = "AAD"
         *     After we scan AA, now i = 2, j = 2, and there is a mismatch.
         *     To be linear, we need to somehow:
         *                       j                 j                     j
         *                   A A A B A    ->   A A A B A    or     A A A B A
         *                   A A D        ->     A A D               A A D
         *                       i                 i                     i
         *
         * So there are 2 things we can conclude:
         *  1) j must be strictly increasing. Every loop j stays the same or increase by 1
         *  2) we need somehow control how i moves.
         *
         * For example, pattern = "ababc", s = "abababc", and we find c mismatches with the string.
         * Instead of go all the way back, starts with i=0, j = 2,
         * we can somehow only go back 2 characters, starts with i=2, j=4.
         *                    j                       j
         *            a b a b a b c    ->     a b a b a b c
         *            a b a b c      ->           a b a b c
         *                    i                       i
         *
         * http://jakeboxer.com/blog/2009/12/13/the-knuth-morris-pratt-algorithm-in-my-own-words/
         *
         * To achieve that, we need the core data structure of KMP, a Partial Match Table.
         * It is also called lps, largest proper prefix which is also a proper suffix.
         * This array determines how many characters do we go back in pattern (i.e., how does i change) by examining the pattern.
         * In terms of how to interpret the array, the following explanation is very important:
         *      each cell i, indicates from 0...i(inclusive), what is the lps of the substring (totally ignore the rest of characters)?
         *      for example, "abababa", at i=3, the value is 1. Because 0..i = "aba", and the lps is "a".
         * So, lets see an example:
         *                      a b a b a b c a
         *                      0 0 1 2 3 4 0 1
         *
         * So how to use the table?
         * If you think fast (or :) smart), you will already see what's happening.
         * In words, if the partial matching stops at i, then it is the last character of substring(0...i) mismatch,
         * but the prefix of the substring all matches.
         * So as we have recorded that what is the lps, we can use this information to skip some of the characters.
         * For example: (x = the current checking character)
         *     1)   s = "abcdddddddd", pattern = "abababca"
         *          a b c d d d d d d d d
         *          a b a b a b c a
         *              x
         *          Because we know "ab" matches, so we need to check what's the value of b.
         *          Because it is 0, it means there is no lps in the substring, so we go back to i=0
         *          then we check if a == c.
         *
         *     2)   s = "ababaabddd", pattern = "abababaca"
         *          a b a b a a b d d d
         *          a b a b a b a c a
         *                    x
         *          the value of a is 3. It means that there are 3 characters occurs both in prefix and suffix.
         *          it means that we can skip the first 3 characters, 也就是说，把pattern的头和刚刚的suffix的头对齐:
         *                    x
         *          a b a b a a b d d d
         *              a b a b b a c a
         *                    x
         *          And because we just change i, and we do not need to duplicate checking "aba", so the overall checking time is still linear!
         */
        public String modifiedKMP(String s) {
            StringBuilder temp = new StringBuilder(s);
            StringBuilder str = new StringBuilder(s);
            str.append("@");
            str.append(temp.reverse());

            int n = str.length();
            if (n == 1) return s;

            // construct lps for s
            int[] lps = new int[n];
            for(int i=1; i<n; i++) {
                int last = lps[i - 1];

                while (last > 0 && str.charAt(i) != str.charAt(last)) {
                    last = lps[last-1];
                    last = Math.max(last, 0);
                }

                if (str.charAt(i) == str.charAt(last)) {
                    lps[i] = last + 1;
                }
            }

            return new StringBuilder(s.substring(lps[n-1])).reverse().append(s).toString();
        }


    }

}
