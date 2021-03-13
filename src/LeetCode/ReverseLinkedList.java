package LeetCode;

/***
 * Leetcode no206
 */
public class ReverseLinkedList {


    /***
     * A -> B -> C
     * C -> B -> A
     *
     * 思路：如果用Stack的话，Stack会长这样：A -> B -> C，
     *      然后会pop C， B， A...
     *      但是，因为是LinkList，所以可以用两个指针来省去Stack的储存空间:
     *          1. 读一个element就按照这个element创一个ListNode
     *          2. 按照逻辑，新建的这个ListNode是下一个ListNode的child
     *          3. 所以两个指针分别是
     *              a) newHead = new ListNode(currHead.val)
     *              b) newHead.next = preHead
     *              c) preHead = newHead
     */

    public ListNode solution(ListNode root) {
        if (root == null || root.next == null) return root;

        ListNode newHead = null, lastHead = null;
        while(root != null) {
            newHead = new ListNode(root.val);
            newHead.next = lastHead;
            lastHead = newHead;
            root = root.next;
        }

        return newHead;
    }
}
