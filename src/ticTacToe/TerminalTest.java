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
        Player[][] board=s.getState();
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
}
