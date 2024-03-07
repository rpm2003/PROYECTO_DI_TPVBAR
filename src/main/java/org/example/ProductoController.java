package org.example;

import javafx.fxml.FXML;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.view.JasperViewer;

public class ProductoController {

    @FXML
    private ListView<Producto> listaProductos;

    @FXML
    private ListView<Producto> listaSeleccionados;

    @FXML
    private TextField totalTextField;

    private ObservableList<Producto> productosDisponibles;
    private ObservableList<Producto> productosSeleccionados;



    public void initialize() {
        productosDisponibles = FXCollections.observableArrayList(productoBBDD.obtenerProductos());
        productosDisponibles = FXCollections.observableArrayList(
                new Producto(1, "Café", 1.2, "https://www.shutterstock.com/image-photo/coffee-cup-saucer-teaspoon-pack-600nw-2337186769.jpg"),
                new Producto(2, "Vino", 1.5, "https://static.vecteezy.com/system/resources/previews/016/475/460/original/transparent-dark-wine-bottle-with-blank-label-and-burgundy-foil-capsule-seal-and-glass-png.png"),
                new Producto(3, "Cerveza" , 1.2, "https://img.freepik.com/foto-gratis/cerveza-barril-png-taza_53876-96732.jpg"),
                new Producto(3, "Cocacola", 1.7, "https://distriortiz.com/cdn/shop/products/527.png?v=1610491419"),
                new Producto(4, "Fanta", 1.7, "https://pngfre.com/wp-content/uploads/Fanta-15.png"),
                new Producto(5,"Nestea",1.7, "https://sportavern.com/wp-content/uploads/productosImgs2Fbebida-nestea-33cl2.png"),
                new Producto(6, "Aquarius", 1.7, "https://sportavern.com/wp-content/uploads/productosImgs2Fbebida-aquarius-limon-33cl.png"),
                new Producto(7, "Agua", 1.0, "https://upload.wikimedia.org/wikipedia/commons/thumb/f/fe/Botella_de_Agua_font_vella.png/170px-Botella_de_Agua_font_vella.png"),
                new Producto(8, "Bocadillo Jamon", 5.0, "https://minimarketformentera.com/wp-content/uploads/2021/06/Entrepa-Pernil-scaled.jpg"),
                new Producto(9, "Bocadillo York", 3.5, "https://lh3.googleusercontent.com/proxy/mpMZyWw5lsjda9-iytcuLNfhd_CxYqXD1SwV-aZROzQ0oJ7RATvCcGdygZCOvNmJ0hEm6qN9OPX_GrX8I-YCK-4ujh0ZN8qXMMA-sXYM89omOhcdFcpedtk9eoNeGkCneKkx6ipuyA"),
                new Producto(10, "Bocadillo Tortilla", 3.5, "https://www.demoslavueltaaldia.com/sites/default/files/bocadillo-tortilla-variada.jpg"),
                new Producto(11, "Bocadillo Lomo/Queso" , 5.0, "https://vilocolo.com/wp-content/uploads/2020/11/Bocadillo-lomo-queso.jpg"),
                new Producto(12, "Bocadillo Queso", 4.5, "https://www.jamonesleyfam.com/wp-content/uploads/2021/04/bocadillos_queso.jpg"),
                new Producto(13, "Bocadillo chorizo", 4.5, "https://www.shutterstock.com/image-photo/spanish-chorizo-sandwich-isolated-on-600nw-51935887.jpg"),
                new Producto(14, "Bocadillo salchichon", 4.5, "https://lh3.googleusercontent.com/proxy/2pHvd3yFyQdI3DFkncwr--d04wrEd6YnnbOYpKr4fXyjDjXfeMJ0plykCUKAF5JTSLYviE0SU12PlyqjsTbbtNlvmgDTXBXgJq693Nh5_VFds0eQ8OGaUjGPwUdC5lBM2BVd9-YslUWlFF-o")



        );
        listaProductos.setItems(productosDisponibles);
        productosSeleccionados = FXCollections.observableArrayList();
        listaSeleccionados.setItems(productosSeleccionados);

        listaProductos.setCellFactory(param -> new ListCell<Producto>() {


            @Override
            protected void updateItem(Producto p, boolean empty) {
                super.updateItem(p, empty);

                if (empty || p == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(p.toString());
                    setGraphic(null);

                    if (p.getUrl() != null && !p.getUrl().isEmpty()) {
                        Image image = new Image(p.getUrl());
                        ImageView imageView = new ImageView(image);
                        imageView.setFitHeight(50);
                        imageView.setFitWidth(50);
                        setGraphic(imageView);
                    }
                }
            }
        });
    }

    @FXML
    private void agregarProducto() {
        Producto seleccionado = listaProductos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            productosSeleccionados.add(seleccionado);
            calcularTotalProductos();
            productoBBDD.guardarProducto(seleccionado);
        }
    }

    @FXML
    private void eliminarProducto() {
        Producto seleccionado = listaProductos.getSelectionModel().getSelectedItem();
        if (seleccionado != null) {
            productosSeleccionados.remove(seleccionado);
            calcularTotalProductos();
        }
    }

    @FXML
    private void generarFactura() {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("cliente", "Nombre del Cliente");

        try {
            Connection conexion = BBDD.getConnection();

            String reportePath = "JRT_TPVBAR.jrxml";

            JasperPrint print = JasperFillManager.fillReport(reportePath, parametros, conexion);

            JasperViewer.viewReport(print, false);
        } catch (JRException | SQLException e) {
            e.printStackTrace();
        }
        listaSeleccionados.getItems().clear();
        totalTextField.clear();

    }

    @FXML
    private void volver() {
        listaSeleccionados.getScene().getWindow().hide();
    }

    @FXML
    private void calcularTotalProductos() {
        double total = 0.0;
        for (Producto producto : productosSeleccionados) {
            total += producto.getPrecio();
        }
        totalTextField.setText(String.valueOf(total));
    }

    private final ProductoBBDD productoBBDD = new ProductoBBDD();


    @FXML
    private void generarHistorico() {
        try {
            Connection conexion = BBDD.getConnection();


            String reportePath = "JRT_TPVBAR.jrxml";


            Map<String, Object> parametros = new HashMap<>();

            JasperPrint print = JasperFillManager.fillReport(reportePath, parametros, conexion);

            JasperViewer.viewReport(print, false);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }
}