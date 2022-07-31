public class Percolation {

    private final int[] parent;
    private final int[] array;
    private final int[] grid;
    private int[] size;
    private int numberOfOpenSites;
    private final int top;
    private final int down;
    private final int side;

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be valid (>0)");
        }
        numberOfOpenSites = 0;
        side = n;
        down = n * n + 1;
        top = 0;
        parent = new int[n * n + 2];
        grid = new int[n * n + 2];
        array = new int[n * n + 2];
        size = new int[n * n + 2];
        int k = n * n + 2;
        for (int i = 0; i < k; i++) {
            grid[i] = 0;
            parent[i] = i;
            array[i] = i;
            size[i] = 1;
        }
        grid[top] = 1;
        grid[down] = 1;
    }

    public void open(int row, int col) {
        int number = (row - 1) * side + col;
        if (row > side || row < 1 || col > side || col < 1 || number > side * side || number < 1) {
            throw new IllegalArgumentException("n must be valid (>0)");
        }
        if (grid[number] == 1) {
            return;
        } else {
            grid[number] = 1;
            if (number + 1 <= array.length - 1 && grid[number + 1] != 0 && (number % side != 0 || number == side * side))
                union(array[number], array[number + 1]);
            if (number - 1 >= 0 && grid[number - 1] != 0 && (number % side != 1 || number == 1))
                union(array[number], array[number - 1]);
            if (number - side >= 1 && grid[number - side] != 0)
                union(array[number], array[number - side]);
            if (number + side < grid.length - 1 && grid[number + side] != 0)
                union(array[number], array[number + side]);
            if (number > side * side - side)
                union(array[number], array[down]);
            if (number <= side)
                union(array[number], array[top]);
        }
        numberOfOpenSites++;
    }

    public boolean isOpen(int row, int col) {
        if (row > side || row < 1 || col > side || col < 1 || (row - 1) * side + col > side * side || (row - 1) * side + col < 1) {
            throw new IllegalArgumentException("n must be valid (>0)");
        }
        return grid[(row - 1) * side + col] == 1;
    }

    public boolean isFull(int row, int col) {
        if (row > side || row < 1 || col > side || col < 1 || (row - 1) * side + col > side * side || (row - 1) * side + col < 1) {
            throw new IllegalArgumentException("n must be valid (>0)");
        }
        return find((row - 1) * side + col) == find(top);
    }

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    public boolean percolates() {
        return find(top) == find(down);
    }

    private int find(int p) {
        int k = p;
        while (p != parent[p])
            p = parent[p];
        parent[k] = p;
        return p;
    }

    private void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ)
            return;
        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        } else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
    }
}