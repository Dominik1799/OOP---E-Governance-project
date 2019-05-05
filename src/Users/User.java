package Users;

import Factories.Datasource;

import javax.xml.crypto.Data;

/**
 * Trieda pouzivatela. Od tejto triedy dedia ostatne typy pouzivatelov(student,ZTP a jeden druh admina)
 */
public class User implements user_interface {
    private String name;
    private String surename;
    private String typeOfCar;
    private String Carid;
    private Double credit;

    @Override
    public Double getCredit() {
        return this.credit;
    }

    @Override
    public void setCredit(Double credit) {
        this.credit = credit;
    }

    public  String getName() {
        return name;
    }

    //na zistenie typu osoby (vyuzivane v adminskom prehlade)
    public String getType(){
        Class pomoc = this.getClass();
        return pomoc.getName().replace("Users.","").replace("Person","");
    }

    public void setFirstName(String name) {
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

    public String getCarID(){
        return Carid;
    }

    public User(String fname,String surename,String typeOfCar,String Carid,Double credit){
        this.name = fname;
        this.surename = surename;
        this.typeOfCar = typeOfCar;
        this.Carid = Carid;
        this.credit = credit;
    }

    /**
     * zvysenie pouzivatelovho kreditu
     * @param value hodnota navysenia
     */
    public void updateCreditInc(Double value){
        this.credit = this.credit + value;
        Datasource.getInstance().updateCredit(this.credit,this.Carid);
    }

    /**
     * znizenie pouzivatelskeho kreditu
     * @param value hodnota znizenia
     */
    public void updateCreditDec(Double value){
        this.credit = this.credit - value;
        Datasource.getInstance().updateCredit(this.credit,this.Carid);
    }

}
