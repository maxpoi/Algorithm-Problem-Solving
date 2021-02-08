package LeetCode.Palindrome;

/***
 * Leetcode No647 Medium Level
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
