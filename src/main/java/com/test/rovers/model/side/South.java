package com.test.rovers.model.side;

import com.test.rovers.model.constant.MarsConstants;

public class South extends Direction {

    private static South instance;

    private South() {
        super(MarsConstants.SOUTH);
    }

    public String getCode() {
        return code;
    }

    public static South getInstance() {
        if (instance == null) {
            instance = new South();
        }
        return instance;
    }

    public int moveY() {
        return -1;
    }

    public Direction goRight() {
        return West.getInstance();
    }

    public Direction goLeft() {
        return East.getInstance();
    }

    @Override
    public String toString() {
        return MarsConstants.SOUTH;
    }
}
