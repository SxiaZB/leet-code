package com.leet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SolutionPermute {

    public List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        int n = nums.length;
        List<Integer> numList = new ArrayList<>(n);
        //需要先将数组转化为Integer的list
        for (int i = 0; i < n; i++) {
            numList.add(nums[i]);
        }
        //先确定0下标的数
        recursivePermute(res, numList, 0, n - 1);
        return res;
    }

    //now为当前正在排列的下标,end为数组最后一个下标
    public void recursivePermute(List<List<Integer>> res, List<Integer> numList, int now, int end) {
        if (now == end) {
            //当now=end时，说明只剩最后一个数，没得选了，本次排列结束，将当前结果保存下来，添加到res中
            res.add(new ArrayList<>(numList));
        } else {
            //如果不等于，便将未排列的数，依次放在当前排列的下标中
            for (int i = now; i <= end; i++) {
                //选择第i个排在第now下标
                Collections.swap(numList, i, now);
                //递归now+1个
                recursivePermute(res, numList, now + 1, end);
                //为了保证排列不重复，需要还原数组为原来的排序，因为选择第i个排列的情况还有别的
                Collections.swap(numList, i, now);
            }
        }
    }
}
