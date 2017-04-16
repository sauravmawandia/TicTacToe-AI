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


    public static int getUtility(State s) {
        if (hasWon(Player.X,s)) {
            return 1000;
        } else if (hasWon(Player.O,s)) {
            return -1000;
        } else {
            return 0;
        }
    }

    public static boolean hasWon(Player player ,State s) {
        int size=s.getSize();
        //check column for player win
        for (int i = 0; i < size; i++) {
            if (s.getPlayerInPos(s.getX(),i) != player)
                break;
            if (i == size - 1) {
                return true;
            }
        }

        //check row for player win
        for (int i = 0; i < size; i++) {
            if (s.getPlayerInPos(i,s.getY()) != player)
                break;
            if (i == size - 1) {
                return true;
            }
        }

        //check diagonal for player win
        if (s.getX() == s.getY()) {
            for (int i = 0; i < size; i++) {
                if (s.getPlayerInPos(i,i) != player)
                    break;
                if (i == size - 1) {
                    return true;
                }
            }
        }

        //check anti diagonal
        if (s.getX() + s.getY() == size - 1) {
            for (int i = 0; i < size; i++) {
                if (s.getPlayerInPos(i,(size - 1) - i) != player)
                    break;
                if (i == size - 1) {
                    return true;
                }
            }
        }
        return false;
    }
}
