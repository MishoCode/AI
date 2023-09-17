import exceptions.InvalidInputException;
import exceptions.PuzzleNotSolvableException;
import state.Node;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Objects;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

public class Solver {
    private static final long TIME_LIMIT = 30_000;

    private int[][] goalState;
    private int[][] initialState;
    private int zeroPositionInSolution;

    public Solver() throws InvalidInputException {
        init();
    }

    public Deque<Node> idaStar(Node node)
        throws PuzzleNotSolvableException, TimeoutException {
        if (!node.canBeSolved()) {
            throw new PuzzleNotSolvableException("This puzzle is not solvable!");
        }

        //int threshold = node.manhattanDistance(goalState);
        int threshold = node.manhattanDistance(zeroPositionInSolution);
        Deque<Node> path = new ArrayDeque<>();
        path.addFirst(node);
        int result;
        long startTime = System.currentTimeMillis();
        while (true) {
            result = search(path, 0, threshold);
            if (result == -1) {
                return path;
            }

            if (System.currentTimeMillis() - startTime > TIME_LIMIT) {
                throw new TimeoutException("Time limit exceeded!");
            }

            threshold = result;
        }
    }

    public int search(Deque<Node> path, int g, int threshold) {
        Node current = path.peekFirst();
        int f = g + Objects.requireNonNull(current).manhattanDistance(zeroPositionInSolution);
        if (f > threshold) {
            return f;
        } else if (current.isGoal(zeroPositionInSolution)) {
            return -1;
        }

        int min = Integer.MAX_VALUE;
        for (Node child : current.getChildren()) {
            if (!path.contains(child)) {
                path.addFirst(child);
                int result = search(path, g + 1, threshold);
                if (result == -1) {
                    return result;
                } else if (result < min) {
                    min = result;
                }

                path.removeFirst();
            }
        }

        return min;
    }

    public Deque<Node> idaStar() throws PuzzleNotSolvableException, TimeoutException {
        Node node = new Node(initialState, null);
        return idaStar(node);
    }

    public void printSolution(Deque<Node> solution) {
        System.out.println("Length: " + (solution.size() - 1));

        while (!solution.isEmpty()) {
            Node node = solution.pollLast();
            if (node.getMove() != null) {
                System.out.println(node.getMove());
            }
        }
    }

    private void init() throws InvalidInputException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        if (!isValidN(n)) {
            throw new InvalidInputException("Invalid N");
        }
        int zeroPositionInSolution = scanner.nextInt();
        this.zeroPositionInSolution = zeroPositionInSolution == -1 ? n : zeroPositionInSolution;
        int size = (int) Math.sqrt(n + 1);
        this.goalState = generateGoalState(size, zeroPositionInSolution);

        initialState = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print("Board[" + i + "][" + j + "]=");
                initialState[i][j] = scanner.nextInt();
            }
        }
    }

    private int[][] generateGoalState(int boardSize, int zeroPosition) {
        int[][] goalState = new int[boardSize][boardSize];

        if (zeroPosition == -1) {
            zeroPosition = (boardSize - 1) * 10 + (boardSize - 1);
        }

        int counter = 1;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                if (i * 10 + j % 10 == zeroPosition) {
                    goalState[i][j] = 0;
                } else {
                    goalState[i][j] = counter;
                    counter++;
                }
            }
        }

        return goalState;
    }

    private boolean isPerfectSquare(int n) {
        for (int i = 1; i <= Math.sqrt(n); i++) {
            if (i * i == n) {
                return true;
            }
        }

        return false;
    }

    private boolean isValidN(int n) {
        return isPerfectSquare(n + 1) && n >= 8;
    }
}
