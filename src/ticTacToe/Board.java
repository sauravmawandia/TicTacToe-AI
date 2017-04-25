package ticTacToe;

import java.util.ArrayList;

/**
 * Created by saura on 4/29/2017.
 */
public class Board {
    private Player[][] board;
    private static final int size=3;
    private Player player=Player.X;
    public int moveCount=0;
    public Board(){
        this.board = new Player[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.board[i][j] = Player.B;
            }
        }
    }
    public Board(Player[][] board){
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; j++) {
                this.board[i][j] = board[i][j];
            }
        }
    }
    public Board(Board board){
        this.moveCount=board.moveCount;
        this.player=board.player;
        this.board=copyArray(board.board);
    }
    public Player[][] copyArray(Player[][] board){
        Player[][] newBoard=new Player[size][size];
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; j++) {
                newBoard[i][j] = board[i][j];
            }
        }
        return newBoard;
    }
    public void copy(Board board){
        this.moveCount=board.moveCount;
        this.player=board.player;
        this.board=copyArray(board.board);
    }
    public boolean setMove(Action a){
        if (board[a.getX()][a.getY()]!=Player.B){
            return false;
        }
        player=player==Player.X?Player.O:Player.X;
        board[a.getX()][a.getY()]=player;
        moveCount++;
        return true;
    }
    public boolean resrtMove(Action a){
        player=player==Player.X?Player.O:Player.X;
        board[a.getX()][a.getY()]=Player.B;
        moveCount++;
        return true;
    }
    public  int getUtility() {
        if (hasWon(Player.X)) {
            return 1000;
        } else if (hasWon(Player.O)) {
            return -1000;
        } else if(isDraw()){
            return 0;
        }
        else {
            return Integer.MIN_VALUE;
        }
    }
    public ArrayList<Action> getAvailableActions() {
        ArrayList<Action> actions = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == Player.B) {
                    actions.add(new Action(i, j));
                }
            }
        }
        return actions;
    }

    public  boolean hasWon(Player player) {
        if(size==3) {
            if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == player) || (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == player)) {
                return true;
            }
            for (int i = 0; i < 3; ++i) {
                if ((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == player)
                        || (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == player)) {
                    return true;
                }
            }
        }
        else if(size==4){
            if ((board[0][0] == board[1][1] && board[0][0] == board[2][2]&& board[0][0]==board[3][3] && board[0][0] == player) ||
                    (board[0][3] == board[1][2] && board[0][3] == board[2][1] && board[0][3] == board[3][0]&& board[0][3] == player)) {
                return true;
            }
            for (int i = 0; i < size; ++i) {
                if ((board[i][0] == board[i][1] && board[i][0] == board[i][2]&&board[i][0]==board[i][3] && board[i][0] == player)
                        || (board[0][i] == board[1][i] && board[0][i] == board[2][i]&& board[0][i]==board[3][i] && board[0][i] == player)) {
                    return true;
                }
            }
        }
        return false;
    }
    public  boolean isDraw(){
        boolean flag=true;
        outerloop:
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                if(board[i][j]==Player.B){
                    flag=false;
                    break outerloop;
                }
            }
        }
        return flag;
    }
    public void print() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (j == 0) {
                    System.out.print("|");
                }
                if (board[i][j] == Player.B) {
                    System.out.print("  |");
                } else
                    System.out.print(board[i][j] + " |");
            }
            //System.out.println();
            System.out.println();
            if (i != board.length - 1)
                System.out.println(" -- -- --");
        }
        System.out.println();
    }

}
