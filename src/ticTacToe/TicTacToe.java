package ticTacToe;

import java.io.*;
import java.util.Scanner;

/**
 * Created by saura on 4/15/2017.
 */
public class TicTacToe {
    public static void main(String[] args) throws IOException{
        Player[][] state=new Player[4][4];
        State s=new State(4,Player.X,state);
        s.initialize();
        int count=0;
        AlphaBeta ab=new AlphaBeta();
        while(true){
            System.out.println("Enter x and y of the position");
            Scanner in = new Scanner(System.in);
            String input = in.nextLine();
            String[] ar=input.split(",");
            int x=Integer.parseInt(ar[0]);
            int y=Integer.parseInt(ar[1]);
            Action a=new Action(x,y);
            s.setPlayer(Player.X);
            s.makeMove(a);
            printWinner(s);

            s.setPlayer(Player.O);
            printCurrentState(s.getState());
            System.out.println("Computer turn");
            a=ab.alphaBetaSearch(s);
            s.makeMove(a);
            printCurrentState(s.getState());
            printWinner(s);



            count++;
        }
    }
    public static void printCurrentState(Player[][] players ){
        for(int i=0;i<4;i++){
            for(int j=0;j<4;j++){
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
}
