package ticTacToe;

import java.util.ArrayList;


/**
 * Created by saura on 4/15/2017.
 */
public class State {
    private final Player state[][];
    private final int size;
    private final Player maxPlayer;
    private final Player minPlayer;
    private final ArrayList<Action> actions;

    public State(int size, Player maxPlayer, Player minPlayer) {
        this.size = size;
        this.state = new Player[size][size];
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                this.state[i][j]=Player.B;
            }
        }
        this.maxPlayer = maxPlayer;
        actions = new ArrayList<>();
        this.minPlayer=minPlayer;
    }
    public State(int size, Player maxPlayer, Player minPlayer,Player[][] board) {
        this.size = size;
        this.state=board;
        this.maxPlayer = maxPlayer;
        actions = new ArrayList<>();
        this.minPlayer=minPlayer;
    }

    public Player getMaxPlayer() {
        return maxPlayer;
    }

    public Player getMinPlayer() {
        return minPlayer;
    }

    public Player getPlayerInPos(int x, int y) {
        return state[x][y];
    }

    public Player[][] getState() {
        return state;
    }


    public int getSize() {
        return size;
    }


    public void makeMove(Action action,Player p) {
        this.state[action.getX()][action.getY()] = p;
    }
    public ArrayList<Action> getAvailableActions() {
        ArrayList<Action> actions = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; j++) {
                if (state[i][j] == Player.B) {
                    actions.add(new Action(i, j));
                }
            }
        }
        return actions;
    }
}