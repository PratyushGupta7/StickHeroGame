package com.example.stickhero;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;


public class MainController {
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;



    @FXML
    void clickingEndButton(ActionEvent event) throws IOException, ClassNotFoundException {
        ObjectInputStream in = null;
        DataBase data = null;
        try {
            in = new ObjectInputStream(new FileInputStream("C:\\Users\\praty\\OneDrive\\Desktop\\Stickfunctioningfile\\AP_2022170_2022375\\AP_2022170_2022375\\stickhero\\src\\main\\java\\com\\example\\stickhero\\database.txt"));
            data = (DataBase) in.readObject();
            data.savedScore = WorldController.getScore();
            data.numCherries = WorldController.getNumCherries();
            if (data.savedScore > data.highestScore) {
                data.highestScore = data.savedScore;
            }
            ObjectOutputStream out = null;
            try {
                out = new ObjectOutputStream(Files.newOutputStream(Paths.get("C:\\Users\\praty\\OneDrive\\Desktop\\Stickfunctioningfile\\AP_2022170_2022375\\AP_2022170_2022375\\stickhero\\src\\main\\java\\com\\example\\stickhero\\database.txt")));
                out.writeObject(data);
                System.out.println("Data saved: " + data.savedScore);
            }
            finally {
                if (out != null) {
                    out = null;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in = null;
            }
        }

        Platform.exit();


    }
}
