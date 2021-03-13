package LeetCode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class CombinationPhone {

    Map<Character, String> phoneMap = new HashMap<Character, String>() {{
        put('2', "abc");
        put('3', "def");
        put('4', "ghi");
        put('5', "jkl");
        put('6', "mno");
        put('7', "pqrs");
        put('8', "tuv");
        put('9', "wxyz");
    }};

    List<String> ret = new ArrayList<>();

    public List<String> solution(String digits) {
        if (digits.length() == 0) {
            return ret;
        }

        backtrack(digits, 0, new StringBuffer());
        return ret;
    }

    public void backtrack(String digits, int index, StringBuffer combination) {
        if (index == digits.length()) {
            ret.add(combination.toString());
        } else {
            char digit = digits.charAt(index);
            String letters = phoneMap.get(digit);
            int lettersCount = letters.length();
            for (int i = 0; i < lettersCount; i++) {
                combination.append(letters.charAt(i));
                backtrack(digits, index + 1, combination);
                combination.deleteCharAt(index);
            }
        }
    }
}
