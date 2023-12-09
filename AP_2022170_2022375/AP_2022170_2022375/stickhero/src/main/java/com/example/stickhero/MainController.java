package com.example.stickhero;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

class SaveGame implements Serializable {

    public Coordinates coordinates1;
    public Coordinates coordinates2;
    class Coordinates implements Serializable{
        double x;
        double y;

        @Override
        public String toString() {
            return "Coordinates{" +
                    "x=" + x +
                    ", y=" + y +
                    ", width=" + width +
                    ", height=" + height +
                    '}';
        }

        double width;
        double height;

        Coordinates (double x,double y,double width,double height) {
            this.x = x;
            this.y = y;
            this.width = width;
            this.height = height;
        }
    }
    private final int score;

    public SaveGame(Rectangle rectangle1 , Rectangle rectangle2, int score) {
        this.score = score;
        coordinates1 = new Coordinates(rectangle1.getLayoutX(),rectangle1.getLayoutY(),rectangle1.getWidth(),rectangle1.getHeight());
        coordinates2 = new Coordinates(rectangle2.getLayoutX(),rectangle2.getLayoutY(),rectangle2.getWidth(),rectangle2.getHeight());


    }



    public int getScore() {
        return score;
    }
}

public class MainController {
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;



    @FXML
    void clickingEndButton(ActionEvent event) throws IOException, ClassNotFoundException {
        SaveGame game;
        int tracker = WorldController.getAlternationTracker();
        if (tracker == 1) {
            game = new SaveGame(WorldController.getmyRectangle(),WorldController.getmyOriginalRectangle(),WorldController.getScore());
        }
        else {
            game = new SaveGame(WorldController.getmyOriginalRectangle(),WorldController.getmyRectangle(),WorldController.getScore());
        }
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(Files.newOutputStream(Paths.get("C:\\Users\\praty\\OneDrive\\Desktop\\Stickfunctioningfile\\AP_2022170_2022375\\AP_2022170_2022375\\stickhero\\src\\main\\java\\com\\example\\stickhero\\savedgame.txt")));
            out.writeObject(game);
        }
        finally {
            if (out!=null) {
                out = null;
            }
        }
        System.out.println("Hi");

        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream("C:\\Users\\praty\\OneDrive\\Desktop\\Stickfunctioningfile\\AP_2022170_2022375\\AP_2022170_2022375\\stickhero\\src\\main\\java\\com\\example\\stickhero\\savedgame.txt"));
            SaveGame loadedGame = (SaveGame) in.readObject();
            System.out.println(loadedGame.coordinates1);
            System.out.println(loadedGame.coordinates2);
        }
        finally{
            if (in != null) {
                in = null;
            }
        }
    }
};
