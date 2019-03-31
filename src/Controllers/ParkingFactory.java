package Controllers;
import ParkPlaces.*;

public class ParkingFactory {
    private static ParkingFactory instance = new ParkingFactory();
    private ParkingFactory(){

    }

    public static ParkingFactory getInstance(){
        return instance;
    }
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
