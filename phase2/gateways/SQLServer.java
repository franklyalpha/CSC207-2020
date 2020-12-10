package gateways;

import java.io.*;
import java.sql.*;
import java.util.ArrayList;


public class SQLServer {
    static final String WRITE_OBJECT_SQL = "INSERT INTO java_objects(name, object_value) VALUES (?, ?)";
    static final String READ_OBJECT_SQL = "SELECT object_value FROM java_objects WHERE name = ?";
    static final private String username = "csc207@csc207";
    static final private String password = "group_0168";
    static final private String instanceURL = "jdbc:sqlserver://csc207.database.windows.net:1433;database=csc207;user=csc207@csc207;password=group_0168;encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;";
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

    public static Connection getConnection() throws Exception {
        Connection conn = DriverManager.getConnection(instanceURL, username, password);
        return conn;
    }

    public static void writeJavaObject(Connection conn, Object object) throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(bos);
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
        } finally {
            try {
                bos.close();
            } catch (IOException ex){
                // ignore
            }
        }

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
        System.out.println(className + " received from cloud!");
        return object;
    }
}
