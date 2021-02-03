package InterviewQuestions;

import java.util.LinkedList;
import java.util.Queue;

/***
 * 这是微软苏州的一个面试题，
 * 给定一个类Stream，其中有个函数叫Add(int value, long timeStamp)，会不断地被别的程序调用
 * 还有一个函数叫GetAverage()，调用它的时候返回过去5分钟内的数据平均值
 * 假设存在已知函数GetNow()，调用时会返回当前时间，
 * 请尝试实现Add 和 GetAverage
 */

public class DataStreamAverage {

    public class Data {
        int val;
        long time;
        public Data(int val, long time) {
            this.val = val;
            this.time = time;
        }
    }

    /***
     * 面试官指出的思路是：
     *  1. 五分钟之外的数据可以丢掉，但是五分钟之内的数据不能丢。因为可能会连续两次叫了getAverage
     *  2. 用FIFO的特性，不断地把“过期”的数据扔掉
     *  3. 辅助参数sum和count可以用来更快速的己算average（详情见代码）
     */
    private final long threshold = 300000;
    private Queue<Data> queue = new LinkedList<>();
    int sum = 0, count = 0;

    void Add(int val, long timeStamp) {
        count++;
        sum += val;
        queue.add(new Data(val, timeStamp));
    }

    int getAverage() {
        removeData(getNow());
        return count == 0 ? 0 : sum / count;
    }

    void removeData(long currTime) {
        while(!queue.isEmpty() && (currTime - queue.peek().time) > threshold) {
            Data temp = queue.remove();
            sum -= temp.val;
            count--;
        }
    }

    long getNow() { return 0; }
}
