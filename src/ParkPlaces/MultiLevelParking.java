package ParkPlaces;
import Users.*;

public class MultiLevelParking extends ParkPlace {


    public MultiLevelParking( int numOfDPPSpots, int BaseHourPrice) {
        super(numOfDPPSpots, BaseHourPrice);
    }

    @Override
    public double calculatePrice(User user, int hours) {
        if (user.getTypeOfCar().equalsIgnoreCase("electric"))
            return 1;

        if (user.getTypeOfCar().equalsIgnoreCase("hybrid"))
            return (this.getBaseHourPrice() * hours) * 0.5;

        if (user.getTypeOfCar().equalsIgnoreCase("CNG") || user.getTypeOfCar().equalsIgnoreCase("LPG"))
            return -1;
        return (this.getBaseHourPrice() * hours);
    }

    @Override
    public double calculatePrice(Student user, int hours) {
        if (user.getTypeOfCar().equalsIgnoreCase("electric"))
            return 1;

        if (user.getTypeOfCar().equalsIgnoreCase("hybrid"))
            return (this.getBaseHourPrice() * hours) * 0.4;

        if (user.getTypeOfCar().equalsIgnoreCase("CNG") || user.getTypeOfCar().equalsIgnoreCase("LPG"))
            return -1;
        return ((this.getBaseHourPrice() * hours) * 0.8);
    }

    @Override
    public double calculatePrice(DisabledPerson user, int hours) {
        if (user.getTypeOfCar().equalsIgnoreCase("electric"))
            return 1;

        if (user.getTypeOfCar().equalsIgnoreCase("hybrid"))
            return (this.getBaseHourPrice() * hours) * 0.5;

        if (user.getTypeOfCar().equalsIgnoreCase("CNG") || user.getTypeOfCar().equalsIgnoreCase("LPG"))
            return (this.getBaseHourPrice() * 0.5) * (hours - 3) + 5;

        return (this.getBaseHourPrice() * 0.5) * (hours - 3);
    }
}
