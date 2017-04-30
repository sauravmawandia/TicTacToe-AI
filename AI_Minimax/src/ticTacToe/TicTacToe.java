package ticTacToe;

/**
 * Created by saura on 4/29/2017.
 */
import java.io.*;
import java.util.*;


public class TicTacToe {

    public static int INFINITY = 100;
    public TicTacToe() {}
    public Node initializeNode() {
        return new Node();
    }
    public Node initializeNodeWithInput(Player[][] board) {
        Node root = new Node();
        root.board = board;
        root.nextPlayer = Player.X;
        return root;
    }

    // Output game board
    public void outputBoard(Player[][] board) {
        int boardSize = board.length;
        System.out.println("Current Board is: ");
        System.out.print("-------------");
        System.out.println();
        for(int row = 0; row < boardSize; row++) {
            System.out.print("|");
            for(int column = 0; column < boardSize; column++) {
                if(board[row][column] == Player.B) System.out.print("  "+" |");
                else System.out.print(" "+board[row][column] +" |");
            }
            System.out.println();
            System.out.print("-------------");
            System.out.println();
        }
    }
    //Check leaf node
    public boolean isLeafNode(Node currentNode) {
        return this.checkWin(currentNode) || (this.scanEmptySquareOnBoard(currentNode)==null);
    }
    // Only evaluate heuristicValue at leafNode.
    public int evaluateHeuristicValue(Node currentNode) {
        if(currentNode.nextPlayer == Player.X && this.checkWin(currentNode)==true) return -1;
        if(currentNode.nextPlayer == Player.O && this.checkWin(currentNode)==true) return 1;
        return 0;
    }
    // Checking winning node
    public boolean checkWin(Node currentNode) {
        return (this.checkWinOnRow(currentNode)
                ||this.checkWinOnColumn(currentNode)
                ||this.checkWinOnDiagonal(currentNode));
    }
    public boolean checkWinOnRow(Node currentNode) {
        for(int row = 0; row < currentNode.board.length; row++) {
            int timesOfNodeRepeated = 0;
            Player scanForElement = currentNode.board[row][0];
            if (scanForElement == Player.B) break;
            for(int column = 1; column < currentNode.board.length; column ++) {
                Player nextPlayer = currentNode.board[row][column];
                if(nextPlayer == Player.B) break;
                else if (scanForElement!=nextPlayer) break;
                else timesOfNodeRepeated++;
            }
            if(timesOfNodeRepeated == 2) return true;
        }
        return false;
    }
    public boolean checkWinOnColumn(Node currentNode) {
        for(int column = 0; column < currentNode.board.length; column++) {
            int timesOfNodeRepeated = 0;
            Player scanForElement = currentNode.board[0][column];
            if (scanForElement == Player.B) break;
            for(int row = 1; row < currentNode.board.length; row ++) {
                Player nextPlayer = currentNode.board[row][column];
                if(nextPlayer == Player.B) break;
                else if (scanForElement!=nextPlayer) break;
                else timesOfNodeRepeated++;
            }
            if(timesOfNodeRepeated == 2) return true;
        }
        return false;
    }
    public boolean checkWinOnDiagonal(Node currentNode) {
        Player[][] aBoard = currentNode.board;
        if(aBoard[1][1] != Player.B) {
            if(aBoard[0][0] != Player.B && aBoard[2][2] != Player.B) return this.checkWinOnLeftDiagonal(currentNode);
            else if(aBoard[0][2] != Player.B && aBoard[2][0] != Player.B) return this.checkWinOnRightDiagonal(currentNode);
            else return false;
        }
        else return false;
    }
    public boolean checkWinOnLeftDiagonal(Node currentNode) {
        Player[][] aBoard = currentNode.board;
        return (aBoard[1][1]==aBoard[0][0] && aBoard[1][1]==aBoard[2][2]);
    }
    public boolean checkWinOnRightDiagonal(Node currentNode) {
        Player[][] aBoard = currentNode.board;
        return (aBoard[1][1]==aBoard[0][2] && aBoard[1][1]==aBoard[2][0]);
    }
    public int[] scanEmptySquareOnBoard(Node currentNode) {
        int boardSize = currentNode.board.length;
        for(int row = 0; row < boardSize; row++) {
            for(int column = 0; column < boardSize; column++) {
                if(currentNode.board[row][column] == Player.B) return this.addValueToArray(row, column);
            }
        }
        return null;
    }
    public ArrayList<int[]> scanAllEmptySquareOnBoard(Node currentNode) {
        int boardSize = currentNode.board.length;
        ArrayList<int[]> anArrayList = new ArrayList<int[]>();
        for(int row = 0; row < boardSize; row++) {
            for(int column = 0; column < boardSize; column++) {
                if(currentNode.board[row][column] == Player.B) anArrayList.add(this.addValueToArray(row, column));
            }
        }
        return anArrayList;
    }
    public int[] addValueToArray(int aNumber, int anotherNumber) {
        int[] anArray = new int[2];
        anArray[0] = aNumber;
        anArray[1] = anotherNumber;
        return anArray;
    }
    public Player[][] copyBoard(Player[][] aBoard) {
        int boardSize = aBoard.length;
        Player[][] newBoard = new Player[boardSize][boardSize];
        for(int row = 0; row < boardSize; row++) {
            for(int column = 0; column < boardSize; column++)
                newBoard[row][column] = aBoard[row][column];
        }
        return newBoard;
    }
    public Player[][] updateBoard(Node currentNode, int[] emptySquareOnBoard) {
        Player[][] newBoard = this.copyBoard(currentNode.board);
        newBoard[emptySquareOnBoard[0]][emptySquareOnBoard[1]] = currentNode.nextPlayer;
        return newBoard;
    }
    public Node getSuccessor(Node currentNode, int[] emptySquareOnBoard) {
        if(this.isLeafNode(currentNode) == true) return null;
        else {
            if(currentNode.nextPlayer==Player.X) return new Node(this.updateBoard(currentNode,emptySquareOnBoard),
                    currentNode,this.evaluateHeuristicValue(currentNode),currentNode.atDepth+1,Player.O);
            else return new Node(this.updateBoard(currentNode,emptySquareOnBoard),currentNode,
                    this.evaluateHeuristicValue(currentNode),currentNode.atDepth+1,Player.X);
        }
    }
    public Vector<Node> getAllSuccessors(Node currentNode) {
        Vector<Node> allSuccessors = new Vector<Node>();
        ArrayList<int[]> allEmptySquareOnBoard = this.scanAllEmptySquareOnBoard(currentNode);
        int numberOfEmptySquareOnBoard = allEmptySquareOnBoard.size();
        for(int i = 0; i < numberOfEmptySquareOnBoard; i++) {
            allSuccessors.add(this.getSuccessor(currentNode, allEmptySquareOnBoard.get(i)));
        }
        return allSuccessors;
    }
    /*
     * Get max/min.
     */
    public int getMaxTwoIntegers(int anInteger, int anotherInteger) {
        if(anInteger < anotherInteger) return anotherInteger;
        else return anInteger;
    }
    public int getMinTwoIntegers(int anInteger, int anotherInteger) {
        if(anInteger > anotherInteger) return anotherInteger;
        else return anInteger;
    }
    public Node getMinNodeInList(Vector<Node> aVectorNode) {
        Node minNode = aVectorNode.get(0);
        int listSize = aVectorNode.size();
        for(int index = 0; index < listSize; index++)
            if(minNode.heuristicValue > aVectorNode.get(index).heuristicValue) minNode = aVectorNode.get(index);
        return minNode;
    }
    public Node getMaxNodeInList(Vector<Node> aVectorNode) {
        Node maxNode = aVectorNode.get(0);
        int listSize = aVectorNode.size();
        for(int index = 0; index < listSize; index++)
            if(maxNode.heuristicValue < aVectorNode.get(index).heuristicValue) maxNode = aVectorNode.get(index);
        return maxNode;
    }
    /*
     * Minimax
     */


    /*
     * Alpha-beta pruning.
     */


}