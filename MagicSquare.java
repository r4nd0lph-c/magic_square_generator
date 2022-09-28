/**
 * A <b>Magic square</b> is a square array <i>n×n</i> filled with <i>n²</i>
 * different numbers in such a way that the sum of the numbers in each row, each
 * column and on both diagonals is the same.
 * <p>
 * If the array includes just the positive integers <i>1, 2, ..., n²</i> the
 * magic square is said to be <b>normal</b>.
 * <p>
 * <ul>
 * <li>{@code} Order {@code} is the number of integers along one side
 * <i>n</i>;</li>
 * <li>{@code} Magic constant {@code} is the sum of the numbers in each row,
 * column
 * and on the diagonals, depends only on <i>n</i> and is determined by the
 * formula <i>n×(n²+1)/2</i>.</li>
 * </ul>
 * <p>
 * Normal magic squares exist for all orders of <i>n>=1</i>, with the exception
 * of <i>n=2</i>, although the case of <i>n=1</i> is trivial - the square
 * consists of a single number.
 * <p>
 * This class is an implementation of a normal magic square.
 * <p>
 * <b>Examples:</b>
 * <blockquote>
 * 
 * <pre>
 * // Creating a class object (size n)
 * MagicSquare square = new MagicSquare(n);
 * 
 * // Get the order value
 * square.getOrder();
 * 
 * // Get the magic constant value
 * square.getMagicConstant();
 * 
 * // Displaying the elements of the magic square on the screen
 * for (int i = 0; i < square.getOrder(); i++) {
 *     for (int j = 0; j < square.getOrder(); j++) {
 *         System.out.print(square.getBody()[i][j] + " ");
 *     }
 *     System.out.println("");
 * }
 * </pre>
 * 
 * </blockquote>
 * 
 * @author r4nd0lph-c
 */
public class MagicSquare {
    /**
     * This is the number of integers along one side <i>n</i>.
     */
    protected int order;
    /**
     * This is the sum of the numbers in each row, column and on the diagonals. It
     * depends only on <i>n</i> and is determined by the formula <i>n×(n²+1)/2</i>.
     */
    protected int magic_constant;
    /**
     * This is a square matrix filled in according to the rules.
     */
    protected int[][] body;

    /**
     * Constructor for the user.
     * 
     * @param o - the order of the magic square;
     */
    public MagicSquare(int o) {
        Init(o, 1);
    }

    /**
     * Constructor for internal use.
     * 
     * @param o   - the order of the magic square;
     * @param num the first number in the chain to generate for
     *            {@link #SinglyEvenSolution()};
     */
    private MagicSquare(int o, int num) {
        Init(o, num);
    }

    /**
     * Implementing the logic of {@link #MagicSquare(int)} and
     * {@link #MagicSquare(int, int)}.
     * 
     * @param o   - the order of the magic square;
     * @param num the first number in the chain to generate for
     *            {@link #SinglyEvenSolution()};
     */
    private void Init(int o, int num) {
        if (o < 1) {
            throw new java.lang.Error("Normal magic squares exist for all orders of n>=1, with the exception of n=2.");
        } else if (o == 1) {
            this.order = o;
            this.magic_constant = 1;
            this.body = new int[][] { new int[] { 1 } };
        } else if (o == 2) {
            throw new java.lang.Error("Normal magic squares exist for all orders of n>=1, with the exception of n=2.");
        } else {
            this.order = o;
            this.magic_constant = o * (o * o + 1) / 2;
            this.body = new int[o][o];
            if (o % 2 != 0) {
                this.OddSolution(num);
            } else if (o % 4 == 0) {
                this.DoublyEvenSolution();
            } else {
                this.SinglyEvenSolution();
            }
        }
    }

    /**
     * Help function for {@link #SinglyEvenSolution()}
     * 
     * @param a - the first array;
     * @param b - the second array;
     * @param i - defines the position of the element (vertical);
     * @param j - defines the position of the element (horizontal);
     */
    private void Swap(int[][] a, int[][] b, int i, int j) {
        a[i][j] = a[i][j] ^ b[i][j] ^ (b[i][j] = a[i][j]);
    }

