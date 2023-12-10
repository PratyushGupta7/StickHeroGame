package com.example.stickhero;

import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class WorldControllerTest {

    @Test
    public void testSerialization() throws IOException, ClassNotFoundException {
        // Create a DataBase object
        DataBase originalData = new DataBase();
        originalData.highestScore = 10;
        originalData.numCherries = 5;

        // Write the object to a file
        ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(Paths.get("C:\\Users\\praty\\OneDrive\\Desktop\\Stickfunctioningfile\\AP_2022170_2022375\\AP_2022170_2022375\\stickhero\\src\\main\\java\\com\\example\\stickhero\\database.txt")));
        out.writeObject(originalData);
        out.close();

        // Read the object back from the file
        ObjectInputStream in = new ObjectInputStream(Files.newInputStream(Paths.get("C:\\Users\\praty\\OneDrive\\Desktop\\Stickfunctioningfile\\AP_2022170_2022375\\AP_2022170_2022375\\stickhero\\src\\main\\java\\com\\example\\stickhero\\database.txt")));
        DataBase readData = (DataBase) in.readObject();
        in.close();

        // Check if the read object is equal to the original one
        assertEquals(originalData.highestScore, readData.highestScore, "Highest score did not match after serialization and deserialization");
        assertEquals(originalData.numCherries, readData.numCherries, "Number of cherries did not match after serialization and deserialization");
    }

    @Test
    public void testInitializeGameObjects() {
        WorldController worldController = new WorldController();
        worldController.initializeGameObjects();
        assertNotNull( "Character object should not be null after initialization",worldController.character);
        assertNotNull( "Cherry object should not be null after initialization",worldController.cherry);
        assertNotNull( "Obstacle object should not be null after initialization",worldController.obstacle  );
    }
}