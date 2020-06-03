package com.leet;

/**
 * @link: https://leetcode-cn.com/problems/implement-trie-prefix-tree/
 */
public class Trie {

    private TrieNote root;

    /**
     * Initialize your data structure here.
     */
    public Trie() {
        root = new TrieNote();
    }

    /**
     * Inserts a word into the trie.
     */
    public void insert(String word) {
        TrieNote note = root;
        int length = word.length();
        for (int i = 0; i < length; i++) {
            note = note.put(word.charAt(i));
        }
        note.setEnd(true);
    }

    /**
     * Returns if the word is in the trie.
     */
    public boolean search(String word) {
        TrieNote note = root;
        int length = word.length();
        for (int i = 0; i < length; i++) {
            note = note.get(word.charAt(i));
            if (note == null) {
                return false;
            }
        }
        return note.isEnd();
    }

    /**
     * Returns if there is any word in the trie that starts with the given prefix.
     */
    public boolean startsWith(String prefix) {
        TrieNote note = root;
        int length = prefix.length();
        for (int i = 0; i < length; i++) {
            note = note.get(prefix.charAt(i));
            if (note == null) {
                return false;
            }
        }
        return note != null;
    }

    class TrieNote {
        private boolean end;
        private TrieNote[] notes;

        private int length = 26;

        public TrieNote() {
            notes = new TrieNote[length];
        }

        public boolean isEnd() {
            return end;
        }

        public void setEnd(boolean end) {
            this.end = end;
        }

        public TrieNote put(char c) {
            int i = c - 'a';
            if (notes[i] == null) {
                TrieNote note = new TrieNote();
                notes[i] = note;
            }
            return notes[i];
        }

        public TrieNote get(char c) {
            int i = c - 'a';
            return notes[i];
        }
    }
}
