package com.example.stickhero;

import javafx.scene.image.ImageView;

public class Obstacle extends com.example.stickhero.Reward {
    @Override
    public ImageView getCurrentObstacle() {
        return currentObstacle;
    }
@Override
    public void setCurrentObstacle(ImageView currentObstacle) {
        this.currentObstacle = currentObstacle;
    }


}