package com.leet;


public class WordDictionary {
    private final char m = '.';
    private TrieNote root;

    /**
     * Initialize your data structure here.
     */
    public WordDictionary() {
        root = new TrieNote();
    }

    /**
     * Adds a word into the data structure.
     */
    public void addWord(String word) {
        TrieNote note = root;
        int length = word.length();
        for (int i = 0; i < length; i++) {
            note = note.put(word.charAt(i));
        }
        note.setEnd(true);
    }

    /**
     * Returns if the word is in the data structure. A word could contain the dot character '.' to represent any one letter.
     */
    public boolean search(String word) {
        return recursive(root, word, 0);
    }

    private boolean recursive(TrieNote note, String word, int now) {
        if (now >= word.length()) {
            return note.isEnd();
        }
        char nc = word.charAt(now);
        if (nc == m) {
            TrieNote[] notes = note.getNotes();
            for (TrieNote trieNote : notes) {
                if (trieNote != null && recursive(trieNote, word, now + 1)) {
                    return true;
                }
            }
            return false;
        } else {
            TrieNote trieNote = note.get(nc);
            return trieNote != null && recursive(trieNote, word, now + 1);
        }
    }

    public class TrieNote {
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

        public TrieNote[] getNotes() {
            return notes;
        }
    }
}
