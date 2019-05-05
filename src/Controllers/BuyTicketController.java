package Controllers;

import Factories.Datasource;
import ParkPlaces.ParkPlace;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import Users.*;

/**
 * V tejto scene moze pouzivatel kupovat parkovacie listky
 */
public class BuyTicketController implements Initializable,MenuInterface {
    ObservableList<String> parking = FXCollections.observableArrayList();
    @FXML
    private ComboBox towns;
    @FXML
    private TextField hours;
    @FXML
    private Label cost,type,basePrice,warning,funds,balance;
    @FXML
    private Button pay;

    private Double priceToBePaid;

    User user;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            getParking();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        towns.setItems(parking);
        pay.setDisable(true);
    }

    public void setUser(User user){
        this.user = user;
    }

    /**
     * naplni sa dropdown-menu obsahujuce dostupne parkoviska (zoznam miest)
     * @throws SQLException
     */
    public void getParking() throws SQLException{
        ResultSet rs = Datasource.getInstance().openParking();
        while(rs.next()){
            this.parking.add(rs.getString("town"));
        }
    }


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

    /**
     * vyrata sa cena za aktualne zvolene hodnoty (mesto a pocet hodin)
     * ak je pouzivatel typu Disabled a parkovisko neobsahuje ziadne ZTP miesta, program vytvori varovnu spravu.
     * ak pouzivatel nema dostatok kreditu na zaplotenie, vytvori sa varovna sprava.
     * @throws SQLException
     */
    public void onOKclick() throws SQLException {
        ParkPlace parkPlace = Datasource.getInstance().findParking((String) towns.getValue());
        Class pomoc = parkPlace.getClass();
        int hour = Integer.parseInt(hours.getText());
        basePrice.setText(String.valueOf(parkPlace.getBaseHourPrice()));

        type.setText(pomoc.getName().replace("ParkPlaces.","").replace("Parking",""));
        if (this.user instanceof Student){
            cost.setText(String.format("%.2f",parkPlace.calculatePrice((Student) this.user,hour)) + " €");
        }

        else
        if (this.user instanceof DisabledPerson){
            cost.setText(String.format("%.2f",parkPlace.calculatePrice((DisabledPerson) this.user,hour)) + " €");

        }

        else {
            cost.setText(parkPlace.calculatePrice(this.user,hour) + " €");
        }
        if (parkPlace.getNumOfDPPSpots() == 0 && this.user instanceof DisabledPerson)
            warning.setText("Warning! This parking lot does not have exclusive places for Disabled people!");
        if (this.user.getCredit() < Double.parseDouble(cost.getText().replace(" €","")) ){
            funds.setText("Insufficient funds, please increase your account balance.");
            pay.setDisable(true);
            return;
        }
        funds.setText("");
        pay.setDisable(false);
        this.priceToBePaid = Double.parseDouble(cost.getText().replace(" €",""));
    }

    /**
     * tlacidlo na zaplatenie sumy a kupy parkovneho listka
     */
    public void onPayClick(){
        this.user.updateCreditDec(priceToBePaid);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Payment successful! You payed " + cost.getText());
        alert.showAndWait();
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

    @Override
    public void onBuyTicketClick(ActionEvent event) throws IOException {

    }

    public void onWalletClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../Scenes/wallet.fxml"));
        Parent newsceneparent = loader.load();
        Scene newscene = new Scene(newsceneparent);
        walletController controller = loader.getController();
        controller.setUser(this.user);
        //This line gets the Stage information
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(newscene);
        window.show();
    }
}
