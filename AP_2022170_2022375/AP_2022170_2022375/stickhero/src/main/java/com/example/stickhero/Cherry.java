package com.example.stickhero;

import javafx.scene.image.ImageView;

public class Cherry extends Reward {
    @Override
    public ImageView getCurrentCherry() {
        return currentCherry;
    }
@Override
    public void setCurrentCherry(ImageView currentCherry) {
        this.currentCherry = currentCherry;
    }


    // Cherry-specific methods and attributes...
}