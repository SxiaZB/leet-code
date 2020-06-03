package com.leet;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @link: https://www.lintcode.com/problem/number-of-islands-ii/description
 */
public class Solution434 {
    private int n, m;

    public List<Integer> numIslands2(int n, int m, Point[] operators) {
        List<Integer> res = new ArrayList<>();
        if (operators == null || operators.length == 0) {
            return res;
        }
        int[][] grid = new int[n][m];

        this.n = n;
        this.m = m;
        for (int i = 0; i < operators.length; i++) {
            Point operator = operators[i];
            if (operator.x < 0 || operator.x >= n || operator.y < 0 || operator.y >= m) {
                //无效点击
                res.add(i == 0 ? 0 : res.get(i - 1));
                continue;
            }
            grid[operator.x][operator.y] = 1;
            res.add(numIslands(grid));
        }
        return res;
    }

    public int numIslands(int[][] grid) {
        int res = 0;
        boolean[][] click = new boolean[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 1 && !click[i][j]) {
                    res++;
                    dfs(grid, click, i, j);
                }
            }
        }

        return res;
    }

    private void dfs(int[][] grid, boolean[][] click, int r, int c) {
        if (r < 0 || c < 0 || r >= n || c >= m || click[r][c] || grid[r][c] == 0) {
            return;
        }

        click[r][c] = true;
        dfs(grid, click, r - 1, c);
        dfs(grid, click, r + 1, c);
        dfs(grid, click, r, c - 1);
        dfs(grid, click, r, c + 1);
    }
}
