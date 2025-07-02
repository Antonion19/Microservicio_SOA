package conexion;

import java.sql.*;

public class db {
    Connection con;

    public Connection Conexion() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Leer variables de entorno desde Railway
            String host = System.getenv("MYSQLHOST"); // mysql.railway.internal
            String port = System.getenv("MYSQLPORT"); // 3306
            String dbname = System.getenv("MYSQLDATABASE"); // railway
            String user = System.getenv("MYSQLUSER"); // root
            String pass = System.getenv("MYSQLPASSWORD"); // JSqwrs...

            String url = "jdbc:mysql://" + host + ":" + port + "/" + dbname
                         + "?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";

            con = DriverManager.getConnection(url, user, pass);
            System.out.println("✅ Conexión exitosa a la base de datos en Railway.");
        } catch (ClassNotFoundException e) {
            System.err.println("❌ Driver MySQL no encontrado.");
            e.printStackTrace();
            con = null;
        } catch (SQLException e) {
            System.err.println("❌ Error al conectar a la base de datos.");
            System.err.println("SQL State: " + e.getSQLState());
            System.err.println("Código de error: " + e.getErrorCode());
            e.printStackTrace();
            con = null;
        } catch (Exception e) {
            System.err.println("❌ Excepción inesperada al conectar.");
            e.printStackTrace();
            con = null;
        }
        return con;
    }
}
