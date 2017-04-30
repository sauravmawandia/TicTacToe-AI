package ticTacToe;

/**
 * Created by saura on 4/15/2017.
 */
public class TerminalTest {
    public static boolean terminalOrNot(State s){
        boolean flag=true;
        outerloop:
        for(int i=0;i<s.getSize();i++){
            for(int j=0;j<s.getSize();j++){
                if(s.getPlayerInPos(i,j)==Player.B){
                    flag=false;
                    break outerloop;
                }
            }
        }
        return hasWon(Player.X,s)||hasWon(Player.O,s)||flag;
    }



}
