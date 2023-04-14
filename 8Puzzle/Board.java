import dsa.Inversions;
import stdlib.In;
import stdlib.StdOut;
import java.util.List;
import java.util.Arrays;
import java.util.LinkedList;

// A data type to represent a board in the 8-puzzle game or its generalizations.
public class Board {
    int[][] tiles; // tiles in the board
    int n; // board size
    int hamming; // Hamming distance
    int manhattan; // Manhattan distance
    int blankPos; // position of blank tile in row major order

    // Constructs a board from an n x n array; tiles[i][j] is the tile at row i and column j, with 0
    // denoting the blank tile.
    public Board(int[][] tiles) {
        this.tiles = tiles;
        this.n = tiles.length;
        this.hamming = hamming();
        this.manhattan = manhattan();
        int index = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    this.blankPos = index + 1;
                    break;
                }
                index++;
            }
        }
    }

    // Returns the size of this board.
    public int size() {
        return n;
    }

    // Returns the tile at row i and column j of this board.
    public int tileAt(int i, int j) {
        return tiles[i][j];
    }

    // Returns Hamming distance between this board and the goal board.
    public int hamming() {
        int wrongPosition = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int expected = i * n + j + 1;
                if (tiles[i][j] != expected && tiles[i][j] != 0) {
                    wrongPosition++;
                }
            }
        }
        return wrongPosition;
    }

    // Returns the Manhattan distance between this board and the goal board.
    public int manhattan() {
        int dist = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != 0) {
                    int expectedRow = (tiles[i][j] - 1) / n;
                    int expectedCol = (tiles[i][j] - 1) % n;
                    dist += Math.abs(expectedRow - i) + Math.abs(expectedCol - j);
                }
            }
        }
        return dist;
    }

    // Returns true if this board is the goal board, and false otherwise.
    public boolean isGoal() {
        return hamming() == 0;
    }

    // Returns true if this board is solvable, and false otherwise.
    public boolean isSolvable() {
        int[] tempArray = new int[n * n - 1];
        int index = 0;
        int blankRow = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] != 0) {
                    tempArray[index++] = tiles[i][j];
                } else {
                    blankRow = i;
                }
            }
        }
        if (n % 2 == 0) {
            return (Inversions.count(tempArray) + blankRow) % 2 != 0;
        }
        return Inversions.count(tempArray) % 2 == 0;
    }

    // Returns an iterable object containing the neighboring boards of this board.
    public Iterable<Board> neighbors() {
        List<Board> neighbors = new LinkedList<>();
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        int blankRow = (blankPos - 1) / n;
        int blankCol = (blankPos - 1) % n;

        for (int[] direction : directions) {
            int newRow = blankRow + direction[0];
            int newCol = blankCol + direction[1];

            if (newRow >= 0 && newRow < tiles.length && newCol >= 0 && newCol < tiles[0].length) {
                int[][] newTiles = cloneTiles();
                newTiles[blankRow][blankCol] = newTiles[newRow][newCol];
                newTiles[newRow][newCol] = 0;
                neighbors.add(new Board(newTiles));
            }
        }
        return neighbors;
    }

    // Returns true if this board is the same as other, and false otherwise.
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }
        if (other == this) {
            return true;
        }
        if (other.getClass() != this.getClass()) {
            return false;
        }
        Board otherBoard = (Board) other;
        return Arrays.deepEquals(this.tiles, otherBoard.tiles);
    }

    // Returns a string representation of this board.
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2s", tiles[i][j] == 0 ? " " : tiles[i][j]));
                if (j < n - 1) {
                    s.append(" ");
                }
            }
            if (i < n - 1) {
                s.append("\n");
            }
        }
        return s.toString();
    }

    // Returns a defensive copy of tiles[][].
    private int[][] cloneTiles() {
        int[][] clone = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                clone[i][j] = tiles[i][j];
            }
        }
        return clone;
    }

    // Unit tests the data type. 
    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board board = new Board(tiles);
        StdOut.printf("The board (%d-puzzle):\n%s\n", n, board);
        String f = "Hamming = %d, Manhattan = %d, Goal? %s, Solvable? %s\n";
        StdOut.printf(f, board.hamming(), board.manhattan(), board.isGoal(), board.isSolvable());
        StdOut.println("Neighboring boards:");
        for (Board neighbor : board.neighbors()) {
            StdOut.println(neighbor);
            StdOut.println("----------");
        }
    }
}
