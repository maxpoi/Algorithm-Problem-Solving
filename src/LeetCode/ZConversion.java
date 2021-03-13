package LeetCode;

/***
 * Leetcode No 6 Medium Level
 */
public class ZConversion {

    /***
     *
     * 这是我自己的纯找规律解法。。
     * 一点也不美丽。。
     *
     */
    public String solution(String s, int numRows) {
        if (s.length() <= numRows || numRows == 1) return s;

        StringBuilder ret = new StringBuilder();

        int index = 0;
        while(index < s.length()) {
            ret.append(s.charAt(index));
            index += numRows + numRows - 2;
        }

        for(int i=1; i<numRows-1; i++) {
            index = i;
            while(index < s.length()) {
                ret.append(s.charAt(index));

                if (index+numRows*2-2-i-i < s.length()) {
                    ret.append(s.charAt(index+numRows*2-2-i-i));
                }

                index += numRows + numRows - 2;
            }
        }

        index = numRows-1;
        while(index < s.length()) {
            ret.append(s.charAt(index));
            index += numRows + numRows - 2;
        }

        return ret.toString();
    }
}
