package Users;

public class User implements user_interface {
    private String name;
    private String surename;
    private String typeOfCar;
    private String Carid;
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

    public User(String fname,String surename,String typeOfCar,String Carid){
        this.name = fname;
        this.surename = surename;
        this.typeOfCar = typeOfCar;
        this.Carid = Carid;
    }

//    public class Admin extends User implements user_interface {
//        public Admin(String fname, String surename, String typeOfCar, String Carid) {
//            super(fname, surename, typeOfCar, Carid);
//        }
//
//        public void acceptUser(String SPZ, String Type) {
//            Datasource.getInstance().updateUser(SPZ, Type);
//        }
//    }

}
