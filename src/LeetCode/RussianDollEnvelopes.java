package LeetCode;

import java.util.*;

/***
 * Leetcode No 354
 */
public class RussianDollEnvelopes {

    /***
     *
     * 思路主要是必须先abstract problem
     * -> width是可以被忽略的（因为sort过了）
     * -> 那问题就变成："找到最长的子序列，并满足height的单调递增"
     * 这样问题不仅简单了一些，思路也会更清楚
     *
     * 仔细一想，不难发现是一个dp问题
     * [[2,100],[3,200],[4,300],[5,250],[5,400],[5,500],[6,360],[6,370],[7,380]]
     * -> 5 (跳过4， 300）
     * -> dp[i] = max(dp[j]+1) for all j < i && height_j < height_i
     * 不过因为我们是loop套loop，所以O(n^2)
     *
     * 既然是查找，又是O(n^2)，让我们想想能不能优化到 nlogn，很明显，用当前的思路并不能。
     * 我们当前思路遇到的问题是，dp[i]可能可以和任何dp[j]组合来构成更长的序列。
     * 比如[100, 200, 300] 和 [100, 200, 250]都是可行的，并且后面的更好
     *
     * 换个思路，有没有办法让我们记录在长度是 i 的时候，最小的height呢？
     * 这个思路是可行的是因为我们在从左往右遍历，如果当前的 index可以组成长度为3的子序列，并且当前的height比之前的height要小，
     * 那必然可以组成更长的子序列
     * 并且因为在这个思路下，我们是要以当前的height为基准，在以往的子序列里找比他更小的，并且以往对子序列一定是单调递增的，所以可以二分查找 ，
     * 这样就是nlogn了。
     *
     */
    public int solution(int[][] envelopes) {
        Arrays.sort(envelopes, (o1, o2) -> {
            if (o1[0] == o2[0]) {
                return o1[1] - o2[1];
            } else {
                return o1[0] - o2[0];
            }
        });

        int[] dp = new int[envelopes.length];
        int ret = 0;
        for(int i=1; i<envelopes.length; i++) {
            for(int j=0; j<i; j++) {
                if (envelopes[i][1] > envelopes[j][1] && envelopes[i][0] > envelopes[j][0]) {
                    dp[i] = Math.max(dp[j]+1, dp[i]);
                }
            }
            ret = Math.max(ret, dp[i]);
        }

        return ret+1;
    }

    public int betterSolution(int[][] envelopes) {
        if (envelopes.length == 0) return 0;

        Arrays.sort(envelopes, (o1, o2) -> {
            if (o1[0] == o2[0]) {
                // 注意这里需要是倒序
                // 为了防止 [2, 2], [2, 3]，然后与其跳过后者，
                // 反而size++了的这种情况
                // 如果是从大到小，就一定是覆盖
                return o2[1] - o1[1];
            } else {
                return o1[0] - o2[0];
            }
        });

        // 不需要width，因为当width是一样时，height是单调递增的
        // 而且我们记录的是"当长度为i时，最小的height"，
        // 所以width是完全不需要考虑的， 比如完全可以在 i=0的时候，对应的是最长的width，但是有着最短的height
        List<Integer> heights = new ArrayList<>() {{
            add(envelopes[0][1]);
        }};

        for(int i=1; i<envelopes.length; i++) {
            int num = envelopes[i][1];
            if (num > heights.get(heights.size()-1)) {
                heights.add(num);
            } else {
                int index = binarySearch(heights, num);
                heights.set(index, num);
            }
        }


        return heights.size();
}

    public int binarySearch(List<Integer> list, int target) {
        int l = 0, r = list.size()-1;
        while (l < r) {
            int middle = (r-l)/2 + l;

            // 必须是这样
            if (list.get(middle) < target) {
                l = middle+1;
            } else {
                r = middle;
            }
        }

        return l;
    }
}
