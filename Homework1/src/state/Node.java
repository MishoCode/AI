package state;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Node {
    private final int[][] state;
    private final String move;

    public Node(int[][] state, String move) {
        this.state = state;
        this.move = move;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node node = (Node) o;
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state.length; j++) {
                if (state[i][j] != node.state[i][j]) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(state);
    }

    public String getMove() {
        return move;
    }

    public boolean canBeSolved() {
        int[] arr = new int[state.length * state.length];
        int k = 0;
        for (int[] row : state) {
            for (int i : row) {
                arr[k] = i;
                k++;
            }
        }
        int inversionsCount = inversionsCount(arr);
        if (state.length % 2 == 1) {
            return inversionsCount % 2 == 0;
        } else {
            int zeroPositionFromBottom = state.length - getZeroPosition().getX();
            if (zeroPositionFromBottom % 2 == 1) {
                return inversionsCount % 2 == 0;
            } else {
                return inversionsCount % 2 == 1;
            }
        }
    }

    private int inversionsCount(int[] arr) {
        int count = 0;

        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] > 0 && arr[j] > 0 && arr[i] > arr[j]) {
                    count++;
                }
            }
        }

        return count;
    }

    public int manhattanDistance(int zeroPosition) {
        int sum = 0;
        int size = state.length;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                int value = state[i][j];

                if (value != 0) {
                    if (i * size + j <= zeroPosition) {
                        value -= 1;
                    }

                    int rowVal = (value / size) - i;
                    int colVal = (value % size) - j;
                    sum += Math.abs(rowVal) + Math.abs(colVal);
                }
            }
        }

        return sum;
    }

    public boolean isGoal(int zeroPosition) {
        return manhattanDistance(zeroPosition) == 0;
    }

    public Position getZeroPosition() {
        return getPosition(state, 0);
    }

    public List<Node> getChildren() {
        Position zeroPosition = getZeroPosition();
        int row = zeroPosition.getX();
        int col = zeroPosition.getY();

        Node up = moveUp(copy(state), row, col);
        Node right = moveRight(copy(state), row, col);
        Node down = moveDown(copy(state), row, col);
        Node left = moveLeft(copy(state), row, col);

        List<Node> children = new ArrayList<>();
        children.add(up);
        children.add(right);
        children.add(down);
        children.add(left);
        return children.stream().filter(Objects::nonNull).collect(Collectors.toList());
    }

    public Node moveUp(int[][] board, int row, int col) {
        if (row < board.length - 1) {
            board[row][col] = board[row + 1][col];
            board[row + 1][col] = 0;
        } else {
            return null;
        }

        return new Node(board, "up");
    }

    public Node moveRight(int[][] board, int row, int col) {
        if (col > 0) {
            board[row][col] = board[row][col - 1];
            board[row][col - 1] = 0;
        } else {
            return null;
        }

        return new Node(board, "right");
    }

    public Node moveDown(int[][] board, int row, int col) {
        if (row > 0) {
            board[row][col] = board[row - 1][col];
            board[row - 1][col] = 0;
        } else {
            return null;
        }

        return new Node(board, "down");
    }

    public Node moveLeft(int[][] board, int row, int col) {
        if (col < board.length - 1) {
            board[row][col] = board[row][col + 1];
            board[row][col + 1] = 0;
        } else {
            return null;
        }

        return new Node(board, "left");
    }

    //method for debug purposes
    public void printState() {
        for (int[] ints : state) {
            for (int j = 0; j < state.length; j++) {
                System.out.print(ints[j]);
            }

            System.out.println();
        }

        System.out.println("====================");
    }

    private int[][] copy(int[][] currentState) {
        int[][] copyState = new int[currentState.length][currentState.length];

        for (int i = 0; i < currentState.length; i++) {
            for (int j = 0; j < currentState.length; j++) {
                copyState[i][j] = currentState[i][j];
            }
        }

        return copyState;
    }

    private Position getPosition(int[][] state, int value) {
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state.length; j++) {
                if (state[i][j] == value) {
                    return new Position(i, j);
                }
            }
        }

        return null;
    }
}
