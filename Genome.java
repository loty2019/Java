import stdlib.StdOut;

import java.util.Comparator;
import java.util.Iterator;

// This comparable, iterable data type represents a genome sequence (a string of letters A, T, G,
// or C denoting nucleotides).
public class Genome implements Comparable<Genome>, Iterable<Character> {
    private String s; // the genome sequence

    // Constructs a Genome object from the genome sequence s.
    public Genome(String s) {
        this.s = s;
    }

    // Returns (G + C) / (A + T + G + C) * 100 for this genome. For example, the GC content of
    // the genome sequence "ACTGCG" is 67%.
    public double gcContent() {
        int A = s.length() - s.replaceAll("A", "").length();
        int T = s.length() - s.replaceAll("T", "").length();
        int G = s.length() - s.replaceAll("G", "").length();
        int C = s.length() - s.replaceAll("C", "").length();

        return ((1.0 * G + C) / (A + T + G + C)) * 100;
    }

    // Returns a string representation of this genome.
    public String toString() {
        return s.length() + ":" + s;
    }

    // Returns a comparison of this and other genomes based on their lengths.
    public int compareTo(Genome other) {
        return Integer.compare(this.s.length(), other.s.length());
    }

    // Returns a comparator for comparing genomes based on their GC content.
    public static Comparator<Genome> gcOrder() {
        return new GCOrder();
    }

    // Returns an iterator for iterating over this genome in reverse order.
    public Iterator<Character> iterator() {
        return new ReverseIterator();
    }

    // A comparator for comparing genomes based on their GC content.
    private static class GCOrder implements Comparator<Genome> {
        // Returns a comparison of genomes g1 and g2 based on their GC content.
        public int compare(Genome g1, Genome g2) {
            return Double.compare(g1.gcContent(), g2.gcContent());
        }
    }

    // An iterator for iterating over a genome in reverse order.
    private class ReverseIterator implements Iterator {
        private int i; // index of current letter

        // Constructs an interator.
        public ReverseIterator() {
            i = s.length() -1;
        }

        // Returns true if there are more letters in the genome, and false otherwise.
        public boolean hasNext() {
            return i >= 0;
        }

        // Returns the next letter in the genome.
        public Character next() {
            return s.charAt(i--);
        }
    }

    // Unit tests the data type .
    public static void main(String[] args) {
        Genome g1 = new Genome(args[0]);
        Genome g2 = new Genome(args[1]);
        StdOut.println("g1                       = " + g1);
        StdOut.println("g2                       = " + g2);
        StdOut.println("g1.gcContent()           = " + g1.gcContent());
        StdOut.println("g2.gcContent()           = " + g2.gcContent());
        StdOut.println("g1.compareTo(g2)         = " + g1.compareTo(g2));
        StdOut.println("GCOrder::compare(g1, g2) = " + Genome.gcOrder().compare(g1, g2));
        StdOut.print("reverse(g1)              = ");
        for (char c : g1) {
            StdOut.print(c);
        }
        StdOut.println();
    }
}