package ticTacToe;

/**
 * Created by saura on 4/19/2017.
 */

import java.util.ArrayList;
import java.util.Vector;


public class GameBoard {

    public static int X_WINS = 1000;
    public static int O_WINS = -1000;
    public static int DRAW = 0;

    public GameBoard() {
    }

    public BoardNode initializeAllNode() {
        return new BoardNode();
    }

    public BoardNode initializeNode(Player[][] board) {
        BoardNode root = new BoardNode();
        root.board = board;
        root.nextPlayer = Player.X;
        return root;
    }

    public boolean isLeafNode(BoardNode currentBoardNode) {
        return this.terminalTest(currentBoardNode) || (this.checkForAvailableMoves(currentBoardNode) == null);
    }

    public int getUtilityOfState(BoardNode currentBoardNode) {
        if (currentBoardNode.nextPlayer == Player.X && this.terminalTest(currentBoardNode) == true) return O_WINS;
        if (currentBoardNode.nextPlayer == Player.O && this.terminalTest(currentBoardNode) == true) return X_WINS;
        return DRAW;
    }

    public boolean terminalTest(BoardNode currentBoardNode) {
        return (this.checkWinOnRow(currentBoardNode)
                || this.checkWinOnColumn(currentBoardNode)
                || this.checkWinOnDiagonal(currentBoardNode));
    }

    public boolean checkWinOnRow(BoardNode currentBoardNode) {
        for (int row = 0; row < currentBoardNode.board.length; row++) {
            int timesOfNodeRepeated = 0;
            Player scanForElement = currentBoardNode.board[row][0];
            if (scanForElement == Player.B) break;
            for (int column = 1; column < currentBoardNode.board.length; column++) {
                Player nextPlayer = currentBoardNode.board[row][column];
                if (nextPlayer == Player.B) break;
                else if (scanForElement != nextPlayer) break;
                else timesOfNodeRepeated++;
            }
            if (timesOfNodeRepeated == 3) return true;
        }
        return false;
    }

    public boolean checkWinOnColumn(BoardNode currentBoardNode) {
        for (int column = 0; column < currentBoardNode.board.length; column++) {
            int timesOfNodeRepeated = 0;
            Player scanForElement = currentBoardNode.board[0][column];
            if (scanForElement == Player.B) break;
            for (int row = 1; row < currentBoardNode.board.length; row++) {
                Player nextPlayer = currentBoardNode.board[row][column];
                if (nextPlayer == Player.B) break;
                else if (scanForElement != nextPlayer) break;
                else timesOfNodeRepeated++;
            }
            if (timesOfNodeRepeated == 3) return true;
        }
        return false;
    }

    public boolean checkWinOnDiagonal(BoardNode currentBoardNode) {
        Player[][] aBoard = currentBoardNode.board;

        if (aBoard[0][0] != Player.B && aBoard[3][3] != Player.B)
            return this.checkWinOnLeftDiagonal(currentBoardNode);
        else if (aBoard[0][3] != Player.B && aBoard[3][0] != Player.B)
            return this.checkWinOnRightDiagonal(currentBoardNode);
        else return false;
    }

    public boolean checkWinOnLeftDiagonal(BoardNode currentBoardNode) {
        Player[][] aBoard = currentBoardNode.board;
        return (aBoard[1][1] == aBoard[0][0] && aBoard[1][1] == aBoard[2][2] && aBoard[3][3] == aBoard[2][2]);
    }

    public boolean checkWinOnRightDiagonal(BoardNode currentBoardNode) {
        Player[][] board = currentBoardNode.board;
        return (board[0][3] == board[1][2] && board[1][2] == board[2][1] && board[2][1] == board[3][0]);
    }

    public int[] checkForAvailableMoves(BoardNode currentBoardNode) {
        int boardSize = currentBoardNode.board.length;
        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                if (currentBoardNode.board[row][column] == Player.B) return this.addMoveToArray(row, column);
            }
        }
        return null;
    }

    public ArrayList<int[]> checkForAvailableMovesOnBoard(BoardNode currentBoardNode) {
        int boardSize = currentBoardNode.board.length;
        ArrayList<int[]> anArrayList = new ArrayList<int[]>();
        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++) {
                if (currentBoardNode.board[row][column] == Player.B) anArrayList.add(this.addMoveToArray(row, column));
            }
        }
        return anArrayList;
    }

    public int[] addMoveToArray(int a, int b) {
        int[] anArray = new int[2];
        anArray[0] = a;
        anArray[1] = b;
        return anArray;
    }

    public Player[][] copyBoard(Player[][] aBoard) {
        int boardSize = aBoard.length;
        Player[][] newBoard = new Player[boardSize][boardSize];
        for (int row = 0; row < boardSize; row++) {
            for (int column = 0; column < boardSize; column++)
                newBoard[row][column] = aBoard[row][column];
        }
        return newBoard;
    }

    public Player[][] updateBoard(BoardNode currentBoardNode, int[] emptySquareOnBoard) {
        Player[][] newBoard = this.copyBoard(currentBoardNode.board);
        newBoard[emptySquareOnBoard[0]][emptySquareOnBoard[1]] = currentBoardNode.nextPlayer;
        return newBoard;
    }

    public BoardNode getSuccessor(BoardNode currentBoardNode, int[] emptySquareOnBoard) {
        if (this.isLeafNode(currentBoardNode) == true) return null;
        else {
            if (currentBoardNode.nextPlayer == Player.X)
                return new BoardNode(this.updateBoard(currentBoardNode, emptySquareOnBoard),
                        currentBoardNode, this.getUtilityOfState(currentBoardNode), currentBoardNode.atDepth + 1, Player.O);
            else return new BoardNode(this.updateBoard(currentBoardNode, emptySquareOnBoard), currentBoardNode,
                    this.getUtilityOfState(currentBoardNode), currentBoardNode.atDepth + 1, Player.X);
        }
    }

    public Vector<BoardNode> getAllSuccessors(BoardNode currentBoardNode) {
        Vector<BoardNode> allSuccessors = new Vector<BoardNode>();
        ArrayList<int[]> allEmptySquareOnBoard = this.checkForAvailableMovesOnBoard(currentBoardNode);
        int numberOfEmptySquareOnBoard = allEmptySquareOnBoard.size();
        for (int i = 0; i < numberOfEmptySquareOnBoard; i++) {
            allSuccessors.add(this.getSuccessor(currentBoardNode, allEmptySquareOnBoard.get(i)));
        }
        return allSuccessors;
    }
}