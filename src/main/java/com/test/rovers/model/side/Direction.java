package com.test.rovers.model.side;

public abstract class Direction {
    protected String code;
    public abstract Direction goRight();
    public abstract Direction goLeft();
    public int moveX() {
        return 0;
    }
    public int moveY() {
        return 0;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Direction(String code) {
        this.code = code;
    }
}
