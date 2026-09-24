package com.gymManage.controller;

import com.gymManage.dao.UsuarioDAO;
import com.gymManage.model.Usuario;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CrearUsuarioController {

    // Declaración de las variables inyectadas desde el FXML
    @FXML private ComboBox<String> cmbRolRegistro;
    @FXML private TextField txtNombreCompleto;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtUsuarioNuevo;
    @FXML private PasswordField txtPasswordNueva;
    @FXML private Label lblMensajeRegistro;

    // Instancia del DAO (corrije el error de llamarlo con mayúscula inicial)
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML
    private void registrarUsuario() {
        String rol = cmbRolRegistro.getValue();
        String nombre = txtNombreCompleto.getText().trim();
        String correo = txtCorreo.getText().trim();
        String usuarioStr = txtUsuarioNuevo.getText().trim();
        String password = txtPasswordNueva.getText().trim();

        // Validación de campos vacíos
        if (rol == null || nombre.isEmpty() || correo.isEmpty() || usuarioStr.isEmpty() || password.isEmpty()) {
            lblMensajeRegistro.setStyle("-fx-text-fill: #dc2626;");
            lblMensajeRegistro.setText("Todos los campos son obligatorios.");
            return;
        }

        // Creación del objeto usuario
        Usuario nuevoUsuario = new Usuario(rol, nombre, correo, usuarioStr, password);

        // Llamada asíncrona al DAO
        usuarioDAO.crearUsuario(nuevoUsuario).thenAccept(guardado -> {
            // Actualización de la UI en el hilo principal de JavaFX
            Platform.runLater(() -> {
                if (guardado) {
                    lblMensajeRegistro.setStyle("-fx-text-fill: #16a34a;");
                    lblMensajeRegistro.setText("¡Usuario registrado con éxito!");
                    limpiarCampos();
                } else {
                    lblMensajeRegistro.setStyle("-fx-text-fill: #dc2626;");
                    lblMensajeRegistro.setText("Error al guardar en la base de datos.");
                }
            });
        });
    }

    @FXML
    private void volverAlLogin(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/com/gymManage/ui/login.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
            stage.setTitle("MetaSistema - Login");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método auxiliar para vaciar el formulario tras el registro
    private void limpiarCampos() {
        cmbRolRegistro.setValue(null);
        txtNombreCompleto.clear();
        txtCorreo.clear();
        txtUsuarioNuevo.clear();
        txtPasswordNueva.clear();
    }
}