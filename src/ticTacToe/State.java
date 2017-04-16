package ticTacToe;

import java.util.ArrayList;


/**
 * Created by saura on 4/15/2017.
 */
public class State {
    private final Player state[][];
    private final int size;
    private Player player;
    private int x;
    private int y;
    private final ArrayList<Action> actions;

    public State(int size, Player player, Player[][] state) {
        this.size = size;
        this.state = state;
        this.player = player;
        actions = new ArrayList<>();
    }
    public void initialize(){
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                this.state[i][j]=Player.B;
            }
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
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

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void makeMove(Action action) {
        if(this.state[action.getX()][action.getY()]!=Player.B){
            throw new IllegalArgumentException("Invalid move");
        }
        this.state[action.getX()][action.getY()]=player;
        x=action.getX();
        y=action.getY();
    }
    public void resetMove(Action action) {
        this.state[action.getX()][action.getY()]=Player.B;
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