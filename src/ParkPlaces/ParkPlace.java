package ParkPlaces;
import Users.*;

/**
 * Abstraktna trieda parkoviska. Od tejto triedy dedia vsetky ostatne typy parkoviska.
 * Trieda obsahuje iba gettery a settery a funckie na vyratanie ceny parkovania.
 * Jedna sa o "kostru" parkoviska
 */
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


    /**
     * vyrata cenu pre bezneho pouzivatela
     * @param user typ pouzivatela
     * @param hours pocet hodin
     * @return sumu na zaplatenie
     */
    public abstract double calculatePrice(User user, int hours);

    /**
     * vyrata cenu pre Studenta pouzivatela
     * @param user typ pouzivatela
     * @param hours pocet hodin
     * @return sumu na zaplatenie
     */
    public abstract double calculatePrice(Student user, int hours);

    /**
     * vyrata cenu pre ZTP pouzivatela
     * @param user typ pouzivatela
     * @param hours pocet hodin
     * @return sumu na zaplatenie
     */
    public abstract double calculatePrice(DisabledPerson user, int hours);
}
