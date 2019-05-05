package Users;


/**
 * interface obsahujuci gettery a settery pre pouzivatelov
 */
public interface user_interface {
    public Double getCredit();


    public void setCredit(Double credit);


    public  String getName();

    public String getType();

    public void setFirstName(String name);

    public String getSurename();

    public void setSurename(String surename);

    public String getTypeOfCar();

    public String getCarID();

    public void setTypeOfCar(String typeOfCar);
}
