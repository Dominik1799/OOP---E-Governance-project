package Controllers;

import javafx.event.ActionEvent;

import java.io.IOException;

public interface MenuInterface {
    public void onLogOutClick(ActionEvent event) throws IOException;
    public void onBuyTicketClick(ActionEvent event) throws IOException;
    public void onDiscReqClick(ActionEvent event) throws IOException;
    public void onOverviewClick(ActionEvent event) throws IOException;
    public void onWalletClick(ActionEvent event) throws IOException;
}
