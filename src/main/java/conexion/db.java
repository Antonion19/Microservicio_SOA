package conexion;

import java.sql.*;

public class db {
    Connection con;

    // Database connection parameters
    // IMPORTANT: Make sure your database name is 'bdsoan' as specified
    String url = "jdbc:mysql://localhost:3306/bdsoan";
    String user = "root";
    String pass = ""; // Often empty for default XAMPP/WAMP/MAMP root user without password

    /**
     * Establishes a connection to the database.
     * @return A Connection object if successful, null otherwise.
     */
    public Connection Conexion() {
        try {
            // Load the MySQL JDBC Driver (explicitly for older JDBC versions, though often not strictly needed for modern drivers)
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Establish the connection
            con = DriverManager.getConnection(url, user, pass);
            System.out.println("Database connection established successfully.");
        } catch (ClassNotFoundException e) {
            System.err.println("Error: MySQL JDBC Driver not found. Make sure the MySQL Connector/J JAR is in your project's libraries.");
            e.printStackTrace();
            con = null; // Ensure con is null on failure
        } catch (SQLException e) {
            System.err.println("Error connecting to the database. Check your URL, username, and password.");
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Error Code: " + e.getErrorCode());
            e.printStackTrace();
            con = null; // Ensure con is null on failure
        } catch (Exception e) {
            // Catch any other unexpected exceptions
            System.err.println("An unexpected error occurred during database connection.");
            e.printStackTrace();
            con = null; // Ensure con is null on failure
        }
        return con;
    }

    /**
     * Main method for testing the database connection.
     * Run this directly to check if the connection works.
     */
    public static void main(String[] args) {
        db conexion = new db();
        Connection con = conexion.Conexion(); // Attempt to get a connection

        if (con != null) {
            System.out.println("Connection successful to database: " + conexion.url.split("/")[3]); // Extract database name for output
            try {
                con.close(); // Close the connection after successful test
                System.out.println("Connection closed.");
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        } else {
            System.out.println("Error: Could not establish a database connection. Check logs for details.");
        }
    }
}