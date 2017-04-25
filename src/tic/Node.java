package tic;

public class Node {
	Player[][] board = new Player[3][3];
	Player nextPlayer;
	Node parent;
	int heuristicValue;
	int atDepth;

	public Node() {
		for(int i=0;i<board.length;i++){
			for(int j=0;j<board[i].length;j++){
				board[i][j]=Player.B;
			}
		}
	}
	public Node(Player[][] board, Node parent, int heuristicValue, int atDepth, Player nextPlayer) {
		this.board = board;
		this.nextPlayer = nextPlayer;
		this.parent = parent;
		this.heuristicValue = heuristicValue;
		this.atDepth = atDepth;
	}
	public Node(Player[][] board) {
		this(board, null, 0, 0, null);
	}
	
}
