package servicio;

import conexion.db;
import servicio.DatosAlumno;
import java.sql.*;
import java.util.Optional;

public class ValidadorAlumnoService {

    public Optional<DatosAlumno> validarAlumno(String nombreCompleto) {
        db database = new db();
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = database.Conexion();
            String sql = "SELECT u.utp_cod AS codigo, u.utp_nombre AS nombre, r.rol AS rol, " +
                         "rs.restric_motivo AS restriccion, s.sede AS sede " +
                         "FROM utp_bd u " +
                         "JOIN rol r ON u.utp_rol = r.rol_id " +
                         "JOIN restric rs ON u.utp_restric = rs.restric_id " +
                         "JOIN sede s ON u.utp_sede = s.sede_id " +
                         "WHERE u.utp_nombre = ?";
            ps = con.prepareStatement(sql);
            ps.setString(1, nombreCompleto);

            rs = ps.executeQuery();
            if (rs.next()) {
                DatosAlumno alumno = new DatosAlumno();
                alumno.setCodigo(rs.getString("codigo"));
                alumno.setNombres(rs.getString("nombre"));
                alumno.setRol(rs.getString("rol"));
                alumno.setRestriccion(rs.getString("restriccion"));
                alumno.setSede(rs.getString("sede"));
                return Optional.of(alumno);
            }
        } catch (Exception e) {
            System.err.println("Error al validar alumno: " + e.getMessage());
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException ignored) {}
            try { if (ps != null) ps.close(); } catch (SQLException ignored) {}
            try { if (con != null) con.close(); } catch (SQLException ignored) {}
        }

        return Optional.empty();
    }
}
