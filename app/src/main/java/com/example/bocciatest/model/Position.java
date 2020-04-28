package com.example.bocciatest.model;

public class Position {

    private int id;
    private float xpos;
    private float ypos;


    public Position(int id, float xpos, float ypos) {
        this.id = id;
        this.xpos = xpos;
        this.ypos = ypos;
    }

    public int getId() {
        return id;
    }

    public float getXpos() {
        return xpos;
    }

    public void setXpos(float xpos) {
        this.xpos = xpos;
    }

    public float getYpos() {
        return ypos;
    }

    public void setYpos(float ypos) {
        this.ypos = ypos;
    }
}
