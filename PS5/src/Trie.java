//import jdk.internal.joptsimple.internal.Strings;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Trie {

    // Wildcards
    final char WILDCARD = '.';
    final char STAR = '*';
    final char QUESTION = '?';
    final char PLUS = '+';
    
    // TODO: Declare any instance variables you need here
    final TrieNode root;

    private class TrieNode {
        // TODO: Create your TrieNode class here.

        // TrieNode data
        TrieNode parent;
        char data;
        TrieNode[] children;
        boolean isEnd;

        public TrieNode() {
            this.children = new TrieNode[62];
            this.isEnd = false;
        }

        public TrieNode(char data) {
            this.data = data;
            this.children = new TrieNode[62];
            this.isEnd = false;
        }

        public TrieNode getChild(int pos) {
            return this.children[pos];
        }

        public void setParent(TrieNode tn) {
            this.parent = tn;
        }

        public void insert(TrieNode tn, int pos) {
            this.children[pos] = tn;
            this.children[pos].setParent(this);
        }

        public void setEnd() {
            this.isEnd = true;
        }

        public boolean isRoot() {
            return this == root;
        }

    }

    public Trie() {
        // TODO: Initialise a trie class here.
        this.root = new TrieNode();
    }
    public int getPos(char c) {
        int ascii = (int) c;
        if (ascii > 47 && ascii < 58) {
            return ascii - 48;
        } else if (ascii > 96 && ascii < 123) {
            return ascii - 61;
        } else if (ascii > 64 && ascii < 91) {
            return ascii - 55;
        } else {
            return -1;
        }
    }

    // insert sting s into trie
    void insert(String s) {
        // TODO
        TrieNode curr = this.root;
        // traverse through the trie and adding nodes if the nodes do not exist yet
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int pos = getPos(c);
            if (curr.getChild(pos) == null) {
                TrieNode tn = new TrieNode(c);
                curr.insert(tn, pos);
            }
            curr = curr.getChild(pos);
        }
        // set last node as end of string
        curr.setEnd();
    }

    // check whether string s exists inside the Trie or not
    boolean contains(String s) {
        // TODO
        TrieNode curr = this.root;
        // traverse through the trie
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int pos = getPos(c);
            curr = curr.getChild(pos);
            // if node does not exist, trie does not contain string
            if (curr == null) {
                return false;
            }
        }
        // if we reach the end of the string, check if node is end node
        return curr.isEnd;
    }

    // Search for string with prefix matching the specified pattern sorted by lexicographical order.
    // Return results in the specified ArrayList.
    // Only return at most the first limit results.
    void prefixSearch(String s, ArrayList<String> results, int limit) {
        // TODO
        prefixSearchHelper(s, 0, this.root, results, "", limit);
    }

    void prefixSearchHelper(String s, int pos, TrieNode curr, ArrayList<String> results, String result, int limit) {
        // if we reach the end of the string
        if (pos == s.length()) {
            // if limit is reached, add string to results arraylist if we are at a node that isEnd is true
            if (results.size() < limit && curr.isEnd) {
                results.add(result);
            }
            // we traverse into every node after reaching the end
            for (TrieNode child: curr.children) {
                if (child != null) {
                    prefixSearchHelper(s, pos, child, results, result + child.data, limit);
                }

                if (results.size() == limit) {
                    break;
                }
            }
        } else {
            char c = s.charAt(pos);
            // if we are at a WILDCARD, we traverse into every node
            if (c == WILDCARD) {
                for (TrieNode child: curr.children){
                    if (child != null) {
                        prefixSearchHelper(s, pos + 1, child, results, result + child.data, limit);
                    }
                }
            } else {
                // else just search normally
                TrieNode child = curr.getChild(getPos(c));
                if (child != null) {
                    prefixSearchHelper(s, pos + 1, child, results, result + child.data, limit);
                }
            }
        }
    }

    // Search for string matching the specified pattern.
    // Return results in the specified ArrayList.
    // Only return at most limit results.
    void patternSearch(String s, ArrayList<String> results, int limit) {
        // TODO
        findPatternString(s, 0, this.root, results, "", limit);
    }

    public boolean isPrevModifier(char c) {
        return c == STAR || c == PLUS || c == QUESTION;
    }

    public void findPatternString(String s, int pos, TrieNode curr, ArrayList<String> results, String result, int limit) {
        // if we reached the end of string, add string into arraylist if limit not reached
        if (results.size() < limit) {
            if (pos == s.length()) {
                if (curr.isEnd) {
                    results.add(result);
                }
            } else {
                char c = s.charAt(pos);
                // if next character is not a modifier
                if (pos == s.length()-1 || !isPrevModifier(s.charAt(pos + 1))) {
                    // check if c is a wildcard
                    if (c == WILDCARD) {
                        // we go into every possible node
                        for (TrieNode child: curr.children) {
                            if (child != null) {
                                findPatternString(s, pos + 1, child, results, result + child.data, limit);
                            }
                        }
                    } else {
                        // we go into the node representing the char c
                        TrieNode child = curr.getChild(getPos(c));
                        if (child != null) {
                            findPatternString(s, pos + 1, child, results, result + child.data, limit);
                        }
                    }
                } else {
                    char mod = s.charAt(pos + 1);
                }
            }
        }
    }

    // Simplifies function call by initializing an empty array to store the results.
    // PLEASE DO NOT CHANGE the implementation for this function as it will be used
    // to run the test cases.
    String[] prefixSearch(String s, int limit) {
        ArrayList<String> results = new ArrayList<String>();
        prefixSearch(s, results, limit);
        return results.toArray(new String[0]);
    }

    // Simplifies function call by initializing an empty array to store the results.
    // PLEASE DO NOT CHANGE the implementation for this function as it will be used
    // to run the test cases.
    String[] patternSearch(String s, int limit) {
        ArrayList<String> results = new ArrayList<String>();
        patternSearch(s, results, limit);
        return results.toArray(new String[0]);
    }

    public static void main(String[] args) {
        Trie t = new Trie();
        t.insert("peter");
        t.insert("piper");
        t.insert("picked");
        t.insert("peck");
        t.insert("of");
        t.insert("pickled");
        t.insert("peppers");
        t.insert("pepppito");
        t.insert("pepi");
        t.insert("pik");
        t.insert("abbde");
        t.insert("abcdef");
        t.insert("a");
        t.insert("aa");
        t.insert("aaa");
        t.insert("aaaa");
        t.insert("aaaaa");
        t.insert("aaaaaab");
        t.insert("their");
        t.insert("tr");
        t.insert("tabcdefghr");
        t.insert("tabcd");
        t.insert("");
        t.insert("h");
        t.insert("ho");
        t.insert("hoo");
        t.insert("hooo");
        t.insert("hoooo");
        t.insert("hooooo");
       String[] result1 = t.prefixSearch("pe", 10);
//        String[] result2 = t.patternSearch("pe.*", 500);
//        String[] result2 = t.patternSearch("po*p", 500);
//        String[] result2 = t.patternSearch(".*", 500);
        System.out.println(Arrays.toString(result1));
        // result1 should be:
        // ["peck", "pepi", "peppers", "pepppito", "peter"]
        // result2 should contain the same elements with result1 but may be ordered arbitrarily


    }
}
