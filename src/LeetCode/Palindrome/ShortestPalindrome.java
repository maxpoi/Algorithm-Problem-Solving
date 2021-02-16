package LeetCode.Palindrome;

import java.util.LinkedList;
import java.util.Queue;

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
