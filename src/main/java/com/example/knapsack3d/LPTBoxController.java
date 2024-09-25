package com.example.knapsack3d;
import com.example.Solver.SolveForFillPentominoes;
import com.example.Solver.SolveForScoreParcels;
import com.example.Solver.SolveForScorePentominoes;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.transform.Rotate;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class LPTBoxController {
    static PerspectiveCamera camera;
    static AmbientLight ambientLight = new AmbientLight(Color.WHITE);
    public final static double truckLength = 33;
    public final static double truckWidth = 5;
    public final static double truckHeight = 8;
    public static Box[][][] cubeBoxes = new Box[(int) truckLength][(int) truckHeight][(int) truckWidth];
    private double lastMouseX, lastMouseY; // Variables to store last mouse coordinates
    private double cameraYRotation = 0; // Initial cameraera rotation
    private double cameraXRotation = 0; // Initial cameraera rotation
    static int[][][] field = new int[(int) truckLength][(int) truckHeight][(int) truckWidth];
    private Stage stage;
    @FXML
    private Pane boxLShow;
    @FXML
    private Pane boxPShow;
    @FXML
    private Pane boxTShow;
    private Stage primaryStage;
    private Scene scene;
    private Parent root;

    private void centerGroupInSubScene(Group group, SubScene subScene) {
        // Center the group horizontally and vertically in the SubScene
        group.setTranslateX(subScene.getWidth() / 2.0);
        group.setTranslateY(subScene.getHeight() / 2.0);
    }

    private void applyRotationAnimation(Group group) {
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(5), group);
        rotateTransition.setAxis(Rotate.Y_AXIS);
        rotateTransition.setFromAngle(0);
        rotateTransition.setToAngle(360);
        rotateTransition.setCycleCount(Timeline.INDEFINITE);
        rotateTransition.play();
    }
    private void add3DLShapeToPane() {
        // Dimensions for the "L" shape parts
        double verticalLength = 120, horizontalLength = 80;
        double blockSize = 40;  // Thickness of each part of the L

        // Create the vertical part of the "L"
        Box verticalPart = new Box(blockSize, verticalLength, blockSize);
        verticalPart.setMaterial(new PhongMaterial(Color.RED));

        // Create the horizontal part of the "L"
        Box horizontalPart = new Box(horizontalLength, blockSize, blockSize);
        horizontalPart.setMaterial(new PhongMaterial(Color.RED));
        horizontalPart.setTranslateY(verticalLength / 2 - blockSize / 2);
        horizontalPart.setTranslateX(-horizontalLength / 2 + blockSize / 2);

        // Group the parts
        Group LShape = new Group(verticalPart, horizontalPart);

        // Calculate the initial offset to center the "L" shape in the group
        LShape.setTranslateX(horizontalLength / 2.0 - blockSize / 2.0);
        LShape.setTranslateY(-verticalLength / 2.0 + blockSize / 2.0);

        // Create a SubScene
        SubScene subScene = new SubScene(LShape, boxLShow.getPrefWidth(), boxLShow.getPrefHeight(), true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.TRANSPARENT);

        // Center the group in the SubScene
        centerGroupInSubScene(LShape, subScene);

        // Add SubScene to Pane
        boxLShow.getChildren().add(subScene);

        // Optional: Apply rotation animation
        applyRotationAnimation(LShape);
    }

    private void add3DPShapeToPane() {
        // Dimensions for the "P" shape parts
        double verticalLength = 120, horizontalLength = 80;
        double blockSize = 40;  // Thickness of each part of the P

        // Create the vertical part of the "P"
        Box verticalPart = new Box(blockSize, verticalLength, blockSize);
        verticalPart.setMaterial(new PhongMaterial(Color.GREEN));
        verticalPart.setTranslateY(verticalPart.getTranslateY());
        verticalPart.setTranslateX(verticalPart.getTranslateX()+10);

        // Create the horizontal part of the "P"
        Box horizontalPart = new Box(horizontalLength, blockSize*2, blockSize);
        horizontalPart.setMaterial(new PhongMaterial(Color.GREEN));
        horizontalPart.setTranslateY(verticalLength/8 - blockSize*1.5+20);
        horizontalPart.setTranslateX(-horizontalLength / 2 + blockSize / 2+10);

        // Group the parts
        Group PShape = new Group(verticalPart, horizontalPart);
        PShape.setTranslateX(horizontalLength / 2.0 - blockSize / 2.0);
        PShape.setTranslateY(-verticalLength / 2.0 + blockSize / 2.0);

        // Create a SubScene
        SubScene subScene = new SubScene(PShape, boxPShow.getPrefWidth(), boxPShow.getPrefHeight(), true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.ALICEBLUE);

        // Center the group in the SubScene
        centerGroupInSubScene(PShape, subScene);

        // Add SubScene to Pane
        boxPShow.getChildren().add(subScene);

        // Optional: Apply rotation animation
        applyRotationAnimation(PShape);
    }

    private void add3DTShapeToPane() {
        // Dimensions for the "T" shape parts
        double verticalLength = 80, horizontalLength = 120;
        double blockSize = 40;  // Thickness of each part of the T

        // Create the vertical part of the "T"
        Box verticalPart = new Box(blockSize, verticalLength, blockSize);
        verticalPart.setMaterial(new PhongMaterial(Color.BLUE));
        verticalPart.setTranslateY(verticalLength / 2 - blockSize+20);
        verticalPart.setTranslateX(-horizontalLength / 2 + blockSize / 2+40);

        // Create the horizontal part of the "T"
        Box horizontalPart = new Box(horizontalLength, blockSize, blockSize);
        horizontalPart.setMaterial(new PhongMaterial(Color.BLUE));
        horizontalPart.setTranslateY(-verticalLength / 2 - blockSize / 2+20);
        horizontalPart.setTranslateX(-horizontalLength / 2 + blockSize / 2+40);

        // Group the parts
        Group TShape = new Group(verticalPart, horizontalPart);

        // Calculate the initial offset to center the "L" shape in the group
        TShape.setTranslateX(horizontalLength / 2.0 - blockSize/2.0);
        TShape.setTranslateY(-verticalLength / 2.0 + blockSize / 2.0);

        // Create a SubScene
        SubScene subScene = new SubScene(TShape, boxTShow.getPrefWidth(), boxTShow.getPrefHeight(), true, SceneAntialiasing.BALANCED);
        subScene.setFill(Color.TRANSPARENT);

        // Center the group in the SubScene
        centerGroupInSubScene(TShape, subScene);

        // Add SubScene to Pane
        boxTShow.getChildren().add(subScene);

        // Optional: Apply rotation animation
        applyRotationAnimation(TShape);
    }


    public void drawMatrix(Group group){
        PhongMaterial cubePhong = new PhongMaterial();
        cubePhong.setDiffuseColor(Color.BLACK);
        for(int x = 0 ; x < truckLength ; x++){
            for(int y = 0 ; y < truckHeight ; y++) {
                for (int z = 0; z < truckWidth; z++) {
                    Box cubeBox = new Box(1,1,1);
                    cubeBox.setDrawMode(DrawMode.FILL);
                    cubeBox.setMaterial(cubePhong);
                    cubeBox.setCullFace(CullFace.BACK);
                    cubeBox.setTranslateX(truckLength/2.0-x);
                    cubeBox.setTranslateY(truckHeight/2.0-y);
                    cubeBox.setTranslateZ(truckWidth/2.0-z);
                    cubeBoxes[x][y][z]=cubeBox;
                    group.getChildren().add(cubeBox);
                }
            }
        }
    }

    public static void colorMatrix() {
        PhongMaterial colorA = new PhongMaterial();
        PhongMaterial colorB = new PhongMaterial();
        PhongMaterial colorC = new PhongMaterial();
        PhongMaterial colorDefault = new PhongMaterial();
        colorA.setDiffuseColor(Color.RED);
        colorB.setDiffuseColor(Color.GREEN);
        colorC.setDiffuseColor(Color.BLUE);
        colorDefault.setDiffuseColor(Color.BLACK);


        for(int x = 0 ; x < truckLength ; x++){
            for(int y = 0 ; y < truckHeight ; y++) {
                for (int z = 0; z < truckWidth; z++) {
                    cubeBoxes[x][y][z].setCullFace(CullFace.NONE);

                    switch (field[x][y][z]){
                        case 0 : {
                            cubeBoxes[x][y][z].setDrawMode(DrawMode.LINE);
                            cubeBoxes[x][y][z].setMaterial(colorDefault);
                            break;
                        }
                        case 1 : {
                            cubeBoxes[x][y][z].setDrawMode(DrawMode.FILL);

                            cubeBoxes[x][y][z].setMaterial(colorA);
                            break;
                        }
                        case 2 : {
                            cubeBoxes[x][y][z].setDrawMode(DrawMode.FILL);

                            cubeBoxes[x][y][z].setMaterial(colorB);
                            break;
                        }
                        case 3 : {
                            cubeBoxes[x][y][z].setDrawMode(DrawMode.FILL);

                            cubeBoxes[x][y][z].setMaterial(colorC);
                            break;
                        }

                    }

                }
            }
        }
    }
    public static int[][][] deserializeField(String serializedField, int depth, int rows, int cols) {
        int[][][] field = new int[depth][rows][cols];
        String[] layers = serializedField.split(";");
        for (int i = 0; i < layers.length; i++) {
            String[] layerRows = layers[i].split("\\|");
            for (int j = 0; j < layerRows.length; j++) {
                String[] rowElements = layerRows[j].split(",");
                for (int k = 0; k < rowElements.length; k++) {
                    field[i][j][k] = Integer.parseInt(rowElements[k]);
                }
            }
        }
        return field;
    }

    public static PerspectiveCamera cameraConstructor(){
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateX(0);
        camera.setTranslateY(0);
        camera.setTranslateZ(-70);
        camera.setNearClip(0.1);
        return camera;
    }
    public void solveForFill(ActionEvent events) throws IOException {
        Group group = new Group();
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        Scene scene = new Scene(group, screenBounds.getMaxX()/1.5f, screenBounds.getMaxY()/1.5f , true, SceneAntialiasing.BALANCED);
        stage = (Stage)((Node)events.getSource()).getScene().getWindow();
        camera = cameraConstructor();
        scene.setCamera(camera);
        stage.setTitle("Knapsack Problem");
        stage.setScene(scene);
        group.getChildren().add(ambientLight);
        drawMatrix(group);

        scene.setOnMousePressed(event -> {
            lastMouseX = event.getSceneX();
            lastMouseY = event.getSceneY();
        });
        scene.setOnMouseDragged(event -> {
            double deltaX = event.getSceneX() - lastMouseX;
            double deltaY = event.getSceneY() - lastMouseY;

            // Adjust camera rotation based on mouse movement
            cameraXRotation += deltaY/3;
            cameraYRotation += deltaX/3;

            // Apply rotation to the camera
            group.getTransforms().clear();
            group.getTransforms().addAll(
                    new Rotate(-cameraYRotation, Rotate.Y_AXIS),
                    new Rotate(-cameraXRotation, Rotate.X_AXIS)
            );


            lastMouseX = event.getSceneX();
            lastMouseY = event.getSceneY();
        });

        stage.show();
        SolveForFillPentominoes.solveForFill(field);
        field=deserializeField(SolveForFillPentominoes.mostFilled, (int) truckLength, (int) truckHeight, (int) truckWidth);
        System.out.println("Empty spaces: "+ SolveForFillPentominoes.lowestEmptySpaces);
        colorMatrix();
    }

    public void solveForScore(ActionEvent events) throws IOException {
        Group group = new Group();
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        Scene scene = new Scene(group, screenBounds.getMaxX()/1.5f, screenBounds.getMaxY()/1.5f , true, SceneAntialiasing.BALANCED );
        stage = (Stage)((Node)events .getSource()).getScene().getWindow();
        camera = cameraConstructor();
        scene.setCamera(camera);
        stage.setTitle("Knapsack Problem");
        stage.setScene(scene);
        group.getChildren().add(ambientLight);
        drawMatrix(group);

        scene.setOnMousePressed(event -> {
            lastMouseX = event.getSceneX();
            lastMouseY = event.getSceneY();
        });
        scene.setOnMouseDragged(event -> {
            double deltaX = event.getSceneX() - lastMouseX;
            double deltaY = event.getSceneY() - lastMouseY;

            // Adjust camera rotation based on mouse movement
            cameraXRotation += deltaY/3;
            cameraYRotation += deltaX/3;

            // Apply rotation to the camera
            group.getTransforms().clear();
            group.getTransforms().addAll(
                    new Rotate(-cameraYRotation, Rotate.Y_AXIS),
                    new Rotate(-cameraXRotation, Rotate.X_AXIS)
            );


            lastMouseX = event.getSceneX();
            lastMouseY = event.getSceneY();
        });

        stage.show();
        SolveForScorePentominoes.solve(field);
        System.out.println("Score: " +SolveForScorePentominoes.currentValue);
        colorMatrix();
    }

    public void clickBack(ActionEvent events) throws IOException {
        root = FXMLLoader.load(getClass().getResource("StartUI.fxml"));
        primaryStage = (Stage)((Node)events.getSource()).getScene().getWindow();
        primaryStage.setTitle("Knapsack");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void clickExit(ActionEvent e){
        System.exit(0);
    }

    public void initialize() {
        add3DLShapeToPane();
        add3DPShapeToPane();
        add3DTShapeToPane();
    }
}
