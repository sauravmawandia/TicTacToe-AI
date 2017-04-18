package ticTacToe;

import java.io.*;
import java.util.Scanner;
import java.util.SplittableRandom;

/**
 * Created by saura on 4/15/2017.
 */
public class TicTacToe {
    public static void main(String[] args) throws IOException{
        State s=new State(3,Player.X,Player.O);
        int count=0;
        AlphaBeta ab=new AlphaBeta();
        while(true){
            System.out.println("Enter x and y of the position"+s.getMaxPlayer());
            Scanner in = new Scanner(System.in);
            String input = in.nextLine();
            String[] ar=input.split(",");
            int x=Integer.parseInt(ar[0]);
            int y=Integer.parseInt(ar[1]);

            Action a=new Action(x,y);
            s.makeMove(a,s.getMaxPlayer());
            printWinner(s);
            printCurrentState(s.getState());
            System.out.println("Computer turn");
            State newState=new State(3,Player.X,Player.O,copyArray(s.getState()));
            a=ab.alphaBetaSearch(newState);
            s.makeMove(a,s.getMinPlayer());
            printCurrentState(s.getState());
            printWinner(s);



            count++;
        }
    }
    public static void printCurrentState(Player[][] players ){
        for(int i=0;i<players.length;i++){
            for(int j=0;j<players[i].length;j++){
                System.out.print(players[i][j]+" | ");
            }
            System.out.println();
        }
    }
    private static void printWinner(State s){
        if(TerminalTest.terminalOrNot(s)){
            if(TerminalTest.hasWon(Player.X,s)){
                System.out.println("Player X Won");
            }else if(TerminalTest.hasWon(Player.O,s)){
                System.out.println("Player O Won");
            } else {
                System.out.println("It's a draw");
            }
            System.exit(0);
        }
    }
    private static Player[][] copyArray(Player[][] board){
        Player a[][]=new Player[board.length][board[0].length];
        for(int i=0;i<a.length;i++){
            for(int j=0;j<a.length;j++){
                a[i][j]=board[i][j];
            }
        }
        return a;
    }
}
