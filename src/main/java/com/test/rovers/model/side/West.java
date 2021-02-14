package com.test.rovers.model.side;

import com.test.rovers.model.constant.MarsConstants;

public class West extends Direction {

    private static West instance;

    private West() {
        super(MarsConstants.WEST);
    }

    public String getCode() {
        return code;
    }

    public static West getInstance() {
        if (instance == null) {
            instance = new West();
        }
        return instance;
    }

    public int moveX() {
        return -1;
    }

    public Direction goRight() {
        return North.getInstance();
    }

    public Direction goLeft() {
        return South.getInstance();
    }

    @Override
    public String toString() {
        return MarsConstants.WEST;
    }
}
