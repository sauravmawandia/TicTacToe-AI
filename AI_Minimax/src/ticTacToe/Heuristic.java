package ticTacToe;

/**
 * Created by saura on 4/19/2017.
 */
public class Heuristic {
    public int heuristicValue(BoardNode boardNode) {
        return heuristicCalculator(boardNode, Player.X) - heuristicCalculator(boardNode, Player.O);
    }

    private int heuristicCalculator(BoardNode boardNode, Player p) {
        int h = 0;
        for (int i = boardNode.board.length - 1; i >= 1; i--) {
            int val = calculateHeuristicWithNandPlayer(boardNode, i, p);
            if (i == 3) {
                h += 6 * val;
            } else if (i == 2) {
                h += 3 * val;
            } else {
                h += val;
            }
        }
        return h;
    }

    private int calculateHeuristicWithNandPlayer(BoardNode currentBoardNode, int n, Player p) {
        return calculateRowHeuristic(currentBoardNode, n, p) +
                calculateColumnHeuristic(currentBoardNode, n, p)
                + calculate45degreeDiagonalHeuristic(currentBoardNode, n, p) +
                calculate135degreeDiagonalHeuristic(currentBoardNode, n, p);
    }

    private int calculateRowHeuristic(BoardNode currentBoardNode, int n, Player p) {
        int count = 0;
        for (int row = 0; row < currentBoardNode.board.length; row++) {
            int timesOfNodeRepeated = 0;
            for (int column = 0; column < currentBoardNode.board.length; column++) {
                Player nextPlayer = currentBoardNode.board[row][column];
                if (p == nextPlayer)
                    timesOfNodeRepeated++;
            }
            if (timesOfNodeRepeated == n) {
                count++;
            }
        }
        return count;
    }

    private int calculateColumnHeuristic(BoardNode currentBoardNode, int n, Player p) {
        int count = 0;
        for (int column = 0; column < currentBoardNode.board.length; column++) {
            int timesOfNodeRepeated = 0;
            for (int row = 0; row < currentBoardNode.board.length; row++) {
                Player nextPlayer = currentBoardNode.board[row][column];
                if (p == nextPlayer)
                    timesOfNodeRepeated++;
            }
            if (timesOfNodeRepeated == n)
                count++;
        }
        return count;
    }

    private int calculate45degreeDiagonalHeuristic(BoardNode currentBoardNode, int n, Player p) {
        int count = 0;
        for (int i = 0; i < currentBoardNode.board.length; i++) {
            for (int j = 0; j < currentBoardNode.board.length; j++) {
                if (i == j && currentBoardNode.board[i][j] == p) {
                    count++;
                }
            }
        }
        if (count == n) {
            return 1;
        }
        return 0;
    }

    private int calculate135degreeDiagonalHeuristic(BoardNode currentBoardNode, int n, Player p) {
        int size = currentBoardNode.board.length;
        int count = 0;
        for (int i = 0; i < currentBoardNode.board.length; i++) {
            if (currentBoardNode.board[i][size - i - 1] == p) {
                count++;
            }
        }
        if (count == n) {
            return 1;
        }
        return 0;
    }
}