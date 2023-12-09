package com.example.stickhero;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.canvas.Canvas;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class World extends Application {


    private double width;
    private double height;
    private double gravity;
    private int score;
    private Character character;
    private Pillar[] pillars;
    private Stick stick;
    private Reward[] rewards;


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(World.class.getResource("StartingScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}

