package ParkPlaces;
import Users.*;

public abstract class ParkPlace {

    private int numOfDPPSpots;
    private double BaseHourPrice;


    public ParkPlace(int numOfDPPSpots,int BaseHourPrice){

        this.numOfDPPSpots = numOfDPPSpots;
        this.BaseHourPrice = BaseHourPrice;

    }


    public int getNumOfDPPSpots() {
        return numOfDPPSpots;
    }

    public void setNumOfDPPSpots(int numOfDPPSpots) {
        this.numOfDPPSpots = numOfDPPSpots;
    }

    public double getBaseHourPrice() {
        return BaseHourPrice;
    }

    public void setBaseHourPrice(int baseHourPrice) {
        BaseHourPrice = baseHourPrice;
    }



    public abstract double calculatePrice(User user, int hours);
    public abstract double calculatePrice(Student user, int hours);
    public abstract double calculatePrice(DisabledPerson user, int hours);
}
