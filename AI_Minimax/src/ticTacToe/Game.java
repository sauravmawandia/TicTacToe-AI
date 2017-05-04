package ticTacToe;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

/**
 * Created by saura on 4/29/2017.
 */
public class Game {
    public final TicTacToe ticTacToe;
    public final AlphaBeta alphaBeta;
    private final Random random;

    public Game() {
        this.ticTacToe = new TicTacToe();
        alphaBeta = new AlphaBeta();
        random=new Random(System.nanoTime());


    }

    public static void main(String args[]) throws IOException {
        Game game = new Game();
        game.gamePlay();
    }

    public void gamePlay() {
        Node root = ticTacToe.initializeNode();
        int level=this.getLevel();
        int player = this.getPlayer();
        this.printBoard(root.board);
        if (player == 1) {
            root.nextPlayer = Player.O;
            this.humanMove(root,level);
        } else if(level==1) {
            root.nextPlayer = Player.X;
            this.randomComputerMove(root);
        }else if(level==2){
            root.nextPlayer = Player.X;
            this.mediumLevelComputerMove(root);
        }
        else {
            root.nextPlayer = Player.X;
            this.computerMove(root);
        }
    }

    public int[] readHumanInput() {
        int[] humanInput = new int[2];
        try {
            System.out.println("Your turn ");
            System.out.println("Please Enter row and column  of the position separated by (,)comma\n Eg:0,0");
            Scanner in = new Scanner(System.in);
            String input = in.nextLine();
            String[] ar = input.split(",");
            humanInput[0] = Integer.parseInt(ar[0]);
            humanInput[1] = Integer.parseInt(ar[1]);
        } catch (Exception ex) {
            System.out.println("Sorry. Your move is not correct.");
            readHumanInput();
        }

        return humanInput;
    }

    public int[] getCorrectInputFromHumanMove(Node currentNode) {
        int[] humanInput = this.readHumanInput();
        while (humanInput[0] >= 4 || humanInput[0] < 0
                || humanInput[1] >= 4 || humanInput[1] < 0
                || this.checkEmptySquareFromHumanInput(currentNode, humanInput) == false) {
            System.out.println("Sorry. Your move is not correct.");
            humanInput = this.readHumanInput();
        }
        return humanInput;
    }

    public void humanMove(Node currentNode, int level) {
        Node newNode = new Node();
        int[] humanInput = this.getCorrectInputFromHumanMove(currentNode);
        newNode = ticTacToe.getSuccessor(currentNode, humanInput);
        this.printBoard(newNode.board);
        if (ticTacToe.terminalTest(newNode) == true) System.out.println("Congratulations. You won!");
        else if (ticTacToe.isLeafNode(newNode) == true) System.out.println("The game is draw");
        else if(level==1) {
            this.randomComputerMove(newNode);
        } else if(level==2){
            this.mediumLevelComputerMove(newNode);
        }
        else {
            this.computerMove(newNode);
        }
    }
    public void randomComputerMove(Node currentNode){
        Node newNode = ticTacToe.initializeNodeWithInput(currentNode.board);
        Vector<Node> nodes=ticTacToe.getAllSuccessors(newNode);
        int pos=random.nextInt(nodes.size());
        newNode=nodes.get(pos);
        this.printBoard(newNode.board);
        if (ticTacToe.terminalTest(newNode) == true) System.out.println("Computer won!");
        else if (ticTacToe.isLeafNode(newNode) == true) System.out.println("The game is draw");
        else this.humanMove(newNode,1);
    }
    public void mediumLevelComputerMove(Node currentNode){
        Node newNode = ticTacToe.initializeNodeWithInput(currentNode.board);
        Vector<Node> nodes=ticTacToe.getAllSuccessors(newNode);
        if(nodes.size()>11){
            int pos=random.nextInt(nodes.size());
            newNode=nodes.get(pos);
        } else{
            newNode = alphaBeta.nextNodeToMove(newNode);
        }
        this.printBoard(newNode.board);
        if (ticTacToe.terminalTest(newNode) == true) System.out.println("Computer won!");
        else if (ticTacToe.isLeafNode(newNode) == true) System.out.println("The game is draw");
        else this.humanMove(newNode,2);
    }

    public void computerMove(Node currentNode) {
        Node newNode = ticTacToe.initializeNodeWithInput(currentNode.board);
        Vector<Node> nodes=ticTacToe.getAllSuccessors(newNode);
        newNode = alphaBeta.nextNodeToMove(newNode);
        this.printBoard(newNode.board);
        if (ticTacToe.terminalTest(newNode) == true) System.out.println("Computer won!");
        else if (ticTacToe.isLeafNode(newNode) == true) System.out.println("The game is draw");
        else this.humanMove(newNode,3);
    }


    public int getPlayer() {
        Scanner player = new Scanner(System.in);
        System.out.print("Press 1 to play or else enter any number");
        return player.nextInt();
    }

    public boolean checkEmptySquareFromHumanInput(Node currentNode, int[] humanInput) {
        return currentNode.board[humanInput[0]][humanInput[1]] == Player.B;
    }
    public int getLevel(){
        Scanner player = new Scanner(System.in);
        System.out.print("Game Levels\n1.Easy \n2.Medium\n3.Hard\nPress either of the three options");
        int p=player.nextInt();
        if(p==0||p>3){
            System.out.println("Invalid Choice.Please enter again.");
            getLevel();
        }
        return p;
    }

    public void printBoard(Player[][] board) {
        int boardSize = board.length;
        System.out.println("Current Board is: ");
        System.out.print("-----------------");
        System.out.println();
        for (int row = 0; row < boardSize; row++) {
            System.out.print("|");
            for (int column = 0; column < boardSize; column++) {
                if (board[row][column] == Player.B) System.out.print("  " + " |");
                else System.out.print(" " + board[row][column] + " |");
            }
            System.out.println();
            System.out.print("------------------");
            System.out.println();
        }
    }


}