    /**
     * Help function for {@link #SinglyEvenSolution()}
     * 
     * @param block - the block whose elements will be inserted into the body;
     * @param add_i - addition to the starting position (vertical);
     * @param add_j - addition to the starting position (horizontal);
     */
    private void Merge(int[][] block, int add_i, int add_j) {
        for (int i = add_i; i < block[0].length + add_i; i++) {
            for (int j = add_j; j < block[0].length + add_j; j++) {
                this.body[i][j] = block[i - add_i][j - add_j];
            }
        }
    }

    /**
     * Filling in the matrix according to the following algorithm:
     * https://www.1728.org/magicsq1.htm
     * 
     * @param num - the first number in the chain to generate.
     */
    protected void OddSolution(int num) {
        int completed = 1;
        int y = 0;
        int x = this.order / 2;
        this.body[y][x] = num;
        while (completed != this.order * this.order) {
            num++;
            y--;
            x++;
            if (y == -1 && x == this.order) {
                y += 2;
                x--;
            }
            if (y == -1) {
                y = this.order - 1;
            }
            if (x == this.order) {
                x = 0;
            }
            if (this.body[y][x] != 0) {
                y += 2;
                x--;
            }
            this.body[y][x] = num;
            completed++;
        }
    }

    /**
     * Filling in the matrix according to the following algorithm:
     * https://www.1728.org/magicsq3.htm
     */
    protected void SinglyEvenSolution() {
        // A B C D blocks generation
        int block_o = this.order / 2;
        MagicSquare[] blocks = new MagicSquare[4];
        for (int i = 0; i < 4; i++) {
            blocks[i] = new MagicSquare(block_o, (block_o * block_o) * i + 1);
        }

        // A & D swaps
        int change_size = block_o / 2;
        for (int i = 0; i < change_size; i++) {
            for (int j = 0; j < change_size; j++) {
                // top
                Swap(blocks[0].getBody(), blocks[3].getBody(), i, j);
                // bottom
                Swap(blocks[0].getBody(), blocks[3].getBody(), block_o - i - 1, j);
            }
        }
        // middle
        for (int j = 1; j < change_size + 1; j++) {
            Swap(blocks[0].getBody(), blocks[3].getBody(), change_size, j);
        }

        // B & C swaps
        int right_col_width = this.order / 4 - 1;
        if (right_col_width != 0) {
            for (int i = 0; i < block_o; i++) {
                for (int j = block_o - right_col_width; j < block_o; j++) {
                    Swap(blocks[1].getBody(), blocks[2].getBody(), i, j);
                }
            }
        }

        // Merge
        Merge(blocks[0].getBody(), 0, 0);
        Merge(blocks[1].getBody(), block_o, block_o);
        Merge(blocks[2].getBody(), 0, block_o);
        Merge(blocks[3].getBody(), block_o, 0);
    }

    /**
     * Filling in the matrix according to the following algorithm:
     * https://www.1728.org/magicsq2.htm
     */
    protected void DoublyEvenSolution() {
        int num = 1;
        int t = this.order / 4;
        for (int i = 0; i < this.order; i++) {
            for (int j = 0; j < this.order; j++) {
                if (((i < t) || (i >= this.order - t)) && ((j < t) || (j >= this.order - t)) ||
                        ((i >= t) && (i < this.order - t)) && ((j >= t) && (j < this.order - t))) {
                    this.body[i][j] = num;
                } else {
                    this.body[i][j] = this.order * this.order - num + 1;
                }
                num++;
            }
        }
    }

    /**
     * @return {@link #order}
     */
    public int getOrder() {
        return this.order;
    }

    /**
     * @return {@link #magic_constant}
     */
    public int getMagicConstant() {
        return this.magic_constant;
    }

    /**
     * @return {@link #body}
     */
    public int[][] getBody() {
        return this.body;
    }
}