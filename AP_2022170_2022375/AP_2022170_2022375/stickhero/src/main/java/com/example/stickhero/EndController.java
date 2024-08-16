package com.example.stickhero;

import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;




public class EndController implements Initializable {
    private Media gamemusic;
    private Media falling;
    private Media stickfall;
    private Media walking;
    private Media stickgrowing;
    private Character character;
    private Cherry cherry;

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label welcomeText;
    @FXML
    private Label best;
    @FXML
    private Label normal;
    private static int alternationTracker = 0;

    private static final int CANVAS_WIDTH = 800;
    private static final int CANVAS_HEIGHT = 400;
    private static final int CHARACTER_SPEED = 15;
    private static final int GHOST_SPEED = 10;
    protected static int characterstartingposition = 25;
    protected static int absolutecharacterstartingposition = 0;
    private double distanceBetweenPillars;
    private double ghostDistance = -150;

    protected static double pillarstartingposition = 180.9;
    private boolean cherrycollected;
    private boolean ghostmet;
    private MediaPlayer mediaPlayer;
    private MediaPlayer ButtonPlayer;
    private MediaPlayer FallingPlayer;
    private MediaPlayer StickfallingPlayer;
    private MediaPlayer RunningPlayer;
    private static final Duration FRAME_DURATION = Duration.millis(16);
    double characterdistance = 0;
    double totalshifteddistance;
    private boolean isFlipped = false;

    private static final Double STICK_EXTENSION_RATE = 0.5;

    private GraphicsContext gc;

    private Stick stick;
    protected double characterX;
    private boolean holdingStick = true;

    private double rectangleRate = 5.0;
    private Timeline heightIncreaseTimeline;
    private Timeline timeline;

    private boolean isSpaceKeyPressed;
    private Rectangle extendingRectangle;
    Rectangle newRectangle = new Rectangle();
    private boolean needed = false;

    @FXML
    private ImageView characterImageView;
    private ImageView placeholder; // Placeholder ImageView
    @FXML
    private static Rectangle myrectangle;
    @FXML
    private ImageView myCherryView;
    @FXML
    private ImageView myGhost;
    @FXML
    private static Rectangle myoriginalrectangle;

    @FXML
    private static Label scoretracker;
    @FXML
    private static Label cherrytracker;
    private Image originalImage;
    private Image kickImage;
    private Rectangle currentPillar;
    private Rectangle nextPillar;

    private Bounds characterBoundsInScreen;
    private Bounds rectangleBoundsInScreen;
    private double nextpillarX;

    private DataBase loadedData;

    public static int getAlternationTracker() {
        return alternationTracker;
    }

    public static Rectangle getmyOriginalRectangle() {
        return myoriginalrectangle;
    }

    public static Rectangle getmyRectangle() {
        return myrectangle;
    }

    public static int getScore() {
        return Integer.valueOf(scoretracker.getText()).intValue();
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // originalImage = new Image(World.class.getResourceAsStream("MainStickHero-removebg-preview.png"));
        // characterImageView.setImage(originalImage);
    }
    void resetGameState() {
        characterstartingposition = 25;
        absolutecharacterstartingposition = 0;
        pillarstartingposition = 180.9;
        characterX = characterstartingposition;
        ghostDistance = -200;
        cherrycollected = false;
        ghostmet = false;
        isFlipped = false;
        needed = false;
        characterdistance = 0;
        totalshifteddistance = 0;
    }
    @FXML
    private Canvas gameCanvas;


