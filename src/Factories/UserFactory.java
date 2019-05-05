package Factories;
import Users.*;

/**
 * Tato trieda sa stara o vytvaranie instancie takeho pouzivatela, akeho sa pozaduje.
 * Cela trieda je vytvorena podla Factory-navrhoveho vzoru a zaroven je to singleton
 */
public class UserFactory {
    private static UserFactory instance = new UserFactory();
    private UserFactory(){

    }

    public static UserFactory getInstance(){
        return instance;
    }

    /**
     * Funckia ktora vrati pozadovany typ pouzivatela na zaklade parametrov
     * @param type typ pouzivatela
     * @param name meno pouzivatela
     * @param surename priezvisko pouzivatela
     * @param typeOfCar typ vozidla pouzivatela
     * @param Carid SPZ pouzivatela
     * @param credit aktualny kredit pouzivatela
     * @return instanciu konkretneho typu pouzivatela alebo null ak nastane chyba
     */
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