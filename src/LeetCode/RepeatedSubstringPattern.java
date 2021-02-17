package LeetCode;

/***
 * Leetcode No 459 Easy Level (although I think it should be medium)
 */
public class RepeatedSubstringPattern {

    /***
     * 一个大佬的思路：
     * s = n个x的pattern重复 -> s = n*x
     * 那如果 s‘ = s+s -> s’ = 2n*x
     * 如果这个时候破坏掉第一个和最后一个x -> s‘ = [1:len-1]
     * s则仍然会出现在s'里。因为 s’ 仍然是多个x的组合，并且因为我们只破坏了第一个和最后一个x，只要s的长度足够，就一定还会出现
     * 比如 ”abab" -> "abababab" -> "bababa" contains "abab".
     *     "aa" -> "aaaa" -> "aa" contains "aa"
     *     "abcd" -> "abcdabcd" -> "bcdabc" does not contain "abcd"
     *
     */
    public boolean solution(String s) {
        String str = s + s;
        return str.substring(1, str.length() - 1).contains(s);
    }

    /***
     * 看解题说KMP也可以用来解这道题。下面就想想怎么用到KMP，以及为什么可以
     *
     * lps的数组存了最长的prefix=suffix。
     * 显而易见，一个重复的s prefix必等于suffix。
     * “abcabcabcabc" -> "abcabcabc"
     *
     * 不过有一个问题是，只是首尾相同，但是中间不同咋办？ 比如 ”abceeeabc"。
     * 这个时候就要上面的数学思路了。
     * 假设s=abc -> abcabc, prefix & suffix 平分了 s
     *          -> abcabcabc, prefix & suffix = “abcabc",
     *          -> abcabcabcabc, prefix & suffix = "abcabcabc"
     *          -> abcabcabcabcabc, -> "abcabcabcabc
     *          -> prefix/suffix = s - abc
     *          -> s - prefix/suffix = x.
     * 不过因为我们不知道x，所以得想办法换一个办法比较。
     * -> s - prefix/suffix = x
     * -> s % x = 0
     * （如果首尾相同，但是中间不同很容易判断，因为此时 s % x 必不等于0）
     *
     * 那如果prefix/suffix包括了大部分的s怎么办，比如 "aababba", "abcabcabc"
     * 还是一样，如果符合条件就能%，不符合就必不能     -> 7 % 3  ,  9 % 3
     *
     */
    public boolean KMPsolution(String s) {
        int n = s.length();

        int[] lps = new int[n];
        for(int i=1; i<n; i++) {
            int last = lps[i-1];

            /***
             * 这里是为了更快速的求出lps[i]的值
             * 如果之前lps[i-1] > 0，就说明之前的substring有prefix=suffix的情况
             * 那如果我们这个时候 charAt(i) == s.charAt(last), 就可以直接++了 （直接跳过while）
             * 但如果不等的话，别急着直接让lps=0，仔细想想会遇到的情况
             *   a a a c a a a a
             *   0 1 2 0 1 2 3 x (should be 3)
             *   -> last = 3, a != c.
             * 不难发现，因为现在指向的char不等于last指向的char，也就是说之前的prefix/suffix不能增加
             * 但因为在prefix/suffix之前的prefix/suffix仍然有可能包含char，所以我们要不断地往前推，直到确认所有的prefix/suffix都不含char才行。
             * 只要有任何一个prefix/suffix包含了char，就停止loop，然后++
             */
            while(last > 0 && s.charAt(i) != s.charAt(last)) {
                last = lps[last-1];
                last = Math.max(last, 0);
            }

            if (s.charAt(i) == s.charAt(last)) {
                lps[i] = last+1;
            }
        }

        return lps[n-1] != 0 &&
                n - lps[n-1] == lps[(n-lps[n-1])*2-1];
    }
}