    @FXML
    void clickingReviveButton(ActionEvent event) throws IOException, ClassNotFoundException, URISyntaxException {
        int numCherries = WorldController.getNumCherries();
        if (numCherries < 3) {
            System.out.println("Not enough cherries");
            return;
        }
        if (numCherries >= 3) {
            ObjectInputStream in = null;
            ObjectOutputStream out = null;
            try {
                Path path = Paths.get("C:\\Users\\praty\\OneDrive\\Desktop\\Java(OOPS)\\Project\\2022170_2022375_Project\\Stickfunctioningfile\\AP_2022170_2022375\\AP_2022170_2022375\\stickhero\\src\\main\\java\\com\\example\\stickhero\\database.txt");
                if (!Files.exists(path)) {
                    Files.createFile(path);
                    loadedData = new DataBase();
                } else {
                    in = new ObjectInputStream(Files.newInputStream(path));
                    loadedData = (DataBase) in.readObject();
                }

                loadedData.numCherries = loadedData.numCherries - 3;
                out = new ObjectOutputStream(Files.newOutputStream(path));
                out.writeObject(loadedData);
            } finally {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            }
        }
        FXMLLoader fxmlLoader = new FXMLLoader(World.class.getResource("MainScreen.fxml"));
        this.root = fxmlLoader.load();
        character = Character.getInstance();
        cherry = new Cherry();
        characterImageView = (ImageView) this.root.lookup("#characterImageView");
        scoretracker = (Label) this.root.lookup("#scoretracker");
        cherrytracker = (Label) this.root.lookup("#cherrytracker");
        System.out.println("HELLO FROM HEREEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
        loadedData = readObject();
        character.setCurrentImageView(characterImageView);
        myrectangle = (Rectangle) root.lookup("#myrectangle");
        myoriginalrectangle = (Rectangle) root.lookup("#myoriginalrectangle");
        myCherryView = (ImageView) this.root.lookup("#myCherryView");
        myGhost = (ImageView) this.root.lookup("#myGhost");
        cherry.setCurrentCherry(myCherryView);
        currentPillar = myoriginalrectangle;
        nextPillar = myrectangle;
        originalImage = new Image(Objects.requireNonNull(World.class.getResourceAsStream("MainStickHero-removebg-preview.png")));
        character.getCurrentImageView().setImage(originalImage);
        kickImage = new Image(Objects.requireNonNull(World.class.getResourceAsStream("Kick.png")));

        // Create a Media object with the specified MP3 file
        gamemusic = new Media(World.class.getResource("walking.mp3").toURI().toString());
        falling = new Media(World.class.getResource("falling.mp3").toURI().toString());
        stickfall = new Media(World.class.getResource("stickfalling.mp3").toURI().toString());
        walking = new Media(World.class.getResource("running-sounds-6003.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(gamemusic);
        ((Pane) root).getChildren().add(newRectangle);
        ((Pane) root).getChildren().remove(cherry.getCurrentCherry());
        ((Pane) root).getChildren().remove(myGhost);
        characterX = characterstartingposition;
        newRectangle.setX(147);
        newRectangle.setY(527.5);
        newRectangle.setWidth(5.8);
        newRectangle.setHeight(1.5);
        newRectangle.setFill(Color.BLACK);
        resetGameState();

        Scene scene = new Scene(root);
        scene.setRoot(root);

        Platform.runLater(() -> {

            characterBoundsInScreen = character.getCurrentImageView().localToScreen(characterImageView.getBoundsInLocal());
            double characterScreenX = characterBoundsInScreen.getMaxX();
            double characterScreenXbefore = characterBoundsInScreen.getMinX();


            System.out.println("Character X in Screen: " + characterScreenXbefore);
            System.out.println("Character X in Screen: " + characterScreenX);


        });


        scene.setOnKeyPressed(Event -> {
            if (Event.getCode() == KeyCode.A) {
                if (needed) {
                    System.out.println("Done it-----");
                    flipCharacter();
                } else {
                    isSpaceKeyPressed = true;
                    startGrowingRectangle();
                }
            }
        });

        scene.setOnKeyReleased(Event -> {
            if (Event.getCode() == KeyCode.A && !needed) {
                isSpaceKeyPressed = false;
                stopGrowingRectangle();
                kick();
            }
        });
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    void clickingPlayButton(ActionEvent event) throws IOException, ClassNotFoundException, URISyntaxException {
        FXMLLoader fxmlLoader = new FXMLLoader(World.class.getResource("MainScreen.fxml"));
        this.root = fxmlLoader.load();
        character = Character.getInstance();
        cherry = new Cherry();
        characterImageView = (ImageView) this.root.lookup("#characterImageView");
        scoretracker = (Label) this.root.lookup("#scoretracker");
        cherrytracker = (Label) this.root.lookup("#cherrytracker");
        System.out.println("HELLO FROM HEREEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEE");
        loadedData = readObject();
        character.setCurrentImageView(characterImageView);
        myrectangle = (Rectangle) root.lookup("#myrectangle");
        myoriginalrectangle = (Rectangle) root.lookup("#myoriginalrectangle");
        myCherryView = (ImageView) this.root.lookup("#myCherryView");
        myGhost = (ImageView) this.root.lookup("#myGhost");
        cherry.setCurrentCherry(myCherryView);
        currentPillar = myoriginalrectangle;
        nextPillar = myrectangle;
        originalImage = new Image(Objects.requireNonNull(World.class.getResourceAsStream("MainStickHero-removebg-preview.png")));
        character.getCurrentImageView().setImage(originalImage);
        kickImage = new Image(Objects.requireNonNull(World.class.getResourceAsStream("Kick.png")));

        gamemusic = new Media(World.class.getResource("walking.mp3").toURI().toString());
        falling = new Media(World.class.getResource("falling.mp3").toURI().toString());
        stickfall = new Media(World.class.getResource("stickfalling.mp3").toURI().toString());
        walking = new Media(World.class.getResource("running-sounds-6003.mp3").toURI().toString());
        mediaPlayer = new MediaPlayer(gamemusic);
        ((Pane) root).getChildren().add(newRectangle);
        ((Pane) root).getChildren().remove(cherry.getCurrentCherry());
        ((Pane) root).getChildren().remove(myGhost);
        characterX = characterstartingposition;
        newRectangle.setX(147);
        newRectangle.setY(527.5);
        newRectangle.setWidth(5.8);
        newRectangle.setHeight(1.5);
        newRectangle.setFill(Color.BLACK);
        resetGameState();

        Scene scene = new Scene(root);
        scene.setRoot(root);

        Platform.runLater(() -> {

            characterBoundsInScreen = character.getCurrentImageView().localToScreen(characterImageView.getBoundsInLocal());
            double characterScreenX = characterBoundsInScreen.getMaxX();
            double characterScreenXbefore = characterBoundsInScreen.getMinX();


            System.out.println("Character X in Screen: " + characterScreenXbefore);
            System.out.println("Character X in Screen: " + characterScreenX);


        });


        scene.setOnKeyPressed(Event -> {
            if (Event.getCode() == KeyCode.A) {
                if (needed) {
                    System.out.println("Done it-----");
                    flipCharacter();
                } else {
                    isSpaceKeyPressed = true;
                    startGrowingRectangle();
                }
            }
        });

        scene.setOnKeyReleased(Event -> {
            if (Event.getCode() == KeyCode.A && !needed) {
                isSpaceKeyPressed = false;
                stopGrowingRectangle();
                kick();
            }
        });
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    private DataBase readObject() throws ClassNotFoundException {
        System.out.println("Inside here");
        DataBase temp;
        ObjectInputStream in = null;
        try {
            System.out.println("try block entered");
            Path path = Paths.get("C:\\Users\\praty\\OneDrive\\Desktop\\Java(OOPS)\\Project\\2022170_2022375_Project\\Stickfunctioningfile\\AP_2022170_2022375\\AP_2022170_2022375\\stickhero\\src\\main\\java\\com\\example\\stickhero\\database.txt");

            if (!Files.exists(path)) {
                Files.createFile(path);
                temp = new DataBase();
                try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(path))) {
                    out.writeObject(temp);
                }
            } else {
                in = new ObjectInputStream(Files.newInputStream(path));
                temp = (DataBase) in.readObject();
            }

            String hiScore = String.valueOf(temp.highestScore);
            String nCherries = String.valueOf(temp.numCherries);
            cherrytracker.setText(nCherries);
        } catch (IOException e) {
            System.out.println("LOADING IT FIRST TIME");
            temp = new DataBase();
            String hiScore = String.valueOf(temp.highestScore);
            String nCherries = String.valueOf(temp.numCherries);
            cherrytracker.setText(nCherries);
            System.out.println(hiScore + '\n' + nCherries);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Read saved score: " + temp.savedScore);
        return temp;
    }

    private void startGrowingRectangle() {

        if (mediaPlayer.getStatus() != MediaPlayer.Status.PLAYING) {
            mediaPlayer.play();
        }
        if (isSpaceKeyPressed) {
            heightIncreaseTimeline = new Timeline(new KeyFrame(Duration.seconds(0.7), e -> increaseRectangleHeight()));
            heightIncreaseTimeline.setCycleCount(1);
            heightIncreaseTimeline.play();
        }
    }

    private void increaseRectangleHeight() {
        newRectangle.setHeight(newRectangle.getHeight() + rectangleRate);
        newRectangle.setY(newRectangle.getY() - rectangleRate);
    }

    private void stopGrowingRectangle() {
        mediaPlayer.stop();
        StickfallingPlayer = new MediaPlayer(stickfall);
        StickfallingPlayer.play();
        if (heightIncreaseTimeline != null && !isSpaceKeyPressed) {
            heightIncreaseTimeline.stop();
            heightIncreaseTimeline = null;

            double pivotX = newRectangle.getX() + newRectangle.getWidth();
            double pivotY = newRectangle.getY() + newRectangle.getHeight();
            Timeline rotationTimeline = new Timeline(
                    new KeyFrame(Duration.seconds(0),
                            new KeyValue(newRectangle.heightProperty(), newRectangle.getHeight()),
                            new KeyValue(newRectangle.yProperty(), newRectangle.getY())),
                    new KeyFrame(Duration.seconds(0.8),
                            new KeyValue(newRectangle.heightProperty(), newRectangle.getHeight() - 2.95 * rectangleRate),
                            new KeyValue(newRectangle.yProperty(), newRectangle.getY() + 2.95 * rectangleRate))
            );

            Rotate rotate = new Rotate(90, pivotX, pivotY);
            newRectangle.getTransforms().add(rotate);

            rotationTimeline.getKeyFrames().addAll(
                    new KeyFrame(Duration.seconds(0), new KeyValue(rotate.angleProperty(), 0)),
                    new KeyFrame(Duration.seconds(0.8), new KeyValue(rotate.angleProperty(), 90))
            );

            rotationTimeline.setCycleCount(1); // Play the timeline once
            rotationTimeline.play();
        }
    }

    private void kick() {
        cherrycollected = false;
        ghostmet = false;

        needed = true;
        System.out.println("Abs Starting Character:" + character.getCurrentImageView().getX());
        if (character.getCurrentImageView() != null) {
            character.getCurrentImageView().setImage(kickImage);

            Timer timer = new Timer();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    mediaPlayer.stop();
                    playAnimation();
                }
            };

            timer.schedule(timerTask, 800);
        }
    }


    private void flipCharacter() {
        double currentScale = character.getCurrentImageView().getScaleY();
        double pivotX = character.getCurrentImageView().getBoundsInLocal().getWidth() / 2;
        double pivotY = character.getCurrentImageView().getBoundsInLocal().getHeight(); // Set pivot point to the bottom of the ImageView

        Scale flip = new Scale(1, -1, pivotX, pivotY);
        character.getCurrentImageView().getTransforms().add(flip);

        isFlipped = !isFlipped;
    }

    public void playAnimation() {
        RunningPlayer = new MediaPlayer(walking);
        RunningPlayer.play();
        Image[] frames = new Image[5];
        for (int i = 0; i < frames.length; i++) {
            frames[i] = new Image(Objects.requireNonNull(World.class.getResourceAsStream("Frame" + (i + 1) + ".png")));
        }

        timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), e -> {
            int frameIndex = (int) (Math.random() * frames.length);
            character.getCurrentImageView().setImage(frames[frameIndex]);
            try {
                updateCharacter();
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            } catch (InterruptedException ex) {
                throw new RuntimeException(ex);
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    public static int getNumCherries() {
        return Integer.parseInt(cherrytracker.getText());
    }

    public static void setNumCherries(int number) {
        cherrytracker.setText(String.valueOf(number));
    }


    class CharacterMotion implements Runnable {

        @Override
        public void run() {

            characterX += CHARACTER_SPEED;
            character.getCurrentImageView().setX(characterX);

        }
    }

    class GhostMotion implements Runnable {

        @Override
        public void run() {
            myGhost.setX(myGhost.getX() - GHOST_SPEED);

        }
    }


    public void updateCharacter() throws IOException, ClassNotFoundException, InterruptedException {
        EndController.CharacterMotion charMoves = new EndController.CharacterMotion();
        EndController.GhostMotion ghostMoves = new EndController.GhostMotion();
        Thread t1 = new Thread(charMoves);
        Thread t2 = new Thread(ghostMoves);
        t1.start();
        t2.start();
        //t1.join();t1.join();

        Bounds characterBounds = character.getCurrentImageView().localToScreen(character.getCurrentImageView().getBoundsInLocal());
        Bounds cherryBounds = cherry.getCurrentCherry().localToScreen(cherry.getCurrentCherry().getBoundsInLocal());
        Bounds ghostBounds = myGhost.localToScreen(myGhost.getBoundsInLocal());
        if (characterBounds != null && cherryBounds != null) {
            if (characterBounds.getMaxX() > cherryBounds.getMinX() && characterBounds.getMaxY() > cherryBounds.getMinY() && characterBounds.getMinX() < cherryBounds.getMaxX() && characterBounds.getMinY() < cherryBounds.getMaxY()) {
                if (((Pane) root).getChildren().contains(cherry.getCurrentCherry()) && !cherrycollected) {
                    cherry.getCurrentCherry().setVisible(false);
                    String cherryString = cherrytracker.getText();
                    int cherryCherry = Integer.parseInt(cherryString);
                    String newCherry = String.valueOf(cherryCherry + 1);
                    cherrytracker.setText(newCherry);
                    cherrycollected = true;

                }
            }
        }
        if (characterBounds != null && ghostBounds != null) {
            if (characterBounds.getMaxX() > (ghostBounds.getMinX() + 12) && characterBounds.getMaxY() > ghostBounds.getMinY() && characterBounds.getMinX() < ghostBounds.getMaxX() && characterBounds.getMinY() < ghostBounds.getMaxY()) {
                if (((Pane) root).getChildren().contains(myGhost) && !ghostmet) {
                    //myGhost.setVisible(false);
                    ghostmet = true;
                    RunningPlayer.stop();
                    timeline.stop();
                    character.getCurrentImageView().setImage(originalImage);
                    characterFall();

                }
            }


        }

        // Check if the character reaches the end of the stick
        double stickEndX = characterstartingposition + newRectangle.getHeight();
        System.out.println("End of Stick:" + stickEndX);
        rectangleBoundsInScreen = nextPillar.localToScreen(nextPillar.getBoundsInLocal());
        distanceBetweenPillars = (rectangleBoundsInScreen.getMinX() - characterBoundsInScreen.getMaxX());
        double rectX1 = characterstartingposition + distanceBetweenPillars;
        double rectX2 = rectX1 + nextPillar.getWidth();
        if (characterX >= (rectX1 - 15) && isFlipped) {
            executeAction();
        }
        if (characterX >= stickEndX) {
            System.out.println("1st End of Rectangle:" + rectX1);
            System.out.println("2nd End of Rectangle:" + rectX2);
            if (stickEndX < rectX1 || stickEndX > rectX2) {
                executeAction();

            } else {
                if (characterX >= (rectX2 - 25)) {
                    if (alternationTracker == 0) {
                        alternationTracker = 1;
                    } else {
                        alternationTracker = 0;
                    }
                    myGhost.setVisible(false);
                    ((Pane) root).getChildren().remove(myGhost);

                    character.getCurrentImageView().setImage(originalImage);
                    timeline.stop();
                    newRectangle.setHeight(1.5);
                    newRectangle.setWidth(5.8);
                    newRectangle.setX(147);
                    newRectangle.setY(527.5);
                    newRectangle.getTransforms().clear();
                    currentPillar.setVisible(false);
                    if (((Pane) root).getChildren().contains(cherry.getCurrentCherry())) {
                        ((Pane) root).getChildren().remove(cherry.getCurrentCherry());
                    }
                    String scoreString = scoretracker.getText();
                    int currentScore = Integer.parseInt(scoreString);
                    String newScore = String.valueOf(currentScore + 1);
                    scoretracker.setText(newScore);
                    CharacterAnimationTransition();
                }
            }
        }
    }


    public void executeAction() throws ClassNotFoundException, IOException {
        character.getCurrentImageView().setImage(originalImage);
        timeline.stop();
        if (Integer.parseInt(scoretracker.getText()) > loadedData.highestScore) {
            loadedData.highestScore = Integer.parseInt(scoretracker.getText());
        }
        loadedData.numCherries = Integer.parseInt(cherrytracker.getText());
        loadedData.savedScore = 0;
        ObjectOutputStream out = null;
        try {
            Path path = Paths.get("C:\\Users\\praty\\OneDrive\\Desktop\\Java(OOPS)\\Project\\2022170_2022375_Project\\Stickfunctioningfile\\AP_2022170_2022375\\AP_2022170_2022375\\stickhero\\src\\main\\java\\com\\example\\stickhero\\database.txt");

            if (!Files.exists(path)) {
                Files.createFile(path);
            }

            out = new ObjectOutputStream(Files.newOutputStream(path));
            out.writeObject(loadedData);
        } finally {
            if (out != null) {
                out.close();
            }
        }
        RunningPlayer.stop();
        characterFall();
    }


    private void CharacterAnimationTransition() {
        RunningPlayer.stop();

        needed = false;
        Rectangle temp = currentPillar;
        currentPillar = nextPillar;
        nextPillar = temp;

        characterdistance = (character.getCurrentImageView().getX() - absolutecharacterstartingposition);
        double pillardistance = (character.getCurrentImageView().getX() - absolutecharacterstartingposition) - 20;

        TranslateTransition shiftTransitionPillar = new TranslateTransition();
        shiftTransitionPillar.setNode(currentPillar);
        shiftTransitionPillar.setDuration(Duration.seconds(1));
        shiftTransitionPillar.setByX(-pillardistance);


        TranslateTransition shiftTransitionCharacter = new TranslateTransition();
        shiftTransitionCharacter.setNode(character.getCurrentImageView());
        shiftTransitionCharacter.setDuration(Duration.seconds(1));
        shiftTransitionCharacter.setByX(-characterdistance);


        ParallelTransition parallelTransition = new ParallelTransition(shiftTransitionPillar, shiftTransitionCharacter);


        parallelTransition.setOnFinished(e2 -> {
            absolutecharacterstartingposition += (int) characterdistance;
            characterstartingposition = absolutecharacterstartingposition + 25;
            characterX = absolutecharacterstartingposition;
            character.getCurrentImageView().setX(characterX);
            character.getCurrentImageView().setImage(originalImage);
            pillarstartingposition += (130 + Math.random() * 20);
            // Randomize the width and position of the next pillar
            nextPillar.setWidth(Math.random() * 100 + 50);
            nextPillar.setX(pillarstartingposition);
            nextPillar.setVisible(true);
            if (Math.random() > 0.25) {
                rectangleBoundsInScreen = nextPillar.localToScreen(nextPillar.getBoundsInLocal());
                distanceBetweenPillars = (rectangleBoundsInScreen.getMinX() - characterBoundsInScreen.getMaxX());
                double cherrywidth = cherry.getCurrentCherry().getFitWidth();
                Random rand = new Random();
                double random = 0.1 + rand.nextDouble() * (0.8 - 0.1);
                ((Pane) root).getChildren().add(cherry.getCurrentCherry());
                cherry.getCurrentCherry().setX((random * (distanceBetweenPillars)));
                ((Pane) root).getChildren().add(myGhost);
                myGhost.setX(distanceBetweenPillars);
                myGhost.setVisible(true);
                System.out.println("Random:" + random);
                cherry.getCurrentCherry().setVisible(true);
            }


            startGrowingRectangle();
        });


        parallelTransition.play();
    }

    private void characterFall() throws IOException {
        FallingPlayer = new MediaPlayer(falling);
        FallingPlayer.play();
        double fallDuration = 2.0;
        double fallDistance = CANVAS_HEIGHT - character.getCurrentImageView().getY();

        Timeline fallTimeline = new Timeline(
                new KeyFrame(Duration.seconds(fallDuration),
                        new KeyValue(character.getCurrentImageView().translateYProperty(), fallDistance))
        );

        fallTimeline.setOnFinished(e -> {
            character.getCurrentImageView().setVisible(false);
            FallingPlayer.stop();
            FXMLLoader endingSceneLoader = new FXMLLoader(getClass().getResource("EndingScene.fxml"));
            try {
                Parent endingSceneRoot = endingSceneLoader.load();
                best = (Label) endingSceneRoot.lookup("#best");
                normal = (Label) endingSceneRoot.lookup("#normal");
                ObjectOutputStream out = null;
                try {
                    out = new ObjectOutputStream(Files.newOutputStream(Paths.get("C:\\Users\\praty\\OneDrive\\Desktop\\Java(OOPS)\\Project\\2022170_2022375_Project\\Stickfunctioningfile\\AP_2022170_2022375\\AP_2022170_2022375\\stickhero\\src\\main\\java\\com\\example\\stickhero\\database.txt")));
                    if (Integer.parseInt(scoretracker.getText()) > loadedData.highestScore) {
                        loadedData.highestScore = Integer.parseInt(scoretracker.getText());
                    }
                    loadedData.numCherries = Integer.parseInt(cherrytracker.getText());
                    out.writeObject(loadedData);
                    best.setText(String.valueOf(loadedData.highestScore));
                    normal.setText(scoretracker.getText());
                } finally {
                    if (out != null) {
                        out = null;
                    }
                }
                Scene endingScene = new Scene(endingSceneRoot);
                stage.setScene(endingScene);
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        fallTimeline.play();
    }

    @FXML
    void clickingHomeButton(ActionEvent event) throws IOException, ClassNotFoundException {
        Platform.exit();

    }
}
