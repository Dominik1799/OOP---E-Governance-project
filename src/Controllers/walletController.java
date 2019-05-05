package Controllers;

import Users.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Scena kde pouzivatel doplna svoju penazenku
 */
public class walletController implements MenuInterface, Initializable {
    private User user;
    @FXML
    private Label funds;
    @FXML
    private TextField money;

    public void setUser(User user){
        this.user = user;
    }

    public void setLabel(){
        funds.setText(String.valueOf(this.user.getCredit()));
    }

    @Override
    public void onLogOutClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../Scenes/login.fxml"));
        Parent loginScreen = loader.load();
        Scene loginScene = new Scene(loginScreen);

        //This line gets the Stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(loginScene);
        window.show();
    }

    @Override
    public void onBuyTicketClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../Scenes/buyTicket.fxml"));
        Parent newsceneparent = loader.load();
        Scene newscene = new Scene(newsceneparent);
        BuyTicketController controller = loader.getController();
        controller.setUser(this.user);
        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(newscene);
        window.show();
    }

    @Override
    public void onDiscReqClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../Scenes/DiscountReq.fxml"));
        Parent newsceneparent = loader.load();
        Scene newscene = new Scene(newsceneparent);
        DiscountReqController controller = loader.getController();
        controller.setUser(this.user);
        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(newscene);
        window.show();
    }

    @Override
    public void onOverviewClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../Scenes/homescreen.fxml"));
        Parent newsceneparent = loader.load();
        Scene newscene = new Scene(newsceneparent);
        HomeScreenController controller = loader.getController();
        controller.setAllLabels(this.user);

        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(newscene);
        window.show();
    }

    public void onOKclick(){
        Double userCreditRaise = Double.parseDouble(money.getText().replace(" €",""));
        this.user.updateCreditInc(userCreditRaise);
        funds.setText(String.valueOf(this.user.getCredit()));
    }

    public void onWalletClick(ActionEvent event) throws IOException {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
