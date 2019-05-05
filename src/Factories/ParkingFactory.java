package Factories;
import ParkPlaces.*;


/**
 * Tato trieda sa stara o vytvaranie instancie takeho parkoviska, akeho sa pozaduje.
 * Cela trieda je vytvorena podla Factory-navrhoveho vzoru a zaroven je to singleton
 */
public class ParkingFactory {
    private static ParkingFactory instance = new ParkingFactory();
    private ParkingFactory(){

    }

    public static ParkingFactory getInstance(){
        return instance;
    }

    /**
     * funkcia ktora vytvori pozadovany typ parkoviska
     * @param type pozadovany typ parkoviska
     * @param numOfDPP pocet ZTP miest
     * @param price cena na hodinu
     * @return instanciu jedneho typu parkoviska alebo null ak nastala chyba a pozadovane parkovisko neexistuje
     */
    public ParkPlace makeParkPlace(String type, int numOfDPP, int price){
        if (type.equalsIgnoreCase("under"))
            return new UndergroundParking(numOfDPP,price);
        if (type.equalsIgnoreCase("multi"))
            return new MultiLevelParking(numOfDPP,price);
        if (type.equalsIgnoreCase("reg"))
            return new RegularParking(numOfDPP,price);

        return null;
    }
}
