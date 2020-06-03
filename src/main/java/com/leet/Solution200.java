package com.leet;

import java.util.LinkedList;
import java.util.Queue;

public class Solution200 {
    private void dfs(char[][] grid, int r, int c) {
        int nr = grid.length;
        int nc = grid[0].length;

        if (r < 0 || c < 0 || r >= nr || c >= nc || grid[r][c] == '0') {
            return;
        }

        grid[r][c] = '0';
        dfs(grid, r - 1, c);
        dfs(grid, r + 1, c);
        dfs(grid, r, c - 1);
        dfs(grid, r, c + 1);
    }

    public int numIslands(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int n = grid.length;
        int m = grid[0].length;
        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == '1') {
                    res++;
                    dfs(grid, i, j);
                }
            }
        }

        return res;
    }

    public int numIslands1(char[][] grid) {
        if (grid == null || grid.length == 0) {
            return 0;
        }
        int n = grid.length;
        int m = grid[0].length;
        int res = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == '1') {
                    res++;
                    Queue<int[]> queue = new LinkedList<>();
                    queue.add(new int[]{i, j});
                    grid[i][j] = '0';
                    while (!queue.isEmpty()) {
                        int[] now = queue.remove();
                        if (now[0] != 0 && grid[now[0] - 1][now[1]] == '1') {
                            queue.add(new int[]{now[0] - 1, now[1]});
                            grid[now[0] - 1][now[1]] = '0';
                        }
                        if (now[0] != (n - 1) && grid[now[0] + 1][now[1]] == '1') {
                            queue.add(new int[]{now[0] + 1, now[1]});
                            grid[now[0] + 1][now[1]] = '0';
                        }
                        if (now[1] != 0 && grid[now[0]][now[1] - 1] == '1') {
                            queue.add(new int[]{now[0], now[1] - 1});
                            grid[now[0]][now[1] - 1] = '0';
                        }
                        if (now[1] != (m - 1) && grid[now[0]][now[1] + 1] == '1') {
                            queue.add(new int[]{now[0], now[1] + 1});
                            grid[now[0]][now[1] + 1] = '0';
                        }
                    }
                }
            }
        }

        return res;
    }

}
