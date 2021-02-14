package com.test.rovers.model.side;

import com.test.rovers.model.constant.MarsConstants;

public class East extends Direction {

    private static East instance;

    private East() {
        super(MarsConstants.EAST);
    }

    public static East getInstance() {
        if (instance == null) {
            instance = new East();
        }
        return instance;
    }

    @Override
    public int moveX() {
        return 1;
    }

    public Direction goRight() {
        return South.getInstance();
    }

    public Direction goLeft() {
        return North.getInstance();
    }

    @Override
    public String toString() {
        return MarsConstants.EAST;
    }
}
