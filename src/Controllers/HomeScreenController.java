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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeScreenController implements Initializable,MenuInterface {

    private User user;
    @FXML
    private Label Name,Surename,Carid,Ftype,Status,Credit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void setAllLabels(User user){
        Class pomoc = user.getClass();
        Name.setText(user.getName());
        Surename.setText(user.getSurename());
        Carid.setText(user.getCarID());
        Ftype.setText(user.getTypeOfCar());
        Status.setText(pomoc.getName().replace("Users.","").replace("Person",""));
        Credit.setText(user.getCredit() + "€");
        this.user = user;
    }


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
    public void onaaClick(){
        if (user.getClass() == Student.class) System.out.println("null");
        System.out.println(user.getName());
        System.out.println(user.getTypeOfCar());
        System.out.println(user.getSurename());
    }

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
    public void onOverviewClick(ActionEvent event) {

    }

    @Override
    public void onWalletClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../Scenes/wallet.fxml"));
        Parent newsceneparent = loader.load();
        Scene newscene = new Scene(newsceneparent);
        walletController controller = loader.getController();
        controller.setUser(this.user);
        controller.setLabel();
        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(newscene);
        window.show();
    }
}
