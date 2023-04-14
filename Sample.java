import stdlib.StdOut;

public class Sample {
    // Entry point.
    public static void main(String[] args) {
        // accept lo, hi, k, mode as command-line arguments
        int lo = Integer.parseInt(args[0]);
        int hi = Integer.parseInt(args[1]);
        int k = Integer.parseInt(args[2]);
        String mode = args[3];

        ResizingArrayRandomQueue q = new ResizingArrayRandomQueue();
        for (int i = lo; i <= hi; i++) {
            q.enqueue(i); // fill the queue
        }
        // check mode
        if (mode.equals("+")) {
            for (int i = 0; i < k; i++) {
                StdOut.println(q.sample()); // sample and write k
            }
        } else if (mode.equals("-")) {
            for (int i = 0; i < k; i++) {
                StdOut.println(q.dequeue()); // dequeue and write k
            }
        } else {
            // corner case
            throw new IllegalArgumentException("Illegal mode");
        }
    }
}
