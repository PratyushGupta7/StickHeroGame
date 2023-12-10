package com.example.stickhero;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;
//The Character class implements Singeton Design Pattern
public class Character  {
    public ImageView getCurrentImageView() {
        return currentImageView;
    }

    public void setCurrentImageView(ImageView currentImageView) {
        this.currentImageView = currentImageView;
    }
    private static Character instance = null;
    private ImageView currentImageView;
    private double x;
    private double y;
    private double width;
    private double height;

    public void setImage(Image image) {
        this.image = image;
    }

    private Image image;

    public Image getImage() {
        return image;
    }
    private ImageView Charcterset;

    public void setCharcterset(ImageView charcterset) {
        Charcterset = charcterset;
    }

    public ImageView getCharcterset() {
        return Charcterset;
    }

    protected List<Reward> rewards = new ArrayList<>();;

    private Character() {
        // Private constructor to prevent instantiation
    }

    public static Character getInstance() {
        if (instance == null) {
            instance = new Character();
        }
        return instance;
    }
    protected String name;
    protected double speed;




    public void move() {

    }

    public void jump() {

    }

    public void flip() {

    }

    public void draw() {

    }





//    public boolean isOnPillar() {
//        // Implement logic to check if the character is on a pillar
//        // For example, check collision with pillars
//        for (Pillar pillar : pillars) {
//            if (collidesWith(pillar) && pillar.isLanded(this)) {
//                return true;
//            }
//        }
//        return false;
//    }


    public void revive() {

        int totalCherries = 0;
        for (Reward reward : rewards) {
            if (reward.isCollected()) {
                totalCherries += reward.getValue();
            }
        }
        if (totalCherries >= 3) {

            int cherriesToRevive = 3;
            for (Reward reward : rewards) {
                if (reward.isCollected()) {
                    reward.setCollected(false);
                    cherriesToRevive--;
                    if (cherriesToRevive == 0) {
                        break;
                    }
                }
            }

        }
    }



}