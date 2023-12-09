package com.example.stickhero;

import javafx.scene.image.ImageView;

public abstract class Reward  {
    private double x;
    private Character character;
    private double y;
    private int value;
    private String image;
    private boolean collected;
    protected ImageView currentCherry;
    protected ImageView currentObstacle;
    // Apple-specific methods and attributes...

    public void draw() {

    }
    // A reward can be collected by a character
    public void collect(Character character) {
        collected = true;
        character.rewards.add(this);
    }

    // A reward can check if it is collected
    public boolean isCollected() {
        return collected;
    }


    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    public int getValue() {
        return value;
    }

    public ImageView getCurrentCherry(){
        return null;
    };

    public void setCurrentCherry(ImageView currentCherry){};

    public ImageView getCurrentObstacle(){
        return null;
    };

    public  void setCurrentObstacle(ImageView currentObstacle)
    {};
}