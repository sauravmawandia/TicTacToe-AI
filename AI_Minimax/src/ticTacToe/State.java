package ticTacToe;

/**
 * Created by saura on 4/19/2017.
 */
public class State {
    public Player[][] board ;
    public Player nextPlayer;
    public State parent;
    public int vValue;
    public int depth;

    //three overloaded constructors based on purpose
    //default constructor when a user needs a new state
    public State() {
        board = new Player[4][4];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = Player.B;
            }
        }
    }
    //construct a copy of state
    public State(Player[][] board, State parent, int vValue, int depth, Player nextPlayer) {
        this.board = board;
        this.nextPlayer = nextPlayer;
        this.parent = parent;
        this.vValue = vValue;
        this.depth = depth;
    }

    // a state with no parent
    public State(Player[][] board) {
        this(board, null, 0, 0, null);
    }

}
