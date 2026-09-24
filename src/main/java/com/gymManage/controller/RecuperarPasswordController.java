package com.gymManage.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class RecuperarPasswordController {

    @FXML private TextField txtCorreoRecuperacion;
    @FXML private Label lblMensaje;

    @FXML
    private void enviarCorreoRecuperacion() {
        String correo = txtCorreoRecuperacion.getText().trim();
        if (correo.isEmpty()) {
            lblMensaje.setStyle("-fx-text-fill: #dc2626;");
            lblMensaje.setText("Ingresa un correo electrónico.");
        } else {
            lblMensaje.setStyle("-fx-text-fill: #16a34a;");
            lblMensaje.setText("Instrucciones enviadas a tu correo.");
        }
    }

    @FXML
    private void volverAlLogin(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/gymManage/ui/login.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
            stage.setTitle("MetaSistema - Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}