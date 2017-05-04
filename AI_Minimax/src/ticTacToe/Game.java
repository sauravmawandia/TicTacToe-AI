package ticTacToe;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

/**
 * Created by saura on 4/19/2017.
 */
public class Game {

    private static final int INTERMEDIATE_MOVE =11;
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
        System.out.println("Welcome to play Tic Tac Toe game .Play until you win against computer in level 3");
        System.out.println("After a game finishes type exit to exit else a new game starts");
        String choice;
        Scanner sc=new Scanner(System.in);
        do {
            game.gamePlay();
            choice=sc.next();
        }while (choice.toLowerCase().equals("exit"));
    }

    //start the game by selecting level and first player
    public void gamePlay() {
        State root = gameBoard.initializeAllState();
        int level = this.getLevel();
        int player = this.getPlayer();
        this.printBoard(root.board);
        if (player == 1) {
            root.nextPlayer = Player.O;
            this.humanMove(root, level);
        }
        else {
            root.nextPlayer = Player.X;
            this.computerMove(root,level);
        }
    }

    //get human input
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

    //check if move is Valid or not
    public int[] checkIfHumanMoveIsValid(State currentState) {
        int[] humanInput = this.getHumanInput();
        while (humanInput[0] >= 4 || humanInput[0] < 0
                || humanInput[1] >= 4 || humanInput[1] < 0
                || (currentState.board[humanInput[0]][humanInput[1]] != Player.B)){
            System.out.println("Sorry. Your move is not correct.");
            humanInput = this.getHumanInput();
        }
        return humanInput;
    }

    //make human move and call the computer move based on level
    public void humanMove(State currentState, int level) {
        State newState = new State();
        int[] humanInput = this.checkIfHumanMoveIsValid(currentState);
        newState = gameBoard.getImmediateSuccessor(currentState, humanInput);
        this.printBoard(newState.board);
        if (gameBoard.terminalTest(newState)) System.out.println("Congratulations. You won!");
        else if (gameBoard.isTerminalState(newState)) System.out.println("The game is draw");
        else {
            this.computerMove(newState,level);
        }
    }

    //make Computer move based on level Level 1 gives random moves,
    // Level 2 generates few random moves and then calls minimax to generate remaining move
    //level 3 is pure minimax
    public void computerMove(State currentState,int level) {
        State newState = gameBoard.initializeState(currentState.board);
        Vector<State> states = gameBoard.getAllSuccessors(newState);
        if(level==1){
            int pos = random.nextInt(states.size());
            newState = states.get(pos);
        } else if(level==2){
            if (states.size() >= INTERMEDIATE_MOVE) {
                int pos = random.nextInt(states.size());
                newState = states.get(pos);
            } else {
                newState = alphaBeta.nextMove(newState);
            }
        }
        else {
            newState = alphaBeta.nextMove(newState);
        }
        this.printBoard(newState.board);
        if (gameBoard.terminalTest(newState) == true) System.out.println("Computer won!");
        else if (gameBoard.isTerminalState(newState) == true) System.out.println("The game is draw");
        else this.humanMove(newState, 3);
    }

    //get input from user whether he wants to go first or not
    public int getPlayer() {
        Scanner player = new Scanner(System.in);
        System.out.print("Press 1 to play or else enter any number");
        return player.nextInt();
    }

    //get input from user to decide level
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

    //print the board
    public void printBoard(Player[][] board) {
        int length = board.length;
        System.out.println("The cuurent board is: ");
        System.out.print("-----------------");
        System.out.println();
        for (int row = 0; row < length; row++) {
            System.out.print("|");
            for (int column = 0; column < length; column++) {
                if (board[row][column] == Player.B){
                    System.out.print("  " + " |");
                }
                else{
                    System.out.print(" " + board[row][column] + " |");
                }
            }
            System.out.println();
            System.out.print("------------------");
            System.out.println();
        }
    }
}
