/**
 * Normal Magic Square
 * 
 * @author r4nd0lph-c
 */
public class MagicSquare {
    protected int order;
    protected int magic_constant;
    protected int[][] body;

    public MagicSquare(int o) {
        Init(o, 1);
    }

    private MagicSquare(int o, int num) {
        Init(o, num);
    }

    private void Init(int o, int num) {
        if (o < 1) {
            // Error 1
            throw new java.lang.Error("this is very bad 1");
        } else if (o == 1) {
            this.order = o;
            this.magic_constant = 1;
            this.body = new int[][] { new int[] { 1 } };
        } else if (o == 2) {
            // Error 2
            throw new java.lang.Error("this is very bad 2");
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

    private void Swap(int[][] a, int[][] b, int i, int j) {
        a[i][j] = a[i][j] ^ b[i][j];
        b[i][j] = a[i][j] ^ b[i][j];
        a[i][j] = a[i][j] ^ b[i][j];
    }

    private void Merge(int[][] block, int add_i, int add_j) {
        for (int i = add_i; i < block[0].length + add_i; i++) {
            for (int j = add_j; j < block[0].length + add_j; j++) {
                this.body[i][j] = block[i - add_i][j - add_j];
            }
        }
    }

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

    public int getOrder() {
        return this.order;
    }

    public int getMagicConstant() {
        return this.magic_constant;
    }

    public int[][] getBody() {
        return this.body;
    }
}