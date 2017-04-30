package ticTacToe;

/**
 * Created by saura on 4/15/2017.
 */
public class ActionWithValue {
    private final Action action;
    private final int value;

    public ActionWithValue(Action action, int value) {
        this.action = action;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public Action getAction() {
        return action;
    }
}
