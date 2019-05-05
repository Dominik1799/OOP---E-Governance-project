package Factories;//using Datasource class as a database manager using singleton design pattern
import ParkPlaces.*;


import java.sql.*;

/**
 * Trieda pouzivana iba na pracu s databazou, vyuziva SQLite systém.
 * Vsetky udaje su citane zo suboru users.db
 * tento subor obsahuje dve tabulky, jednu s pouzivatelmi, druhu s parkoviskami
 * Cela trieda je vytvorena ako singleton pre jednoduchost a istotu ze je vzdy vytvorena
 * iba jedna instancia
 */
public class Datasource {
    private Connection conn;
    private static Datasource instance = new Datasource();

    private Datasource() {
    }


    public static Datasource getInstance() {
        return instance;
    }

    /**
     * zatvori databazu
     * @throws SQLException ak je databaza poskodena alebo neexistujuca
     */
    public void closeConnection() throws SQLException{
        this.conn.close();
    }


    /**
     * otvori tabulku pouzivatelia
     * @return udaje z tabulky alebo null ak bola operacia neuspesna
     */
    public ResultSet openUsers() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:users.db");
            Statement statement = conn.createStatement();
            statement.execute("SELECT * FROM users");
            //statement.execute("INSERT INTO users (SPZ)" + "VALUES:('ahoj')");
            return statement.getResultSet();


        } catch (SQLException e) {
            System.out.println("FATAL ERROR: couldn't connect to the USERS database");
        }
        return null;
    }

    /**
     * otvori tabulku parkoviska
     * @return udaje z tabulky alebo null ak bola operacia neuspesna
     */
    public ResultSet openParking() {
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:users.db");
            Statement statement = conn.createStatement();
            statement.execute("SELECT * FROM parkplaces");
            //statement.execute("INSERT INTO users (SPZ)" + "VALUES:('ahoj')");
            return statement.getResultSet();


        } catch (SQLException e) {
            System.out.println("FATAL ERROR: couldn't connect to the USERS database");
        }
        return null;
    }


    /**
     * Vytvori noveho pouzivatela
     * @param SPZ  SPZ pouzivatela
     * @param Password heslo pouzivatela
     * @param Name meno pouzivatela
     * @param Surename priezvisko pouzivatela
     * @param FuelType typ vozidla pouzivatela
     */
    public void createUser(String SPZ,String Password,String Name,String Surename,String FuelType)  {
        String sql = "INSERT INTO users(SPZ,Password,Name,Surename,FuelType,Type,credit) VALUES(?,?,?,?,?,?,?)";
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:users.db");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, SPZ);
            pstmt.setString(2, Password);
            pstmt.setString(3, Name);
            pstmt.setString(4, Surename);
            pstmt.setString(5, FuelType);
            pstmt.setString(6, "regular");
            pstmt.setInt(7, 0);
            pstmt.executeUpdate();
            conn.close();

        } catch (SQLException e) {
            System.out.println("FATAL ERROR: couldn't connect to the USERS database");
        }
    }

    /**
     * vytvori ziadost od pouzivatela aby ju mohol admin schvalit.
     * @param type typ ziadost. 1 ak je to ziadost o studentsky ucet,2 ak je to ziadost o ZTP zlavu
     * @param SPZ SPZ pouzivatela pre orientaciu v tabulke
     */
    public void makeReq(int type,String SPZ){
        String sql = "UPDATE users "
                + "SET req = ? "
                + "WHERE SPZ = ?";
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:users.db");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            //Dominik je to tym prikazom samotnym. z nejakeho dovodu to nestiha prelozit. najdi iny prikaz
            pstmt.setInt(1, type);
            pstmt.setString(2, SPZ);
            int rowAffected = pstmt.executeUpdate();
//            PreparedStatement pstmt = conn.prepareStatement(sql);
//
//            pstmt.setString(1,String.valueOf(type));
//            pstmt.setString(2,SPZ);
//            pstmt.executeUpdate();
//            conn.close();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * najde parkovisko v danom meste
     * @param town vybrane mesto
     * @return vrati instanciu toho typu parkoviska, ake sa nachadza v danom meste
     * @throws SQLException ak je problem s databazou (je vymazana,otvorena)
     */
    public ParkPlace findParking(String town) throws SQLException {
        Connection conn = DriverManager.getConnection("jdbc:sqlite:users.db");
        Statement statement = conn.createStatement();
        statement.execute("SELECT * FROM parkplaces");
        ResultSet results = statement.getResultSet();
        while (results.next()){
            if (results.getString("town").equalsIgnoreCase(town)) {
                System.out.println("naslo sa parkovisko");
                break;
            }
        }
        String type = results.getString("type");
        int numOfDPP = results.getInt("DPP-places");
        int hourPrice = results.getInt("hourprice");
        conn.close();
        return ParkingFactory.getInstance().makeParkPlace(type,numOfDPP,hourPrice);
    }

    /**
     * danemu pouzivatelovi zmeni status (ci je student,ZTP,alebo normalny)
     * @param SPZ SPZ pouzivatela
     * @param Type typ ktory bude pouzivatelovi dany
     */
    public void updateUser(String SPZ,String Type){
        String sql = "UPDATE users "
                + "SET Type = ? "
                + "WHERE SPZ = ?";
        String sql1 = "UPDATE users "
                + "SET req = ? "
                + "WHERE SPZ = ?";
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:users.db");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            PreparedStatement pstmt1 = conn.prepareStatement(sql1);
            //Dominik je to tym prikazom samotnym. z nejakeho dovodu to nestiha prelozit. najdi iny prikaz
            pstmt.setString(1, Type);
            pstmt.setString(2, SPZ);
            pstmt1.setString(1, "");
            pstmt1.setString(2, SPZ);
            pstmt.executeUpdate();
            pstmt1.executeUpdate();
        }catch(SQLException e){
            System.out.println(e);
        }
    }

    /**
     * aktualizuje pouzivatelov kredit na ucte, je to spustene ked si pouzivatel dobije kredit
     * @param value
     * @param SPZ
     */
    public void updateCredit(Double value,String SPZ){
        String sql = "UPDATE users "
                + "SET credit = ? "
                + "WHERE SPZ = ?";
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:users.db");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            //Dominik je to tym prikazom samotnym. z nejakeho dovodu to nestiha prelozit. najdi iny prikaz
            pstmt.setDouble(1, value);
            pstmt.setString(2, SPZ);
            pstmt.executeUpdate();
        }catch(SQLException e){
            System.out.println(e);
        }
    }

    /**
     * vytvori nove parkovisko, funckiu pouziva iba admin
     * @param town mesto v ktorom parkovisko bude
     * @param price cena na hodinu
     * @param DPP pocet miest pre ZTP
     * @param type typ parkoviska
     */
    public void createParking(String town,Integer price,Integer DPP,String type)  {
        String sql = "INSERT INTO parkplaces(town,type,hourprice) VALUES(?,?,?)";
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:users.db");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, town);
            pstmt.setString(2, type);
            pstmt.setString(3, type);
            pstmt.executeUpdate();
            conn.close();

        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}

