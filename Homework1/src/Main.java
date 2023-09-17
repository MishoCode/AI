import exceptions.InvalidInputException;
import exceptions.PuzzleNotSolvableException;
import state.Node;

import java.util.Deque;
import java.util.concurrent.TimeoutException;

public class Main {
    public static void main(String[] args) {
        /*int[][] board = new int[3][3];
        matrix[0][0] = 1;
        matrix[0][1] = 8;
        matrix[0][2] = 2;
        matrix[1][0] = 0;
        matrix[1][1] = 4;
        matrix[1][2] = 3;
        matrix[2][0] = 7;
        matrix[2][1] = 6;
        matrix[2][2] = 5;*/

        /*int[][] board = new int[4][4];

        matrix[0][0] = 14;
        matrix[0][1] = 12;
        matrix[0][2] = 6;
        matrix[0][3] = 3;

        matrix[1][0] = 10;
        matrix[1][1] = 4;
        matrix[1][2] = 2;
        matrix[1][3] = 15;

        matrix[2][0] = 9;
        matrix[2][1] = 1;
        matrix[2][2] = 13;
        matrix[2][3] = 7;

        matrix[3][0] = 8;
        matrix[3][1] = 5;
        matrix[3][2] = 0;
        matrix[3][3] = 11 */

        /*int[][] board = new int[3][3];
        matrix[0][0] = 1;
        matrix[0][1] = 2;
        matrix[0][2] = 3;
        matrix[1][0] = 4;
        matrix[1][1] = 5;
        matrix[1][2] = 6;
        matrix[2][0] = 0;
        matrix[2][1] = 7;
        matrix[2][2] = 8; */

        //Not solvable puzzle
        /*int[][] board = new int[4][4];

        matrix[0][0] = 3;
        matrix[0][1] = 9;
        matrix[0][2] = 1;
        matrix[0][3] = 15;

        matrix[1][0] = 14;
        matrix[1][1] = 11;
        matrix[1][2] = 4;
        matrix[1][3] = 6;

        matrix[2][0] = 13;
        matrix[2][1] = 0;
        matrix[2][2] = 10;
        matrix[2][3] = 12;

        matrix[3][0] = 2;
        matrix[3][1] = 7;
        matrix[3][2] = 8;
        matrix[3][3] = 5; */


        Solver solver = null;
        Deque<Node> solution = null;
        long startTime = 0;
        try {
            solver = new Solver();
            startTime = System.currentTimeMillis();
            solution = solver.idaStar();
        } catch (PuzzleNotSolvableException | TimeoutException | InvalidInputException e) {
            System.out.println(e.getMessage());
        }

        long endTime = System.currentTimeMillis();

        if (solution != null) {
            System.out.println("Time: " + (endTime - startTime) / 1_000.0);
            solver.printSolution(solution);
        }
    }
}
