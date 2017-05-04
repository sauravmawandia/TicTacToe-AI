package ticTacToe;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

/**
 * Created by saura on 4/19/2017.
 */
public class Game {
    private final GameBoard gameBoard;
    private final AlphaBeta alphaBeta;
    private final Random random;

    public Game() {
        this.gameBoard = new GameBoard();
        alphaBeta = new AlphaBeta();
        random = new Random(System.nanoTime());


    }

    public static void main(String args[]) throws IOException {
        Game game = new Game();
        game.gamePlay();
    }

    public void gamePlay() {
        BoardNode root = gameBoard.initializeAllNode();
        int level = this.getLevel();
        int player = this.getPlayer();
        this.printBoard(root.board);
        if (player == 1) {
            root.nextPlayer = Player.O;
            this.humanMove(root, level);
        } else if (level == 1) {
            root.nextPlayer = Player.X;
            this.randomComputerMove(root);
        } else if (level == 2) {
            root.nextPlayer = Player.X;
            this.mediumLevelComputerMove(root);
        } else {
            root.nextPlayer = Player.X;
            this.computerMove(root);
        }
    }

    public int[] getHumanInput() {
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
            getHumanInput();
        }

        return humanInput;
    }

    private boolean isValidMove(BoardNode currentBoardNode, int[] humanInput) {
        return currentBoardNode.board[humanInput[0]][humanInput[1]] == Player.B;
    }

    public int[] checkIfHumanMoveIsValid(BoardNode currentBoardNode) {
        int[] humanInput = this.getHumanInput();
        while (humanInput[0] >= 4 || humanInput[0] < 0
                || humanInput[1] >= 4 || humanInput[1] < 0
                || this.isValidMove(currentBoardNode, humanInput) == false) {
            System.out.println("Sorry. Your move is not correct.");
            humanInput = this.getHumanInput();
        }
        return humanInput;
    }

    public void humanMove(BoardNode currentBoardNode, int level) {
        BoardNode newBoardNode = new BoardNode();
        int[] humanInput = this.checkIfHumanMoveIsValid(currentBoardNode);
        newBoardNode = gameBoard.getSuccessor(currentBoardNode, humanInput);
        this.printBoard(newBoardNode.board);
        if (gameBoard.terminalTest(newBoardNode) == true) System.out.println("Congratulations. You won!");
        else if (gameBoard.isLeafNode(newBoardNode) == true) System.out.println("The game is draw");
        else if (level == 1) {
            this.randomComputerMove(newBoardNode);
        } else if (level == 2) {
            this.mediumLevelComputerMove(newBoardNode);
        } else {
            this.computerMove(newBoardNode);
        }
    }

    public void randomComputerMove(BoardNode currentBoardNode) {
        BoardNode newBoardNode = gameBoard.initializeNode(currentBoardNode.board);
        Vector<BoardNode> boardNodes = gameBoard.getAllSuccessors(newBoardNode);
        int pos = random.nextInt(boardNodes.size());
        newBoardNode = boardNodes.get(pos);
        this.printBoard(newBoardNode.board);
        if (gameBoard.terminalTest(newBoardNode) == true) System.out.println("Computer won!");
        else if (gameBoard.isLeafNode(newBoardNode) == true) System.out.println("The game is draw");
        else this.humanMove(newBoardNode, 1);
    }

    public void mediumLevelComputerMove(BoardNode currentBoardNode) {
        BoardNode newBoardNode = gameBoard.initializeNode(currentBoardNode.board);
        Vector<BoardNode> boardNodes = gameBoard.getAllSuccessors(newBoardNode);
        if (boardNodes.size() > 11) {
            int pos = random.nextInt(boardNodes.size());
            newBoardNode = boardNodes.get(pos);
        } else {
            newBoardNode = alphaBeta.nextMove(newBoardNode);
        }
        this.printBoard(newBoardNode.board);
        if (gameBoard.terminalTest(newBoardNode) == true) System.out.println("Computer won!");
        else if (gameBoard.isLeafNode(newBoardNode) == true) System.out.println("The game is draw");
        else this.humanMove(newBoardNode, 2);
    }

    public void computerMove(BoardNode currentBoardNode) {
        BoardNode newBoardNode = gameBoard.initializeNode(currentBoardNode.board);
        Vector<BoardNode> boardNodes = gameBoard.getAllSuccessors(newBoardNode);
        newBoardNode = alphaBeta.nextMove(newBoardNode);
        this.printBoard(newBoardNode.board);
        if (gameBoard.terminalTest(newBoardNode) == true) System.out.println("Computer won!");
        else if (gameBoard.isLeafNode(newBoardNode) == true) System.out.println("The game is draw");
        else this.humanMove(newBoardNode, 3);
    }


    public int getPlayer() {
        Scanner player = new Scanner(System.in);
        System.out.print("Press 1 to play or else enter any number");
        return player.nextInt();
    }

    public int getLevel() {
        Scanner player = new Scanner(System.in);
        System.out.print("Game Levels\n1.Easy \n2.Medium\n3.Hard\nPress either of the three options");
        int p = player.nextInt();
        if (p == 0 || p > 3) {
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
