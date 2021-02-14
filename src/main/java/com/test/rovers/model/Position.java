package com.test.rovers.model;

import com.test.rovers.model.side.Direction;

public class Position implements Cloneable{
    private Point point;
    private Direction direction;

    public Position(Point point, Direction direction) {
        this.point = point;
        this.direction = direction;
    }

    public void goLeft() {
        this.setDirection(direction.goLeft());
    }

    public void goRight() {
        this.setDirection(direction.goRight());
    }

    public int calculateNewX() {
        return getPoint().getX() + getDirection().moveX();
    }

    public int calculateNewY() {
        return getPoint().getY() + getDirection().moveY();
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    @Override
    public Object clone() {
        return new Position(new Point(this.getPoint().getX(), this.getPoint().getY()), this.direction);
    }
}
