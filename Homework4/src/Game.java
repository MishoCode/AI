import java.util.Scanner;

public class Game {
    private final Board board = new Board();
    private final Scanner scanner = new Scanner(System.in);
    private final AlphaBetaPruning solver = new AlphaBetaPruning();


    public void play() {
        System.out.println("You play with 'O' and the computer plays with 'X'.");
        System.out.println("Choose who plays first by picking the corresponding option");
        System.out.println("Type 1 if you want to play first");
        System.out.println("Type 2 if you want the computer to play first");

        int choice;
        while (true) {
            System.out.println("Your choice: ");

            choice = scanner.nextInt();

            if (choice == 1) {
                board.setCurrentPlayer('O');
                break;
            } else if (choice == 2) {
                board.setCurrentPlayer('X');
                break;
            } else {
                System.out.println("This is an invalid choice!");
            }

        }

        System.out.println("==============================");


        while (true) {
            board.print();
            nextMove();

            if (board.isGameOver()) {
                board.print();
                printWinner();
                break;
            }
        }
    }

    public void nextMove() {
        if (board.getTurn() == 'O') {
            playerMove();
        } else {
            computerMove(board.getTurn(), board, Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
        }
    }

    public void computerMove(char player, Board board, int alpha, int beta, int currentDepth) {
        System.out.println("Computer: ");
        solver.alphaBetaPruning(player, board, alpha, beta, currentDepth);
    }

    public void playerMove() {
        while (true) {
            System.out.println("Your turn: ");
            int row = scanner.nextInt();
            int col = scanner.nextInt();
            boolean isInserted = board.insertLetter(row - 1, col - 1);
            if (isInserted) {
                break;
            } else {
                System.out.println("This position is invalid. Try again!");
            }
        }
    }

    public void printWinner() {
        char winner = board.getWinner();
        if (winner == ' ') {
            System.out.println("Draw!");
        } else if (winner == 'X') {
            System.out.println("The computer wins!");
        } else if (winner == 'O') {
            System.out.println("You win!");
        }
    }
}
