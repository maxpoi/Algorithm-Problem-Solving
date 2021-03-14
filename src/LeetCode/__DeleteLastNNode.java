package LeetCode;

import java.util.Stack;

/***
 * Leetcode No 19. Medium Level
 * 滑动窗口
 */
public class __DeleteLastNNode {

    /***
     *
     * Must achieve n
     * 如果只是用常规的做法，比如存一个stack，然后根据n来pop，
     * 那worst case就是 2n
     *
     * 要想achieve n，题目中的"删除倒数第n个"这句话是关键。
     * 而且之前的方法之所以是2n，是因为worst case的时候我们发现n=listNode的数量
     * 运用滑动窗口的思路可以解决这个问题。
     * size=n, 在读新的listnode的时候，如果数量超过了size，说明最左边的一定不会被扔掉
     *
     */

    public ListNode solution(ListNode head, int n) {
        ListNode parent = head;
        ListNode root = head;

        while (n > 0) {
            root = root.next;
            n--;
        }

        // 这个时候要么root=null，head要删掉，return head.next
        if (root == null) {
            return head.next;
        }

        // 要么root.next != null, 还可以继续遍历
        // 而且要删掉的node的parent要更新成目前最左边的
        while (root.next != null) {
            parent = parent.next;
            root = root.next;
        }

        // 要么root.next == null， 那就是要删除parent.next
        parent.next = parent.next.next;
        return head;
    }
}
