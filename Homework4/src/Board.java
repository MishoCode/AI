import java.util.ArrayList;
import java.util.List;

public class Board {
    char[][] board = new char[3][3];
    private char winner = ' ';
    private char currentPlayer = 'O';
    private boolean isGameOver = false;

    public Board() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    public Board(char[][] board) {
        this.board = board;
    }

    public void setCurrentPlayer(char currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public void setWinner(char winner) {
        this.winner = winner;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public char getWinner() {
        return winner;
    }

    public char getTurn() {
        return currentPlayer;
    }

    public boolean isEmptyPosition(int row, int col) {
        return board[row][col] == ' ';
    }

    public boolean checkForWin() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] != ' ') {
                winner = board[i][0];
                isGameOver = true;
                return true;
            }

            if (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] != ' ') {
                winner = board[0][i];
                isGameOver = true;
                return true;
            }
        }

        if (board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] != ' ') {
            winner = board[0][0];
            isGameOver = true;
            return true;
        }

        if (board[2][0] == board[1][1] && board[2][0] == board[0][2] && board[2][0] != ' ') {
            winner = board[2][0];
            isGameOver = true;
            return true;
        }

        return false;

    }

    public boolean checkForDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }

        isGameOver = true;
        return true;
    }

    public boolean insertLetter(int row, int col) {
        if ((row < 0 || row > 2) || (col < 0 || col > 2)) {
            return false;
        }

        if (!isEmptyPosition(row, col)) {
            return false;
        }

        board[row][col] = currentPlayer;
        checkForWin();
        checkForDraw();
        currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        return true;
    }


    public List<Position> getPossibleMoves() {
        List<Position> positions = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    positions.add(new Position(i, j));
                }
            }
        }

        return positions;
    }

    public Board copy() {
        char[][] newBoard = new char[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                newBoard[i][j] = board[i][j];
            }
        }

        Board boardCopy = new Board(newBoard);
        boardCopy.setCurrentPlayer(currentPlayer);
        boardCopy.setGameOver(isGameOver);
        boardCopy.setWinner(winner);

        return boardCopy;
    }

    public void print() {
        System.out.println(board[0][0] + "|" + board[0][1] + "|" + board[0][2]);
        System.out.println("-+-+-");
        System.out.println(board[1][0] + "|" + board[1][1] + "|" + board[1][2]);
        System.out.println("-+-+-");
        System.out.println(board[2][0] + "|" + board[2][1] + "|" + board[2][2]);
        System.out.println();
    }
}
