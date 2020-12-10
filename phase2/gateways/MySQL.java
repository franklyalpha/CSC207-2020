package gateways;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;


public class MySQL {
    static final String WRITE_OBJECT_SQL = "INSERT INTO java_objects(name, object_value) VALUES (?, ?)";
    static final String READ_OBJECT_SQL = "SELECT object_value FROM java_objects WHERE name = ?";
    static final String CREATE_TABLE_SQL = "CREATE TABLE IF NOT EXISTS java_objects (id INT AUTO_INCREMENT, " +
            "name varchar(128), object_value BLOB, primary key (id))";
    static final private String username = "newuser";
    static final private String password = "password";

    public MySQL() {
        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/csc207", username, password);
            stmt = conn.createStatement();
            stmt.executeUpdate(CREATE_TABLE_SQL);
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null){
                    stmt.close();
                }
            } catch (SQLException se) {
                // Noting
            }
            try {
                if (conn != null){
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }

    }

    public static Connection getConnection() throws Exception {
        String url = "jdbc:mysql://localhost:3306/csc207";
        Connection conn = DriverManager.getConnection(url, username, password);
        if (conn != null){
            System.out.println("Connected to database: csc207");
        }
        return conn;
    }

    public static void writeJavaObject(Connection conn, Object object) throws Exception {
        String className = object.getClass().getName();
        PreparedStatement pstmt = conn.prepareStatement(WRITE_OBJECT_SQL, java.sql.Statement.RETURN_GENERATED_KEYS);

        // set input parameters
        pstmt.setString(1, className);
        pstmt.setObject(2, object);
        pstmt.executeUpdate();

        // get the generated key for the id
        ResultSet rs = pstmt.getGeneratedKeys();

        rs.close();
        pstmt.close();
        System.out.println(className + " saved!");
    }

    public static Object readJavaObject(Connection conn, String name) throws Exception {
        PreparedStatement pstmt = conn.prepareStatement(READ_OBJECT_SQL);
        pstmt.setString(1, name);
        ResultSet rs = pstmt.executeQuery();
        ArrayList<Object> objs = new ArrayList<>();
        while (rs.next()){
            objs.add((rs.getObject(1)));
        }
        ByteArrayInputStream in = new ByteArrayInputStream((byte[]) objs.get(objs.size() - 1));
        ObjectInputStream is = new ObjectInputStream(in);
        Object object = is.readObject();
        String className = object.getClass().getName();

        rs.close();
        pstmt.close();
        System.out.println(className + " GET");
        return object;
    }
}
