package gateways;

import java.io.*;
import java.sql.*;

import java.util.logging.Level;
import java.util.logging.Logger;

public class MySQL {

    public MySQL() throws Exception {
        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        String URL = "jdbc:odbc:dbname";
        Connection dbConn = DriverManager.getConnection(URL, "root", "");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        byte[] employeeAsBytes = baos.toByteArray();
        PreparedStatement pstmt = dbConn
                .prepareStatement("INSERT INTO EMPLOYEE (emp) VALUES(?)");
        ByteArrayInputStream bais = new ByteArrayInputStream(employeeAsBytes);
        pstmt.setBinaryStream(1, bais, employeeAsBytes.length);
        pstmt.executeUpdate();
        pstmt.close();
        Statement stmt = dbConn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT emp FROM Employee");
        while (rs.next()) {
            byte[] st = (byte[]) rs.getObject(1);
            ByteArrayInputStream baip = new ByteArrayInputStream(st);
            ObjectInputStream ois = new ObjectInputStream(baip);
        }
        stmt.close();
        rs.close();
        dbConn.close();
    }
}
