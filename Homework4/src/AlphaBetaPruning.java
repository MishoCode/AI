public class AlphaBetaPruning {

    public int alphaBetaPruning(char player, Board board, int alpha, int beta, int currentDepth) {
        currentDepth++;

        if (board.isGameOver()) {
            return score(player, board, currentDepth);
        }

        if (board.getTurn() == player) {
            return getMax(player, board, alpha, beta, currentDepth);
        } else {
            return getMin(player, board, alpha, beta, currentDepth);
        }
    }

    public int getMax(char player, Board board, int alpha, int beta, int currentDepth) {
        Position bestPosition = null;

        for (Position move : board.getPossibleMoves()) {
            Board boardCopy = board.copy();


            boardCopy.insertLetter(move.row, move.col);
            int score = alphaBetaPruning(player, boardCopy, alpha, beta, currentDepth);

            if (score > alpha) {
                alpha = score;
                bestPosition = move;
            }

            if (alpha >= beta) {
                break;
            }
        }

        if (bestPosition != null) {
            board.insertLetter(bestPosition.row, bestPosition.col);
        }

        return alpha;
    }

    public int getMin(char player, Board board, int alpha, int beta, int currentDepth) {
        Position bestPosition = null;

        for (Position move : board.getPossibleMoves()) {
            Board boardCopy = board.copy();
            boardCopy.insertLetter(move.row, move.col);
            int score = alphaBetaPruning(player, boardCopy, alpha, beta, currentDepth);

            if (score < beta) {
                beta = score;
                bestPosition = move;
            }

            if (alpha >= beta) {
                break;
            }
        }

        if (bestPosition != null) {
            board.insertLetter(bestPosition.row, bestPosition.col);
        }

        return beta;
    }

    private int score(char player, Board board, int currentDepth) {
        char other = player == 'X' ? 'O' : 'X';

        if (board.getWinner() == player) {
            return 10 - currentDepth;
        } else if (board.getWinner() == other) {
            return currentDepth - 10;
        } else {
            return 0;
        }
    }
}
