package Users;
public class User implements user_interface {
    private String name;
    private String surename;
    private String typeOfCar;
    private String Carid;

    public  String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurename() {
        return surename;
    }

    public void setSurename(String surename) {
        this.surename = surename;
    }

    public String getTypeOfCar() {
        return typeOfCar;
    }

    public void setTypeOfCar(String typeOfCar) {
        this.typeOfCar = typeOfCar;
    }



    public User(String name,String surename,String typeOfCar,String Carid){
        this.name = name;
        this.surename = surename;
        this.typeOfCar = typeOfCar;
        this.Carid = Carid;
    }




}
