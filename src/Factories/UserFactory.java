package Factories;
import Users.*;

public class UserFactory {
    private static UserFactory instance = new UserFactory();
    private UserFactory(){

    }

    public static UserFactory getInstance(){
        return instance;
    }
    public User makeUser(String type,String name,String surename,String typeOfCar,String Carid,Double credit){
        if (type.equalsIgnoreCase("regular"))
            return new User(name, surename, typeOfCar, Carid, credit);
        if (type.equalsIgnoreCase("student"))
            return new Student(name, surename, typeOfCar, Carid,credit);
        if (type.equalsIgnoreCase("disabled"))
            return new DisabledPerson(name, surename, typeOfCar, Carid,credit);

        return null;
    }
}