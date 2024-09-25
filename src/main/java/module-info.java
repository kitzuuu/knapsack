module com.example.knapsack3d {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.knapsack3d to javafx.fxml;
    exports com.example.knapsack3d;
}