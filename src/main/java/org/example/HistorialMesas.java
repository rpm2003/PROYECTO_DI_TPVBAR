package org.example;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class HistorialMesas {

    public void insertarRegistro(int numeroMesa, Date fechaApertura, Date fechaCierre, double totalFactura) {
        String query = "INSERT INTO historial_mesas (numero_mesa, fecha_apertura, fecha_cierre, total_factura) VALUES (?, ?, ?, ?)";

        try (Connection conexion = BBDD.getConnection();
             PreparedStatement pstmt = conexion.prepareStatement(query)) {

            pstmt.setInt(1, numeroMesa);
            pstmt.setTimestamp(2, new Timestamp(fechaApertura.getTime()));
            pstmt.setTimestamp(3, new Timestamp(fechaCierre.getTime()));
            pstmt.setDouble(4, totalFactura);

            pstmt.executeUpdate();

            System.out.println("Registro insertado en el historial de mesas.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
