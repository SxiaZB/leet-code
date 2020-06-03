package com.leet;

import java.util.LinkedList;
import java.util.Queue;

public class Solution529 {
    private final char e = 'E';
    private final char m = 'M';
    private final char x = 'X';
    private final char b = 'B';
    private Queue<int[]> queue = new LinkedList<>();

    int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1};
    int[] dy = {0, 1, 1, 1, 0, -1, -1, -1};

    public char[][] updateBoard(char[][] board, int[] click) {
        queue.offer(click);
        while (!queue.isEmpty()) {
            click = queue.poll();
            int i = click[0];
            int j = click[1];
            char now = board[i][j];
            if (now == m) {
                board[i][j] = x;
            } else if (now == e) {
                int count = 0;
                Queue<int[]> q = new LinkedList<>();
                for (int d = 0; d < 8; d++) {
                    int newX = i + dx[d];
                    int newY = j + dy[d];
                    if (newX < 0 || newX >= board.length || newY < 0 || newY >= board[0].length) {
                        continue;
                    }
                    count = getCount(board[newX][newY], newX, newY, count, q);
                }
                if (count == 0) {
                    board[i][j] = b;
                    while (!q.isEmpty()) {
                        queue.offer(q.poll());
                    }
                } else {
                    board[i][j] = (char) (count + 48);
                }
            }
        }
        return board;
    }

    private void recursive(char[][] board) {
        int[] click = queue.poll();
        if (click == null) {
            return;
        }
        int i = click[0];
        int j = click[1];
        char now = board[i][j];
        if (now == m) {
            board[i][j] = x;
        } else if (now == e) {
            int count = 0;
            Queue<int[]> q = new LinkedList<>();
            for (int d = 0; d < 8; d++) {
                int newX = i + dx[d];
                int newY = j + dy[d];
                if (newX < 0 || newX >= board.length || newY < 0 || newY >= board[0].length) {
                    continue;
                }
                count = getCount(board[newX][newY], newX, newY, count, q);
            }
            if (count == 0) {
                board[i][j] = b;
                while (!q.isEmpty()) {
                    queue.offer(q.poll());
                }
            } else {
                board[i][j] = (char) (count + 48);
            }
        }
        recursive(board);
    }

    private int getCount(char c, int i, int j, int count, Queue<int[]> q) {
        if (c == m) {
            count++;
        } else if (c == e) {
            q.offer(new int[]{i, j});
        }
        return count;
    }
//        public int getCount(char[][] board, int x, int y) {
//            int r = board.length;
//            int c = board[0].length;
//            int count = 0;
//            for (int i = 0; i < 8; i++) {
//                int newX = x + dx[i];
//                int newY = y + dy[i];
//                if (newX < 0 || newX >= r || newY < 0 || newY >= c) {
//                    continue;
//                }
//                if (board[newX][newY] == 'M') {
//                    count++;
//                }
//            }
//            return count;
//        }

}
