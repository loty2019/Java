import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import stdlib.StdIn;
import stdlib.StdOut;

public class Count {
    // Entry point .
    public static void main(String[] args) {
        String[] a = StdIn.readAllStrings();
        StdOut.println(distinct(a));
    }

    // Returns the number of unique strings in a.
    private static int distinct(String[] a) {
        Set<String> set = new HashSet<>();

        return set.size();
    }

}
