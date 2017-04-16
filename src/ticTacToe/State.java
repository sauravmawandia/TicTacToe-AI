package ticTacToe;

import java.util.ArrayList;


/**
 * Created by saura on 4/15/2017.
 */
public class State {
    private final Player state[][];
    private final int size;
    private final int x;
    private final int y;
    private final Player player;
    private final ArrayList<Action> actions;

    public State(int size, Player p, Player[][] state) {
        this.size = size;
        this.x = 0;
        this.y = 0;
        this.state = state;
        this.player = p;
        actions = new ArrayList<>();
    }
    public void initialize(){
        for(int i=0;i<size;i++){
            for(int j=0;j<size;j++){
                this.state[i][j]=Player.Blank;
            }
        }
    }

    public Player getPlayerInPos(int x, int y) {
        return state[x][y];
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSize() {
        return size;
    }

    public Player getPlayer() {
        return player;
    }

    public Player[][] getState() {
        return state;
    }

    public void makeMove(int x, int y, Player p) {
        this.state[x][y]=p;
    }

    public ArrayList<Action> getAvailableActions() {
        ArrayList<Action> actions = new ArrayList<>();
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; j++) {
                if (state[i][j] == Player.Blank) {
                    actions.add(new Action(i, j));
                }
            }
        }
        return actions;
    }
}