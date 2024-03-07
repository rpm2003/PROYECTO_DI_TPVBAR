package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class PrimaryController {

    @FXML
    private Button mesa1Button;
    @FXML
    private Button mesa2Button;
    @FXML
    private Button mesa3Button;
    @FXML
    private Button mesa4Button;
    @FXML
    private Button mesa5Button;
    @FXML
    private Button mesa6Button;
    @FXML
    private Button mesa7Button;
    @FXML
    private Button mesa8Button;
    @FXML
    private Button mesa9Button;
    @FXML
    private Button mesa10Button;

    private final HistorialMesas historial = new HistorialMesas();



    public void start(Stage primaryStage){
        mesa1Button.setStyle("-fx-background-image: url('/resources/mesas.png')");
        mesa2Button.setStyle("-fx-background-image: url('/resources/mesas.png')");
        mesa3Button.setStyle("-fx-background-image: url('/resources/mesas.png')");
        mesa4Button.setStyle("-fx-background-image: url('/resources/mesas.png')");
        mesa5Button.setStyle("-fx-background-image: url('/resources/mesas.png')");
        mesa6Button.setStyle("-fx-background-image: url('/resources/mesas.png')");
        mesa7Button.setStyle("-fx-background-image: url('/resources/mesas.png')");
        mesa8Button.setStyle("-fx-background-image: url('/resources/mesas.png')");
        mesa9Button.setStyle("-fx-background-image: url('/resources/mesas.png')");
        mesa10Button.setStyle("-fx-background-image: url('/resources/mesas.png')");
    }



    @FXML
    private void handleMesaClick(ActionEvent event) {
        Button btn = (Button) event.getSource();
        String buttonText = btn.getText();
        int mesaNumber = Integer.parseInt(buttonText.substring(5));
        openProductosView(mesaNumber);
        registrarMesaEnHistorial(mesaNumber);
    }


    private void openProductosView(int mesaNumber) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/producto.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            Scene scene = new Scene(root, 800, 600);
            stage.setScene(scene);

            stage.setTitle("Productos para Mesa " + mesaNumber);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void registrarMesaEnHistorial(int mesaNumber) {
        Date fechaApertura = new Date();
        Date fechaCierre = new Date();
        double totalFactura = 0.0;

        historial.insertarRegistro(mesaNumber, fechaApertura, fechaCierre, totalFactura);
    }


}
