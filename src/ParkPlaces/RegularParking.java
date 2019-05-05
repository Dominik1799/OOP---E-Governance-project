package ParkPlaces;

import Users.*;

/**
 * trieda obycajneho parkoviska
 */
public class RegularParking extends ParkPlace {
    public RegularParking(int numOfDPPSpots, int BaseHourPrice) {
        super(numOfDPPSpots, BaseHourPrice);
    }


    //rozne ceny pre typy pouzivatela a typ vozidla aktualneho pouzivatela.

    @Override
    public double calculatePrice(User user, int hours) {
        if (user.getTypeOfCar().equalsIgnoreCase("electric"))
            return 1;

        if (user.getTypeOfCar().equalsIgnoreCase("hybrid"))
           return (this.getBaseHourPrice() * hours) * 0.5;

        return (this.getBaseHourPrice() * hours);
    }

    @Override
    public double calculatePrice(Student user, int hours) {
        if (user.getTypeOfCar().equalsIgnoreCase("electric"))
            return 1;

        if (user.getTypeOfCar().equalsIgnoreCase("hybrid"))
            return ((this.getBaseHourPrice() * hours) * 0.5) * 0.8;

        return (this.getBaseHourPrice() * hours) * 0.8;
    }

    @Override
    public double calculatePrice(DisabledPerson user, int hours) {
        return 0;
    }
}
