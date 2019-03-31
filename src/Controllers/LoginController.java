package Controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    private String userName;
    private String userSurename;
    private String userSPZ;
    private String userFuelType;
    private boolean isLoginSuccesful = false;

    @FXML
    private TextField spz;
    @FXML
    private TextField password;
    @FXML
    private Button loginButton;

    @FXML
    public void initialize(){
        loginButton.setDisable(true);
        //opening the database just for better performance
        Datasource.getInstance().openUsers();
    }

    @FXML
    public void onLoginClick(ActionEvent event) throws IOException{

        try {
            ResultSet results = Datasource.getInstance().openUsers();
            //iterating through the table
            while (results.next ()){
                //We look into the database if there is a match with loogin and password
                if (results.getString("SPZ").equals(spz.getText()) && results.getString("Password").equals(password.getText())){
                    System.out.println("logged in");
                    userName = results.getString("Name");
                    userSurename = results.getString("Surename");
                    userSPZ = results.getString("SPZ");
                    userFuelType = results.getString("FuelType");
                    isLoginSuccesful = true;
                    break;
                }

            }
        } catch (SQLException e) {
            System.out.println("FATAL ERROR: couldn't connect to the USERS database");
        }
        //login is succesfull,change screen
        if (isLoginSuccesful == true) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("../Scenes/homescreen.fxml"));
            Parent newsceneparent = loader.load();
            Scene newscene = new Scene(newsceneparent);
            HomeScreenController controller = loader.getController();
            controller.setAllLabels(userName,userSurename,userSPZ,userFuelType);

            //This line gets the Stage information
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(newscene);
            window.show();
        }

    }

    @FXML
    public void handleKeyReleased(){
        String text = spz.getText();
        String text1 = password.getText();
        boolean disableButtons = text.isEmpty() || text.trim().isEmpty() || text1.isEmpty() || text1.trim().isEmpty() ;
        loginButton.setDisable(disableButtons);
    }

    public void onRegisterClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../Scenes/register.fxml"));
        Parent newsceneparent = loader.load();
        Scene newscene = new Scene(newsceneparent);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(newscene);
        window.show();
    }

}
