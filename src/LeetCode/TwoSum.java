package LeetCode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/***
 * Leetcode No170
 */

public class TwoSum {
    private HashMap<Integer, Integer> container;

    /** Initialize your data structure here. */
    public TwoSum() {
        this.container = new HashMap<>();
    }

    /** Add the number to an internal data structure.. */
    public void add(int number) {
        if (this.container.containsKey(number)) {
            this.container.replace(number, this.container.get(number)+1);
        } else {
            this.container.put(number, 1);
        }
    }

    /** Find if there exists any pair of numbers which sum is equal to the value. */
    public boolean find(int value) {
        for(Map.Entry<Integer, Integer> entry : this.container.entrySet()) {
            int rest = value - entry.getValue();
            if (rest != entry.getValue()) {
                if (this.container.containsKey(rest)) {
                    return true;
                }
            } else {
                if (entry.getValue() > 1) {
                    return true;
                }
            }
        }

        return false;
    }
}
