package Users;

/**
 * trieda Studenta pouzivatela
 */
public class Student extends User implements user_interface {
    public Student(String name, String surename, String typeOfCar,String Carid,Double credit) {
        super(name, surename, typeOfCar,Carid,credit);
    }

}
