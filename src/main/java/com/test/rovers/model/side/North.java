package com.test.rovers.model.side;
import com.test.rovers.model.constant.MarsConstants;

public class North extends Direction {

    private static North instance;

    private North() {
        super(MarsConstants.NORTH);
    }

    public static North getInstance() {
        if (instance == null) {
            instance = new North();
        }
        return instance;
    }

    public String getCode() {
        return code;
    }

    public int moveY() {
        return 1;
    }

    public Direction goRight() {
        return East.getInstance();
    }

    public Direction goLeft() {
        return West.getInstance();
    }

    public String toString() {
        return MarsConstants.NORTH;
    }
}
