package com.example.stickhero;

//Design Pattern Factory is implemented in which the RewardFactory class is the factory class
public class RewardFactory {
    public static com.example.stickhero.Reward createReward(String type) {
        if (type.equals("Cherry")) {
            return new com.example.stickhero.Cherry();
        } else if (type.equals("Obstacle")) {
            return new com.example.stickhero.Obstacle();
        } else {
            return null;
        }
    }
}