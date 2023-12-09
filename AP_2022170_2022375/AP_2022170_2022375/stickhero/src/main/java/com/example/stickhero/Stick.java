package com.example.stickhero;


import javafx.scene.shape.Rectangle;

public class Stick {
    private double x;
    private double y;
    private double length;
    private double angle;
    private String color;

    public void draw() {

    }

    public void extend(Double stickExtensionRate) {
        this.length = length + stickExtensionRate;

        // Ensure that the new length is within a certain range if needed
        // For example, you might want to set a maximum length for the stick
        // double maxLength = 100; // Adjust as needed
        // newLength = Math.min(newLength, maxLength);

        // Update the stick's length
        }

    public Stick() {
        this.length = 0;
        this.angle = 90; // Assuming the angle is in degrees

        // Initialize x, y, and color
        this.x = 0; // Set the initial x-coordinate as needed
        this.y = 0; // Set the initial y-coordinate as needed
        this.color = "black"; // Set the color to black
    }




    public void rotate() {

    }
}
