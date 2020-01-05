package com.company;

public class GeneralList {

    private final Node HEAD = new Node();

    public void add(String word) {
        add(HEAD, word, 0);
    }

    public void remove(String word) {
        remove(HEAD, word, 0);
    }

    public boolean exist(String word) {
        return exist(HEAD, word, 0);
    }

    public void printAll() {
        printAll(HEAD, "");
    }

    private void add(Node head, String word, int index) {
        if (index == word.length()) {
            head.endOfWord = index != 0;
            return;
        }
        if (head.right == null) {
            (head.right = new Node()).value = word.charAt(index);
            add(head.right, word, index + 1);
        } else if (head.right.value == word.charAt(index)) {
            add(head.right, word, index + 1);
        } else {
            add(head.down != null ? head.down : (head.down = new Node()), word, index);
        }
    }

    private void remove(Node head, String word, int index) {
        if (index == word.length()) {
            head.endOfWord = false;
            return;
        }
        if (head.right != null && head.right.value == word.charAt(index)) {
            remove(head.right, word, index + 1);
        } else if (head.down != null) {
            remove(head.down, word, index);
        }
    }

    private boolean exist(Node head, String word, int index) {
        if (index == word.length()) {
            return head.endOfWord;
        }
        if (head.right != null && head.right.value == word.charAt(index)) {
            return exist(head.right, word, index + 1);
        } else if (head.down != null) {
            return exist(head.down, word, index);
        }
        return false;
    }

    private void printAll(Node head, String pre) {
        Node row = head;
        while (row != null) {
            Node col = row.right;
            String line = "";
            while (col != null) {
                line += col.value;
                System.out.print((col.endOfWord ? pre + line + "\n" : ""));
                if (col.down != null) {
                    printAll(col, pre + line);
                    break;
                }
                col = col.right;
            }
            row = row.down;
        }
    }

    private class Node {

        public char value;

        public boolean endOfWord;

        public Node right;

        public Node down;

    }

}
