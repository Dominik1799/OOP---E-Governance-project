package Controllers;

import javafx.event.ActionEvent;

import java.io.IOException;

/**
 * tento interface implementuje kazdy controller ktory ovlada pouzivatelsku cast programu
 * z kazdej sceny kde sa vie dostat bezny pouzivatel sa da preklikat na ine sceny,
 * preto ho musi mat kazdy takyto controller.
 */
public interface MenuInterface {

    /**
     * Odhlasi aktualneho pouzivatela a program prepne na login screen.
     * @param event
     * @throws IOException
     */
    public void onLogOutClick(ActionEvent event) throws IOException;

    /**
     * Prepne na scenu kde sa da kupit listok
     * @param event
     * @throws IOException
     */
    public void onBuyTicketClick(ActionEvent event) throws IOException;

    /**
     * Prepne na scenu kde sa da poziadat o schvalenie specialneho uctu
     * @param event
     * @throws IOException
     */
    public void onDiscReqClick(ActionEvent event) throws IOException;

    /**
     * prepne na domovsku obrazovku
     * @param event
     * @throws IOException
     */
    public void onOverviewClick(ActionEvent event) throws IOException;

    /**
     * prepne na spravu penazenky
     * @param event
     * @throws IOException
     */
    public void onWalletClick(ActionEvent event) throws IOException;
}
