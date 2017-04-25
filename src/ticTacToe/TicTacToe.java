package ticTacToe;

import java.io.*;
import java.util.Scanner;
import java.util.SplittableRandom;

/**
 * Created by saura on 4/15/2017.
 */
public class TicTacToe {
    public static void main(String[] args) throws IOException{
        System.out.print("Enter size");
        Scanner sc=new Scanner(System.in);
        int size=sc.nextInt();
        State s=new State(size,Player.X,Player.O);
        printCurrentState(s.getState());
        AlphaBeta ab=new AlphaBeta();
        while(true){
            Action a;
            try {
                System.out.println("Your turn ");
                System.out.println("Please Enter x and y of the position" + s.getMaxPlayer());
                Scanner in = new Scanner(System.in);
                String input = in.nextLine();
                String[] ar = input.split(",");
                int x = Integer.parseInt(ar[0]);
                int y = Integer.parseInt(ar[1]);

                a = new Action(x, y);
                s.makeMove(a, s.getMinPlayer());
            }
            catch (IllegalArgumentException e){
                System.out.print("Player already in pos");
                continue;
            }
            printWinner(s);
            printCurrentState(s.getState());
            System.out.println("Computer turn");
            a=ab.alphaBetaSearch(s);
            if(a==null){
                printWinner(s);
            }
            s.makeMove(a,s.getMaxPlayer());
            printWinner(s);
            printCurrentState(s.getState());
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
    }
