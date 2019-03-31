//using Datasource class as a database manager using singleton design pattern



import java.sql.*;

public class Datasource {
    private static Datasource instance = new Datasource();

    private Datasource() {

    }

    public static Datasource getInstance() {
        return instance;
    }
    //FUNGUJE
    public ResultSet openUsers() {
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:users.db");
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
        String sql = "INSERT INTO users(SPZ,Password,Name,Surename,FuelType) VALUES(?,?,?,?,?)";
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:users.db");
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, SPZ);
            pstmt.setString(2, Password);
            pstmt.setString(3, Name);
            pstmt.setString(4, Surename);
            pstmt.setString(5, FuelType);
            pstmt.executeUpdate();
            conn.close();

        } catch (SQLException e) {
            System.out.println("FATAL ERROR: couldn't connect to the USERS database");
        }
    }
}
