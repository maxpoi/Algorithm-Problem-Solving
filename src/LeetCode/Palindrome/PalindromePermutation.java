package LeetCode.Palindrome;

import java.util.HashSet;

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
