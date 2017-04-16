package ticTacToe;

/**
 * Created by saura on 4/15/2017.
 */
public class ActionWithValue {
    private final Action a;
    private final int v;

    public ActionWithValue(Action a, int v) {
        this.a = a;
        this.v = v;
    }

    public int getV() {
        return v;
    }

    public Action getA() {

        return a;
    }
}
