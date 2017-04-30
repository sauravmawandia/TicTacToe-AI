package ticTacToe;

/**
 * Created by saura on 4/15/2017.
 */
public class TerminalTest {
    public  boolean terminalOrNot(Node n){
        boolean flag=true;
        outerloop:
        for(int i=0;i<n.board.length;i++){
            for(int j=0;j<n.board.length;j++){
                if(n.board[i][j]==Player.B){
                    flag=false;
                    break outerloop;
                }
            }
        }
        return flag;
    }

    public int getUtility(Node n) {
        if (hasWon(Player.X,n)) {
            return -1;
        } else if (hasWon(Player.O,n)) {
            return 1;
        } else {
            return 0;
        }
    }
    public  boolean hasWon(Player player,Node n) {
        int size=n.board.length;
        Player[][] board=n.board;
        if(size==3) {
            if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == player)
                    || (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == player)) {

                return true;
            }
            for (int i = 0; i < 3; i++) {
                if ((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == player)) {
                    return true;
                }
                else if (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == player){
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
