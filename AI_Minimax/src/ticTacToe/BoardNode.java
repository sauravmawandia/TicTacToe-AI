package ticTacToe;

/**
 * Created by saura on 4/19/2017.
 */
public class BoardNode {
    Player[][] board = new Player[4][4];
    Player nextPlayer;
    BoardNode parent;
    int heuristicValue;
    int atDepth;

    public BoardNode() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = Player.B;
            }
        }
    }

    public BoardNode(Player[][] board, BoardNode parent, int heuristicValue, int atDepth, Player nextPlayer) {
        this.board = board;
        this.nextPlayer = nextPlayer;
        this.parent = parent;
        this.heuristicValue = heuristicValue;
        this.atDepth = atDepth;
    }

    public BoardNode(Player[][] board) {
        this(board, null, 0, 0, null);
    }

}
