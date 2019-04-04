package Controllers;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;


public class Main extends Application {
    private Button runApp = new Button("Start Easy-park");
    private Label welcomeText = new Label("Welcome to Easy-Park");
    private Button quitApp = new Button("Quit program");

    @Override
    public void start(Stage primaryStage) throws Exception{
        //manualne vytvoreny prvy screen
        //tieto dva riadky su len na rychlejsie pracovanie s databzou
        Datasource.getInstance().openUsers();
        Datasource.getInstance().closeConnection();
        Parent root = FXMLLoader.load(getClass().getResource("../Scenes/login.fxml"));
        VBox pane = new VBox();
        pane.setAlignment(Pos.CENTER);
        pane.getChildren().add(welcomeText);
        pane.getChildren().add(runApp);
        pane.getChildren().add(quitApp);
        pane.setSpacing(25);
        String stylePane = "-fx-background-color: rgba(255, 220, 0, 1);";
        String styleButton = "-fx-background-color: rgba(52, 58, 64, 1);" +
                            "-fx-text-fill: white;" +
                            "-fx-pref-height: 35;" +
                            "-fx-pref-width: 100;";
        pane.setStyle(stylePane);
        quitApp.setStyle(styleButton);
        runApp.setStyle(styleButton);
        welcomeText.setFont(Font.font("Cambria", 32));
        runApp.setOnAction(event1 -> primaryStage.setScene((new Scene(root, 800, 600))));
        quitApp.setOnAction(event2 -> primaryStage.close());
        primaryStage.setTitle("Welcome");
        primaryStage.setScene(new Scene(pane, 800, 600));
        primaryStage.show();


//        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
//        primaryStage.setTitle("Easy-Park");
//        primaryStage.setScene(new Scene(root, 800, 600));
//        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
