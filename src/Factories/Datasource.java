package Factories;//using Datasource class as a database manager using singleton design pattern
import ParkPlaces.*;


import java.sql.*;

public class Datasource {
    private Connection conn;
    private static Datasource instance = new Datasource();

    private Datasource() {
//        try {
//            conn = DriverManager.getConnection("jdbc:sqlite:users.db");
//        } catch (SQLException e){
//            System.out.println("ajaaj");
//        }
    }


    public static Datasource getInstance() {
        return instance;
    }

    public void closeConnection() throws SQLException{
        this.conn.close();
    }
    //FUNGUJE
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


    //FUNGUJE
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
}

