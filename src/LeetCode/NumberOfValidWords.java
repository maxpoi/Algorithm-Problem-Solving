package LeetCode;


import java.util.*;
import java.util.stream.Collectors;

/***
 * No. 1178 Hard Level
 */
public class NumberOfValidWords {

    /***
     *
     * 这道题难点不在解题思路，思路很简单，就是哈希表判断
     * 难点主要是用什么数据结构？
     *
     * 至于为什么难点是这个，让我们来分析一下用hashset的时间复杂度
     * O(n*7*m*7)：创建puzzles hashset的时间 + 检查每个word的时间
     * O(m*p)：创建words hashset的时间
     * 总共 O(nm)
     * 而且当mn数量很多的时候，会超时，而且其实很多的word set有可能会是一样的
     *
     */
    public List<Integer> timeoutNaiveSolution(String[] words, String[] puzzles) {
        ArrayList<HashSet<Character>> wordStorage = new ArrayList<>();


        for(int i=0; i<words.length; i++) {
            HashSet<Character> set = new HashSet<>();
            for (int j = 0; j < words[i].length(); j++) {
                set.add(words[i].charAt(j));
            }
            if (set.size() <= 7) {
                wordStorage.add(set);
            }
        }

        List<Integer> ret = new ArrayList<>();
        for(int i=0; i<puzzles.length; i++) {
            HashSet<Character> set = new HashSet<>();
            for(int j=0; j<puzzles[i].length(); j++) {
                set.add(puzzles[i].charAt(j));
            }

            int total = 0;
            for(HashSet<Character> word : wordStorage) {
                if (word.contains(puzzles[i].charAt(0)) && set.containsAll(word)) {
                    total++;
                }
            }

            ret.add(total);
        }


        return  ret;
    }

    /***
     *
     * 之前的问题是不能发现重复的set，那就要换一个数据结构
     * 我自己确实不知道有什么数据结构能够满足hash条件还能被hash本身查重。
     * 看了解析发现可以用二进制int来hash，而且因为本身是int所以被hash
     * 并且因为puzzle固定长度=7，所以预期 for each puzzle compare each word
     * 不如 for each puzzle, 生成所有满足条件的word'，然后看word'在words里出现了几次
     *
     * 那具体思路就是搞一个25位的int，遍历words，遇到一个字母就把那个字母所在的位置变成1
     * 然后遍历puzzles，并且生成所有的排列组合（包含第一个字母）
     * 至于枚举的算法也是学来的：
     *
     *  function get_subset(bitmask)
     *      subset = bitmask
     *      answer = [bitmask]
     *      while subset != 0
     *          subset = (subset - 1) & bitmask
     *          put subset into the answer list
     *      end while
     *      return answer
     *  end function

     *
     */
    public List<Integer> betterSolution(String[] words, String[] puzzles) {
        Map<Integer, Integer> frequency = new HashMap<>();

        for (String word : words) {
            int mask = 0;
            for (int i = 0; i < word.length(); ++i) {
                mask |= (1 << (word.charAt(i) - 'a'));
            }
            if (Integer.bitCount(mask) <= 7) {
                frequency.put(mask, frequency.getOrDefault(mask, 0) + 1);
            }
        }

        List<Integer> ret = new ArrayList<>();
        for(String word : puzzles) {
            int total = 0;

            int mask = 0;
            for (int i = 0; i < word.length(); ++i) {
                mask |= (1 << (word.charAt(i) - 'a'));
            }

            int subset = mask;
            int initial = (1 << (word.charAt(0) - 'a'));
            while(subset > 0) {
                if ((subset & initial) != 0 && frequency.containsKey(subset)) {
                    total += frequency.get(subset);
                }

                subset = (subset-1) & mask;
            }

            ret.add(total);
        }

        return ret;
    }

}