package gateways;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;

/**
 * SQLServer to save/read data to/from server.
 */

public class SQLServer {
    static final String WRITE_OBJECT_SQL = "INSERT INTO java_objects(name, object_value) VALUES (?, ?)";
    static final String READ_OBJECT_SQL = "SELECT object_value FROM java_objects WHERE name = ?";
    static final private String username = "csc207@csc207";
    static final private String password = "group_0168";
    static final private String instanceURL = "jdbc:sqlserver://csc207.database.windows.net:1433;database=csc207;user=csc207@csc207;password=group_0168;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";

    /**
     * Check connection with azure server.
     */
    public static void init(){
        Connection conn = null;
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(instanceURL, username, password);
            if (conn != null){
                System.out.println("Connected to database: csc207");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (conn != null){
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    /**
     * Establish a connection to SQL server.
     * @return connection to the SQL server
     * @throws Exception when cannot connect
     */
    public static Connection getConnection() throws Exception {
        Connection conn = DriverManager.getConnection(instanceURL, username, password);
        return conn;
    }

    /**
     * Save java object to SQL server.
     * @param conn a connection to SQL server.
     * @param object the java object to save and upload to server.
     * @throws Exception when there's error writing java object
     */
    public static void writeJavaObject(Connection conn, Object object) throws Exception {
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            ObjectOutputStream out = new ObjectOutputStream(bos);
            out.writeObject(object);
            out.flush();
            byte[] objBytes = bos.toByteArray();
            String className = object.getClass().getName();
            PreparedStatement pstmt = conn.prepareStatement(WRITE_OBJECT_SQL);
            // set input parameters
            pstmt.setString(1, className);
            pstmt.setObject(2, objBytes);
            pstmt.executeUpdate();
            pstmt.execute();

            pstmt.close();
            System.out.println(className + " saved in cloud!");
        }

    }

    /**
     * Read java object from SQL server.
     * @param conn a connection to SQL server.
     * @param name the actual class name of the java object to fetch from the server.
     * @return the java object we fetched from server.
     * @throws Exception when there's error reading java object.
     */

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
        System.out.println(className + " received from cloud!");
        return object;
    }
}
