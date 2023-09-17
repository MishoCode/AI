import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Board {
    private static final int K = 2;

    private final int n;

    //queens[i] = the row where the queen in the i-th column is located
    private int[] queens;

    //rowQueensCount[i] = the count of the queens in the i-th row
    private int[] rowQueensCount;

    //mainDiagQueensCount[i] = the count of the queens in the i-th main diagonal
    private int[] mainDiagQueensCount;

    //secondaryDiagQueensCount[i] = the count of the queens in the i-th secondary diagonal
    private int[] secondaryDiagQueensCount;

    private final Random random = new Random();

    public Board(int n) {
        this.n = n;
    }

    public int countBoardConflicts() {
        int count = 0;

        for (int i = 0; i < 2 * n - 1; i++) {
            if (i < n) {
                count += (rowQueensCount[i] * (rowQueensCount[i] - 1)) / 2;
            }

            count += (mainDiagQueensCount[i] * (mainDiagQueensCount[i] - 1)) / 2;
            count += (secondaryDiagQueensCount[i] * (secondaryDiagQueensCount[i] - 1)) / 2;
        }

        return count;
    }

    public int getColWithMaxConflicts() {
        List<Integer> columns = new ArrayList<>();
        int conflicts;
        int maxConflicts = 0;

        for (int i = 0; i < n; i++) {
            int row = queens[i];
            conflicts = rowQueensCount[row] - 1
                        + mainDiagQueensCount[i - row + n - 1] - 1
                        + secondaryDiagQueensCount[i + row] - 1;
            if (conflicts == maxConflicts) {
                columns.add(i);
            } else if (conflicts > maxConflicts) {
                maxConflicts = conflicts;
                columns.clear();
                columns.add(i);
            }
        }

        return maxConflicts == 0 ? -1 : columns.get(random.nextInt(columns.size()));
    }

    public int getRowWithMinConflicts(int col) {
        int oldRow = queens[col];
        int minConflicts = Integer.MAX_VALUE;
        int conflicts;

        List<Integer> rows = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            if (i != oldRow) {
                conflicts = rowQueensCount[i]
                            + mainDiagQueensCount[col - i + n - 1]
                            + secondaryDiagQueensCount[col + i];
            } else {
                conflicts = rowQueensCount[i] - 1
                            + mainDiagQueensCount[col - i + n - 1] - 1
                            + secondaryDiagQueensCount[col + i] - 1;
            }

            if (conflicts == minConflicts && i != oldRow) {
                rows.add(i);
            } else if (conflicts < minConflicts) {
                minConflicts = conflicts;
                rows.clear();
                rows.add(i);
            }
        }

        return rows.isEmpty() ? 0 : rows.get(random.nextInt(rows.size()));
    }

    private boolean hasConflicts() {
        return countBoardConflicts() > 0;
    }

    public void solve() {
        if (n == 2 || n == 3) {
            System.out.println("There is no solution");
            return;
        }

        long startTime = System.currentTimeMillis();
        init();
        int count = 0;
        int row, col, oldRow;

        while (count++ <= K * n) {
            col = getColWithMaxConflicts();
            if (col == -1) {
                print(startTime);
                return;
            }

            oldRow = queens[col];
            row = getRowWithMinConflicts(col);
            queens[col] = row;

            mainDiagQueensCount[col - row + n - 1]++;
            mainDiagQueensCount[col - oldRow + n - 1]--;
            secondaryDiagQueensCount[col + row]++;
            secondaryDiagQueensCount[col + oldRow]--;
            rowQueensCount[row]++;
            rowQueensCount[oldRow]--;
        }

        if (!hasConflicts()) {
            print(startTime);
        } else {
            solve();
        }
    }

    public void print(long startTime) {
        System.out.println("Time:" + ((System.currentTimeMillis() - startTime) / 1000.0));

        if (n > 100) {
            return;
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < queens[i]; j++) {
                System.out.print(" _ ");
            }
            System.out.print(" * ");
            for (int j = 0; j < n - queens[i] - 1; j++) {
                System.out.print(" _ ");
            }
            System.out.println();
        }
    }

    private void init() {
        queens = new int[n];
        rowQueensCount = new int[n];
        mainDiagQueensCount = new int[2 * n - 1];
        secondaryDiagQueensCount = new int[2 * n - 1];

        for (int i = 0; i < n; i++) {
            int row = getRowWithMinConflicts(i);
            queens[i] = row;
            rowQueensCount[row]++;
            mainDiagQueensCount[i - row + n - 1]++;
            secondaryDiagQueensCount[i + row]++;
        }
    }
}
