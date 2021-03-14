package LeetCode;

/***
 * Leetcode No 141, 142
 * 快慢指针
 */
public class __LinkedListLoop {

    /***
     * 做题的时候一定要记得，2N,3N仍然是O(n)，
     * 考虑一下能不能靠循环两遍就能解决问题的
     *
     * 这种类型的题一般都是一个思路：快慢指针
     * 核心思路就是让两个指针的距离在进入loop后越来越小
     * 一般是k=2，至于为什么不用更大的数，
     * 是因为多走的路也是要算时间复杂度的，k=2的时候每次只需要多走一步，
     * 而且因为进入loop后两个指针的距离每次都会-1，所以最多是2n
     * 而且就算进入loop前的长度特别长，导致快指针在慢指针进入loop前不得不不断的loop，
     * 最终也是xN，而不是N^2
     *
     * 设链表中环外部分的长度为 a。slow 指针进入环后，又走了 b 的距离与 fast 相遇。此时，fast 指针已经走完了环的 n 圈，
     * 让b+c=环的长度，则它走过的总距离为 a+n(b+c)+b=a+(n+1)b+nc
     *
     */
    public boolean solution141(ListNode head) {
        ListNode slow = head, fast = head;

        // 看head本身就是null，或者只有一个node
        if (fast == null || fast.next == null) return false;

        // 如果至少有两个node，直接比较
        // 这个时候fast已经指向第二个node了，fast.next指向的就是第三个node
        fast = fast.next;
        if (slow == fast) return true;

        while (fast != null) {
            fast = fast.next;

            if (fast == slow) {
                return true;
            }

            slow = slow.next;

            if (fast != null)
                fast = fast.next;
        }

        return false;
    }

    /***
     * 因为快指针走过的距离永远是慢指针走过的距离的两倍，
     * 所以 a+(n+1)b + nc = 2 (a+b)
     * => a = c + (n-1)(b+c)
     * 也就是说，从head走到loop的开始需要c+(n-1)圈。
     * 而我们的慢指针现在就在b，再走c就到了loop开始的地方
     * 所以只要这个时候从头再来一个指针，然后一起++，最后相遇的就是loop开始的地方
     */

    public ListNode solution142(ListNode head) {
        ListNode slow = head, fast = head;

        // 看head本身就是null，或者只有一个node
        if (fast == null || fast.next == null) return null;

        while (fast != null) {
            slow = slow.next;
            if (fast.next != null) {
                fast = fast.next.next;
            } else {
                return null;
            }

            if (fast == slow) {
                ListNode ptr = head;
                while(ptr != slow) {
                    ptr = ptr.next;
                    slow = slow.next;
                }

                return ptr;
            }
        }

        return null;
    }

}
