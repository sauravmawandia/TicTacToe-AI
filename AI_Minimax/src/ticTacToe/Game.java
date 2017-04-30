package ticTacToe;

import java.io.IOException;
import java.util.Scanner;

/**
 * Created by saura on 4/29/2017.
 */
public class Game {
    public final TicTacToe ticTacToe;
    public final AlphaBeta alphaBeta;
    public Game(){
        this.ticTacToe=new TicTacToe();
        alphaBeta=new AlphaBeta();


    }
    public static void main(String args[]) throws IOException {
        Game game = new Game();
        game.gamePlay();
    }
    public void gamePlay() {
        Node root = ticTacToe.initializeNode();
        this.outputBoard(root.board);
        int player = this.getPlayer();
        if(player == 1) { root.nextPlayer = Player.O; this.humanMove(root); }
        else { root.nextPlayer = Player.X; this.machineMove(root); }
    }
    public int[] modifyHumanInput(int[] humanInput) {
        int[] returnFromHumanInput = new int[2];
        returnFromHumanInput[0] = humanInput[0]-1;
        returnFromHumanInput[1] = humanInput[1]-1;
        return returnFromHumanInput;
    }
    public int[] readHumanInput() {
        int[] humanInput = new int[2];
        Scanner row = new Scanner(System.in); Scanner column = new Scanner(System.in);
        System.out.print("Enter which row you want to check: ");
        humanInput[0] = row.nextInt();
        System.out.print("Please enter column you want to check: ");
        humanInput[1] = column.nextInt();
        return this.modifyHumanInput(humanInput);
    }
    public int[] getCorrectInputFromHumanMove(Node currentNode) {
        int[] humanInput = this.readHumanInput();
        while(humanInput[0] >= 4 || humanInput[0] < 0
                || humanInput[1] >= 4 || humanInput[1] < 0
                || this.checkEmptySquareFromHumanInput(currentNode, humanInput)==false) {
            System.out.println("Sorry. Your move is not correct.");
            humanInput = this.readHumanInput();
        }
        return humanInput;
    }
    public void humanMove(Node currentNode) {
        Node newNode = new Node();
        int[] humanInput = this.getCorrectInputFromHumanMove(currentNode);
        newNode = ticTacToe.getSuccessor(currentNode, humanInput);
        this.outputBoard(newNode.board);
        if(ticTacToe.terminalTest(newNode)==true) System.out.println("Congratulations. You won!");
        else if(ticTacToe.isLeafNode(newNode) == true)  System.out.println("The game is draw");
        else this.machineMove(newNode);
    }
    public void machineMove(Node currentNode) {
        Node newNode = ticTacToe.initializeNodeWithInput(currentNode.board);
        newNode = alphaBeta.nextNodeToMove(newNode);
        this.outputBoard(newNode.board);
        if(ticTacToe.terminalTest(newNode) == true) System.out.println("Computer won!");
        else if(ticTacToe.isLeafNode(newNode) == true)  System.out.println("The game is draw");
        else this.humanMove(newNode);
    }

    public int getPlayer(){
        Scanner player = new Scanner(System.in);
        System.out.print("Do you want to play first? Yes (1). No (0): ");
        return player.nextInt();
    }

    public boolean checkEmptySquareFromHumanInput(Node currentNode, int[] humanInput) {
        return currentNode.board[humanInput[0]][humanInput[1]] == Player.B;
    }
    // Output game board
    public void outputBoard(Player[][] board) {
        int boardSize = board.length;
        System.out.println("Current Board is: ");
        System.out.print("-----------------");
        System.out.println();
        for(int row = 0; row < boardSize; row++) {
            System.out.print("|");
            for(int column = 0; column < boardSize; column++) {
                if(board[row][column] == Player.B) System.out.print("  "+" |");
                else System.out.print(" "+board[row][column] +" |");
            }
            System.out.println();
            System.out.print("------------------");
            System.out.println();
        }
    }


}
