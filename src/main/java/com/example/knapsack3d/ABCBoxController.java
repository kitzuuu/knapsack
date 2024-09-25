package com.example.knapsack3d;

import com.example.Solver.SolveForFillParcels;
import com.example.Solver.SolveForFillPentominoes;
import com.example.Solver.SolveForScoreParcels;
import javafx.animation.AnimationTimer;
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

import java.io.IOException;

public class ABCBoxController {
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
    public static int ABoxLength = 40;
    public static int ABoxWidth = 40;
    public static int ABoxHeight = 80;
    public static int BBoxLength = 40;
    public static int BBoxWidth = 60;
    public static int BBoxHeight = 80;
    public static int CBoxLength = 60;
    public static int CBoxWidth = 60;
    public static int CBoxHeight = 60;
    public static Box A = new Box(ABoxHeight,ABoxWidth,ABoxLength);
    public static Box B = new Box(BBoxHeight,BBoxWidth,BBoxLength);
    public static Box C = new Box(CBoxHeight,CBoxWidth,CBoxLength);
    @FXML
    private Pane boxAShow;
    @FXML
    private Pane boxBShow;
    @FXML
    private Pane boxCShow;
    private Stage primaryStage;
    private Scene scene;
    private Parent root;

    private void centerCubeInPane(Group group) {
        double centerX = boxAShow.getWidth() / 2.0;
        double centerY = boxAShow.getHeight() / 2.0;
        double centerZ = 0; // Adjust if needed

        // Assuming the cube's center is at (0, 0, 0) in the group
        group.setTranslateX(centerX);
        group.setTranslateY(centerY);
        group.setTranslateZ(centerZ);
    }

    private void addRotatingCubeAToPane() {
        // Create the 3D cube
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.RED);
        A.setMaterial(material);

        // Create rotation transforms
        Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
        Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);
        A.getTransforms().addAll(rotateX, rotateY);

        // Create a SubScene for 3D content
        Group group = new Group(A);
        SubScene subScene = new SubScene(group, boxAShow.getPrefWidth(), boxAShow.getPrefHeight());
        subScene.setFill(Color.ALICEBLUE);

        centerCubeInPane(group);
        // Add the SubScene to the Pane
        boxAShow.getChildren().add(subScene);

        // Handle Pane resize to re-center the cube
        boxAShow.widthProperty().addListener((obs, oldVal, newVal) -> centerCubeInPane(group));
        boxAShow.heightProperty().addListener((obs, oldVal, newVal) -> centerCubeInPane(group));

        // Animation for rotation
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                rotateX.setAngle(rotateX.getAngle() + 0.5); // Adjust rotation speed
                rotateY.setAngle(rotateY.getAngle() + 0.5); // Adjust rotation speed
            }
        };
        timer.start();
    }

    private void addRotatingCubeBToPane() {
        // Create the 3D cube
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.GREEN); // Set color as needed
        B.setMaterial(material);

        // Create rotation transforms
        Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
        Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);
        B.getTransforms().addAll(rotateX, rotateY);

        // Create a SubScene for 3D content
        Group group = new Group(B);
        SubScene subScene = new SubScene(group, boxBShow.getPrefWidth(), boxBShow.getPrefHeight());
        subScene.setFill(Color.ALICEBLUE);

        centerCubeInPane(group);
        // Add the SubScene to the Pane
        boxBShow.getChildren().add(subScene);

        // Handle Pane resize to re-center the cube
        boxBShow.widthProperty().addListener((obs, oldVal, newVal) -> centerCubeInPane(group));
        boxBShow.heightProperty().addListener((obs, oldVal, newVal) -> centerCubeInPane(group));

        // Animation for rotation
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                rotateX.setAngle(rotateX.getAngle() + 0.5); // Adjust rotation speed
                rotateY.setAngle(rotateY.getAngle() + 0.5); // Adjust rotation speed
            }
        };
        timer.start();
    }

    private void addRotatingCubeCToPane() {
        // Create the 3D cube
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.BLUE); // Set color as needed
        C.setMaterial(material);

        // Create rotation transforms
        Rotate rotateX = new Rotate(0, Rotate.X_AXIS);
        Rotate rotateY = new Rotate(0, Rotate.Y_AXIS);
        C.getTransforms().addAll(rotateX, rotateY);

        // Create a SubScene for 3D content
        Group group = new Group(C);
        SubScene subScene = new SubScene(group, boxCShow.getPrefWidth(), boxCShow.getPrefHeight());
        subScene.setFill(Color.ALICEBLUE);

        centerCubeInPane(group);
        // Add the SubScene to the Pane
        boxCShow.getChildren().add(subScene);

        // Handle Pane resize to re-center the cube
        boxCShow.widthProperty().addListener((obs, oldVal, newVal) -> centerCubeInPane(group));
        boxCShow.heightProperty().addListener((obs, oldVal, newVal) -> centerCubeInPane(group));

        // Animation for rotation
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                rotateX.setAngle(rotateX.getAngle() + 0.5); // Adjust rotation speed
                rotateY.setAngle(rotateY.getAngle() + 0.5); // Adjust rotation speed
            }
        };
        timer.start();
    }
    public void drawMatrix(Group group){
        PhongMaterial cubePhong = new PhongMaterial();
        cubePhong.setDiffuseColor(Color.BLACK);
        for(int x = 0 ; x < truckLength ; x++){
            for(int y = 0 ; y < truckHeight ; y++) {
                for (int z = 0; z < truckWidth; z++) {
                    Box cubeBox = new Box(1,1,1);
                    cubeBox.setDrawMode(DrawMode.FILL);
                    cubeBox.setCullFace(CullFace.NONE);
                    cubeBox.setMaterial(cubePhong);
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
        SolveForFillParcels.solveForFill(field);
        field=deserializeField(SolveForFillParcels.mostFilled, (int) truckLength, (int) truckHeight, (int) truckWidth);
        System.out.println("Empty spaces: "+ SolveForFillParcels.lowestEmptySpaces);
        colorMatrix();
    }

    public void solveForScore(ActionEvent events) throws IOException {
        Group group = new Group();
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        Scene scene = new Scene(group, screenBounds.getMaxX()/1.5f, screenBounds.getMaxY()/1.5f , true, SceneAntialiasing.BALANCED );
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
        SolveForScoreParcels.solve(field);
        System.out.println("Score: "+ SolveForScoreParcels.currentValue);
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
        addRotatingCubeAToPane();
        addRotatingCubeBToPane();
        addRotatingCubeCToPane();
    }
}
