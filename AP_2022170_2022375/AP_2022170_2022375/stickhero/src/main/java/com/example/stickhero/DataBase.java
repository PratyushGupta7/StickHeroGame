package com.example.stickhero;

import java.io.Serializable;


public class DataBase implements Serializable {
    int highestScore;
    int numCherries;

    int savedScore;
    DataBase() {
        this.highestScore = 0;
        this.numCherries = 0;
    }


}
