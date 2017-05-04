package ticTacToe;

/**
 * Created by saura on 4/19/2017.
 */
public class Heuristic {
    //calculate heuristic of State
    public int heuristicValue(State state) {
        return heuristicCalculator(state, Player.X) - heuristicCalculator(state, Player.O);
    }

    //calculate heuristic of a player
    private int heuristicCalculator(State state, Player p) {
        int h = 0;
        for (int i = state.board.length - 1; i >= 1; i--) {
            int val = calculateHeuristicForN(state, i, p);
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

    //calculate heuristic with Player-n where n is the number of moves in a particular row or column or diagonal
    private int calculateHeuristicForN(State currentState, int n, Player p) {
        return calculateRowHeuristic(currentState, n, p) +
                calculateColumnHeuristic(currentState, n, p)
                + calculate45degreeDiagonalHeuristic(currentState, n, p) +
                calculate135degreeDiagonalHeuristic(currentState, n, p);
    }

    //calculate row heuristic
    private int calculateRowHeuristic(State currentState, int n, Player p) {
        int count = 0;
        for (int row = 0; row < currentState.board.length; row++) {
            int timesOfNodeRepeated = 0;
            for (int column = 0; column < currentState.board.length; column++) {
                Player nextPlayer = currentState.board[row][column];
                if (p == nextPlayer)
                    timesOfNodeRepeated++;
            }
            if (timesOfNodeRepeated == n) {
                count++;
            }
        }
        return count;
    }

    //calculate column heuristic
    private int calculateColumnHeuristic(State currentState, int n, Player p) {
        int count = 0;
        for (int column = 0; column < currentState.board.length; column++) {
            int timesOfNodeRepeated = 0;
            for (int row = 0; row < currentState.board.length; row++) {
                Player nextPlayer = currentState.board[row][column];
                if (p == nextPlayer)
                    timesOfNodeRepeated++;
            }
            if (timesOfNodeRepeated == n)
                count++;
        }
        return count;
    }

    //calculate 45 degree diagonal heuristic
    private int calculate45degreeDiagonalHeuristic(State currentState, int n, Player p) {
        int count = 0;
        for (int i = 0; i < currentState.board.length; i++) {
            for (int j = 0; j < currentState.board.length; j++) {
                if (i == j && currentState.board[i][j] == p) {
                    count++;
                }
            }
        }
        if (count == n) {
            return 1;
        }
        return 0;
    }

    //calculate 135 degree diagonal heuristic
    private int calculate135degreeDiagonalHeuristic(State currentState, int n, Player p) {
        int size = currentState.board.length;
        int count = 0;
        for (int i = 0; i < currentState.board.length; i++) {
            if (currentState.board[i][size - i - 1] == p) {
                count++;
            }
        }
        if (count == n) {
            return 1;
        }
        return 0;
    }
}