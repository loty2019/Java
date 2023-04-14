import java.util.Arrays;

import stdlib.In;
import stdlib.StdIn;
import stdlib.StdOut;

public class Autocomplete {
    Term[] terms; // array of terms

    // Constructs an autocomplete data structure from an array of terms.
    public Autocomplete(Term[] terms) {
        if (terms == null) {
            throw new NullPointerException("terms is null");
        }
        this.terms = Arrays.copyOf(terms, terms.length); // create a defensive copy
        Arrays.sort(this.terms); // sort in lexicographic order
    }

    // Returns all terms that start with prefix, in descending order of their weights.
    public Term[] allMatches(String prefix) {
        if (prefix == null) {
            throw new NullPointerException("prefix is null");
        }
        // search
        int i = BinarySearchDeluxe.firstIndexOf(
                terms, new Term(prefix, 0), Term.byPrefixOrder(prefix.length()));
        if (i == -1) { // handles empty arrays
            return new Term[0];
        }
        int n = numberOfMatches(prefix);
        Term[] matchTerms = Arrays.copyOfRange(terms, i, i + n); // create a copy
        Arrays.sort(matchTerms, Term.byReverseWeightOrder()); // sort by reverse weight order
        return matchTerms;
    }

    // Returns the number of terms that start with prefix.
    public int numberOfMatches(String prefix) {
        if (prefix == null) {
            throw new NullPointerException("prefix is null");
        }
        // search
        int i = BinarySearchDeluxe.firstIndexOf(
                terms, new Term(prefix, 0), Term.byPrefixOrder(prefix.length()));
        if (i == -1) { // handles empty array
            return 0;
        }
        int j = BinarySearchDeluxe.lastIndexOf(
                terms, new Term(prefix, 0), Term.byPrefixOrder(prefix.length()));
        return 1 + j - i; // tot number of terms starting with prefix
    }

    // Unit tests the data type. 
    public static void main(String[] args) {
        String filename = args[0];
        int k = Integer.parseInt(args[1]);
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong();
            in.readChar();
            String query = in.readLine();
            terms[i] = new Term(query.trim(), weight);
        }
        Autocomplete autocomplete = new Autocomplete(terms);
        StdOut.print("Enter a prefix (or ctrl-d to quit): ");
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            String msg = " matches for \"" + prefix + "\", in descending order by weight:";
            if (results.length == 0) {
                msg = "No matches";
            } else if (results.length > k) {
                msg = "First " + k + msg;
            } else {
                msg = "All" + msg;
            }
            StdOut.printf("%s\n", msg);
            for (int i = 0; i < Math.min(k, results.length); i++) {
                StdOut.println("  " + results[i]);
            }
            StdOut.print("Enter a prefix (or ctrl-d to quit): ");
        }
    }
}
