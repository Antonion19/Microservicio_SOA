package servicio;

import conexion.db;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.*;

@Path("/validarAlumno")
public class ValidadorAlumnoResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response validarAlumno(DatosAlumno datos) {
        if (datos == null) {
        System.out.println("DatosAlumno es null");
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"error\":\"JSON no válido o datos nulos\"}")
                .build();
        }
        
        String nombres = datos.getNombres();
        String apellidoPaterno = datos.getApellidoPaterno();
        String apellidoMaterno = datos.getApellidoMaterno();

        String fullName = nombres + " " + apellidoPaterno + " " + apellidoMaterno;

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            db database = new db();
            conn = database.Conexion();

            if (conn == null) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("{\"error\":\"No se pudo conectar a la base de datos\"}")
                        .build();
            }

            String sql = 
            "SELECT u.utp_cod AS codigo, u.utp_nombre AS nombre, r.rol AS rol, " +
            "rs.restric_motivo AS restriccion, s.sede AS sede " +
            "FROM utp_bd u " +
            "JOIN rol r ON u.utp_rol = r.rol_id " +
            "JOIN restric rs ON u.utp_restric = rs.restric_id " +
            "JOIN sede s ON u.utp_sede = s.sede_id " +
            "WHERE u.utp_nombre = ?";


            stmt = conn.prepareStatement(sql);
            stmt.setString(1, fullName);
            rs = stmt.executeQuery();

            if (rs.next()) {
                String responseJson = String.format(
                    "{ \"codigoUTP\": \"%s\", \"rol\": \"%s\", \"restriccion\": \"%s\", \"sede\": \"%s\" }",
                    rs.getString("codigo"),
                    rs.getString("rol"),
                    rs.getString("restriccion"),
                    rs.getString("sede")
                );

                return Response.ok(responseJson, MediaType.APPLICATION_JSON).build();
            } else {
                String jsonVacio = "{ \"codigoUTP\": \"\", \"rol\": \"\", \"restriccion\": \"\", \"sede\": \"\" }";
                return Response.ok(jsonVacio, MediaType.APPLICATION_JSON).build();
            }


        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\":\"Error en el servidor: " + e.getMessage() + "\"}")
                    .build();
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
