package com.example.bocciatest.model;

public class Ball {

    private int id;
    private int x;
    private int y;
    private String colour;

    public Ball(int id, int x, int y, String colour) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.colour = colour;
    }

    public int getId() {
        return id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getColour() {
        return colour;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }
}
