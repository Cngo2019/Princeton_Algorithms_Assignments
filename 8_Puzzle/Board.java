import java.util.ArrayList;

public class Board {

    private int[][] tiles;
    private int[][] twin;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        this.tiles = new int[tiles.length][tiles.length];
        for (int r = 0; r < tiles.length; r++) {
            for (int c = 0; c < tiles[0].length; c++) {
                this.tiles[r][c] = tiles[r][c];
            }
        }

        /*
        figure a way to swap tiles.
         */

    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(tiles.length + "\n");
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles.length; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return tiles.length;
    }

    // number of tiles out of place
    public int hamming() {
        int k = 1;
        int counter = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (tiles[i][j] != k && tiles[i][j] != 0) {
                    counter += 1;
                }
                k += 1;
            }
        }

        return counter;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int sum = 0;
        for (int row = 0; row < tiles.length; row++) {
            for (int col = 0; col < tiles[0].length; col++) {
                int value;
                if (tiles[row][col] != 0) {
                    value = tiles[row][col];
                    int true_row = (value - 1) / tiles.length;
                    int true_col = (value - 1) - (true_row * tiles.length);
                    sum += Math.abs((true_row - row)) + Math.abs((true_col - col));
                }
            }
        }

        return sum;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return manhattan() == 0;
    }

    // does this board equal y?
    @Override
    public boolean equals(Object y) {
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board) y;
        boolean isSame = true;

        if (that.tiles.length != this.tiles.length)
            return false;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (this.tiles[i][j] != that.tiles[i][j]) {
                    return false;
                }
            }
        }

        return isSame;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> my_neighbors = new ArrayList<Board>();
        // boolean variables to help with edge cases.
        int[] location_of_zero = new int[2];
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (tiles[i][j] == 0) {
                    location_of_zero[0] = i;
                    location_of_zero[1] = j;
                }
            }
        }
        /**
         * 0 can move up only if:
         *   location_of_zero[0] != 0
         * 0 can only move down if
         *   location_of_zero[0] != tiles.length - 1
         * 0 can only move left if
         *    location_of_zero[1] != 0
         * 0 can only move right if:
         *    location_of_zero[1] != length - 1
         */
        if (location_of_zero[0] != 0) {
            int temp = tiles[location_of_zero[0] - 1][location_of_zero[1]];
            tiles[location_of_zero[0] - 1][location_of_zero[1]] = 0;
            tiles[location_of_zero[0]][location_of_zero[1]] = temp;
            my_neighbors.add(new Board(tiles));
            //revert it back to normal
            tiles[location_of_zero[0] - 1][location_of_zero[1]] = temp;
            tiles[location_of_zero[0]][location_of_zero[1]] = 0;
        }

        if (location_of_zero[0] != tiles.length - 1) {
            int temp = tiles[location_of_zero[0] + 1][location_of_zero[1]];
            tiles[location_of_zero[0] + 1][location_of_zero[1]] = 0;
            tiles[location_of_zero[0]][location_of_zero[1]] = temp;
            my_neighbors.add(new Board(tiles));
            //revert it back to normal
            tiles[location_of_zero[0] + 1][location_of_zero[1]] = temp;
            tiles[location_of_zero[0]][location_of_zero[1]] = 0;
        }

        if (location_of_zero[1] != tiles.length - 1) {
            int temp = tiles[location_of_zero[0]][location_of_zero[1] + 1];
            tiles[location_of_zero[0]][location_of_zero[1] + 1] = 0;
            tiles[location_of_zero[0]][location_of_zero[1]] = temp;
            my_neighbors.add(new Board(tiles));
            //revert it back to normal
            tiles[location_of_zero[0]][location_of_zero[1] + 1] = temp;
            tiles[location_of_zero[0]][location_of_zero[1]] = 0;
        }

        if (location_of_zero[1] != 0) {
            int temp = tiles[location_of_zero[0]][location_of_zero[1] - 1];
            tiles[location_of_zero[0]][location_of_zero[1] - 1] = 0;
            tiles[location_of_zero[0]][location_of_zero[1]] = temp;
            my_neighbors.add(new Board(tiles));
            //revert it back to normal
            tiles[location_of_zero[0]][location_of_zero[1] - 1] = temp;
            tiles[location_of_zero[0]][location_of_zero[1]] = 0;
        }

        return my_neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        if (twin == null) {

            int[][] tiles_temp = tiles.clone();
            int firstValRow = -1;
            int firstValCol = -1;
            int secondValRow = -1;
            int secondValCol = -1;

            for (int r = 0; r < tiles_temp.length; r++) {
                for (int c = 0; c < tiles_temp[0].length; c++) {
                    if (tiles_temp[r][c] != 0 && firstValRow < 0 && firstValCol < 0) {
                        firstValRow = r;
                        firstValCol = c;

                    }
                    else if (tiles_temp[r][c] != 0 && secondValRow < 0 && secondValCol < 0) {
                        secondValRow = r;
                        secondValCol = c;
                    }

                }
            }

            int temp = tiles_temp[firstValRow][firstValCol];
            tiles_temp[firstValRow][firstValCol] = tiles_temp[secondValRow][secondValCol];
            tiles_temp[secondValRow][secondValCol] = temp;
            twin = tiles_temp.clone();
        }

        return new Board(twin);
    }

}
