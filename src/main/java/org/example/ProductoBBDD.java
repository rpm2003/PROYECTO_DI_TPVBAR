package org.example;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoBBDD {
    private static final String INSERT_PRODUCTO = "INSERT INTO productos (nombre, precio) VALUES (?, ?)";
    private static final String SELECT_ALL_PRODUCTOS = "SELECT * FROM productos";

    public void guardarProducto(Producto producto) {
        try (Connection connection = BBDD.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_PRODUCTO)) {
            preparedStatement.setString(1, producto.getNombre());
            preparedStatement.setDouble(2, producto.getPrecio());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Producto> obtenerProductos() {
        List<Producto> productos = new ArrayList<>();
        try (Connection connection = BBDD.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(SELECT_ALL_PRODUCTOS)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String nombre = resultSet.getString("nombre");
                double precio = resultSet.getDouble("precio");
                productos.add(new Producto(id, nombre, precio, null));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }
}