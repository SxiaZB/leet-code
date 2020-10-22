package com.leet;

import java.util.*;

public class Solution36 {

    private List<Set<Character>> rowSets = new ArrayList<>(9);
    private List<Set<Character>> columnSets = new ArrayList<>(9);
    private List<Set<Character>> blockSets = new ArrayList<>(9);

    public boolean isValidSudoku(char[][] board) {
        for (int i = 0; i < board.length; i++) {
            char[] rows = board[i];
            for (int j = 0; j < rows.length; j++) {
                char c = rows[j];
                if (!isValid(c, i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean isValid(char c, int i, int j) {
        if (rowSets.size() <= i) {
            rowSets.add(i, new HashSet<Character>(9));
        }
        if (columnSets.size() <= j) {
            columnSets.add(j, new HashSet<Character>(9));
        }

        int blockIndex = (i / 3) * 3 + j / 3;
        if (blockSets.size() <= blockIndex) {
            blockSets.add(blockIndex, new HashSet<Character>(9));
        }
        if (c == '.') {
            return true;
        }

        Set<Character> rowSet = rowSets.get(i);
        Set<Character> columnSet = columnSets.get(j);
        Set<Character> blockSet = blockSets.get(blockIndex);
        if (rowSet.contains(c) || columnSet.contains(c) || blockSet.contains(c)) {
            return false;
        }
        rowSet.add(c);
        columnSet.add(c);
        blockSet.add(c);
        return true;
    }
}
