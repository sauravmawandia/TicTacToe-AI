package ticTacToe;

/**
 * Created by saura on 4/19/2017.
 */

import java.util.ArrayList;
import java.util.Vector;


public class GameBoard {

    //terminal values
    public static int X_WINS = 1000;
    public static int O_WINS = -1000;
    public static int DRAW = 0;

    public State initializeAllState() {
        return new State();
    }

    public State initializeState(Player[][] board) {
        State root = new State();
        root.board = board;
        root.nextPlayer = Player.X;
        return root;
    }

    //check if the state is terminal
    public boolean isTerminalState(State currentState) {
        return this.terminalTest(currentState) || (this.checkIfTerminalState(currentState) == null);
    }

    //get utility value of state
    public int getUtilityOfState(State currentState) {
        if (currentState.nextPlayer == Player.X && this.terminalTest(currentState) == true) return O_WINS;
        if (currentState.nextPlayer == Player.O && this.terminalTest(currentState) == true) return X_WINS;
        return DRAW;
    }

    //check whether the state is terminal
    public boolean terminalTest(State currentState) {
        return (this.checkWinOnRow(currentState)
                || this.checkWinOnColumn(currentState)
                || this.checkIfWinOnBothDiagonalIsPossible(currentState));
    }

    //check whether win is possible in the row
    public boolean checkWinOnRow(State currentState) {
        for (int row = 0; row < currentState.board.length; row++) {
            int timesOfNodeRepeated = 0;
            Player scanForElement = currentState.board[row][0];
            if (scanForElement == Player.B) break;
            for (int column = 1; column < currentState.board.length; column++) {
                Player nextPlayer = currentState.board[row][column];
                if (nextPlayer == Player.B) break;
                else if (scanForElement != nextPlayer) break;
                else timesOfNodeRepeated++;
            }
            if (timesOfNodeRepeated == 3) return true;
        }
        return false;
    }

    //check if win is possible in column
    public boolean checkWinOnColumn(State currentState) {
        for (int column = 0; column < currentState.board.length; column++) {
            int timesOfNodeRepeated = 0;
            Player scanForElement = currentState.board[0][column];
            if (scanForElement == Player.B) break;
            for (int row = 1; row < currentState.board.length; row++) {
                Player nextPlayer = currentState.board[row][column];
                if (nextPlayer == Player.B) break;
                else if (scanForElement != nextPlayer) break;
                else timesOfNodeRepeated++;
            }
            if (timesOfNodeRepeated == 3) return true;
        }
        return false;
    }

    //check if win is possible in the diagonal
    public boolean checkIfWinOnBothDiagonalIsPossible(State currentState) {
        Player[][] aBoard = currentState.board;

        if (aBoard[0][0] != Player.B && aBoard[3][3] != Player.B)
            return this.checkWinOnMainDiagonal(currentState);
        else if (aBoard[0][3] != Player.B && aBoard[3][0] != Player.B)
            return this.checkWinOnAntiDiagonal(currentState);
        else return false;
    }

    //check if win is possible in main diagonal
    public boolean checkWinOnMainDiagonal(State currentState) {
        Player[][] aBoard = currentState.board;
        return (aBoard[1][1] == aBoard[0][0] && aBoard[1][1] == aBoard[2][2] && aBoard[3][3] == aBoard[2][2]);
    }

    //check if win is possible on anti diagonal
    public boolean checkWinOnAntiDiagonal(State currentState) {
        Player[][] board = currentState.board;
        return (board[0][3] == board[1][2] && board[1][2] == board[2][1] && board[2][1] == board[3][0]);
    }

    //check if it is a terminal state
    public int[] checkIfTerminalState(State currentState) {
        int boardSize = currentState.board.length;
        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                if (currentState.board[row][column] == Player.B) return this.addMoveToArray(row, column);
            }
        }
        return null;
    }

    //get Available moves
    public ArrayList<int[]> getAvailableMoves(State currentState) {
        int boardSize = currentState.board.length;
        ArrayList<int[]> list = new ArrayList<int[]>();
        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                if (currentState.board[row][column] == Player.B) list.add(this.addMoveToArray(row, column));
            }
        }
        return list;
    }

    //a method to add move
    public int[] addMoveToArray(int a, int b) {
        int[] ar = {a,b};
        return ar;
    }

    //copy board
    public Player[][] copyBoard(Player[][] aBoard) {
        int boardSize = aBoard.length;
        Player[][] newBoard = new Player[boardSize][boardSize];
        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++)
                newBoard[row][column] = aBoard[row][column];
        }
        return newBoard;
    }

    //update move in the board
    public Player[][] updateBoard(State currentState, int[] emptySquareOnBoard) {
        Player[][] newBoard = this.copyBoard(currentState.board);
        newBoard[emptySquareOnBoard[0]][emptySquareOnBoard[1]] = currentState.nextPlayer;
        return newBoard;
    }

    //get immediate  Successor of currentState
    public State getImmediateSuccessor(State currentState, int[] emptySquareOnBoard) {
        if (this.isTerminalState(currentState) == true) return null;
        else {
            if (currentState.nextPlayer == Player.X)
                return new State(this.updateBoard(currentState, emptySquareOnBoard),
                        currentState, this.getUtilityOfState(currentState), currentState.depth + 1, Player.O);
            else return new State(this.updateBoard(currentState, emptySquareOnBoard), currentState,
                    this.getUtilityOfState(currentState), currentState.depth + 1, Player.X);
        }
    }

    //get all successors
    public Vector<State> getAllSuccessors(State currentState) {
        Vector<State> allSuccessors = new Vector<State>();
        ArrayList<int[]> allEmptySquareOnBoard = this.getAvailableMoves(currentState);
        int numberOfEmptySquareOnBoard = allEmptySquareOnBoard.size();
        for (int i = 0; i < numberOfEmptySquareOnBoard; i++) {
            allSuccessors.add(this.getImmediateSuccessor(currentState, allEmptySquareOnBoard.get(i)));
        }
        return allSuccessors;
    }
}