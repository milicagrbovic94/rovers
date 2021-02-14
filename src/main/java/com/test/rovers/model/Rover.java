package com.test.rovers.model;

public class Rover {
    public static int index = 1;
    private int id;
    private Position position;

    public Rover(Position position) {
        this.id = index++;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void goLeft() {
        position.goLeft();

    }

    public void goRight() {
        position.goRight();

    }


    public void updateX(int newX) {
        position.getPoint().setX(newX);
    }

    public void updateY(int newY) {
        position.getPoint().setY(newY);
    }

}
