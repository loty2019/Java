import dsa.LinkedStack;

import stdlib.StdIn;
import stdlib.StdOut;

public class Sort {
    // Entry point.
    public static void main(String[] args) {
        LinkedDeque<String> d = new LinkedDeque<String>();

        while (!StdIn.isEmpty()) {
            String w = StdIn.readString();
            if (d.isEmpty() || less(w, d.peekFirst())) { // if it is less than the first word in d
                d.addFirst(w); // add w to the front of d
            } else if (less(d.peekLast(), w)) { // if it is greater than the last word in d
                d.addLast(w); // add w to the back of d
            } else {
                LinkedStack<String> s = new LinkedStack<String>();

                while (less(d.peekFirst(), w)) {
                    // remove words less than w from the front of d
                    // and store them in s
                    s.push(d.removeFirst());
                }
                d.addFirst(w); //  add w to the front of d
                while (!s.isEmpty()) {
                    d.addFirst(s.pop()); // add words from s to the front of d
                }
            }
        }
        // print out d
        for (String str : d) {
            StdOut.println(str);
        }
    }

    // Returns true if v is less than w according to their lexicographic order, and false otherwise.
    private static boolean less(String v, String w) {
        return v.compareTo(w) < 0;
    }
}
