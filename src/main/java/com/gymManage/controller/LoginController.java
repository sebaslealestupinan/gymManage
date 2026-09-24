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
import java.util.Objects;

public class LoginController {

    @FXML private ComboBox<String> cmbRol;
    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtPassword;
    @FXML private Label lblMensaje;

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    @FXML
    private void login() {
        String rol = cmbRol.getValue();
        String usuario = txtUsuario.getText().trim();
        String password = txtPassword.getText().trim();

        if (rol == null || usuario.isEmpty() || password.isEmpty()) {
            lblMensaje.setStyle("-fx-text-fill: #dc2626;");
            lblMensaje.setText("Por favor diligencia todos los campos.");
            return;
        }

        lblMensaje.setStyle("-fx-text-fill: #2563eb;");
        lblMensaje.setText("Autenticando...");

        // Llamada asíncrona a Supabase
        usuarioDAO.autenticarUsuario(usuario, password, rol).thenAccept(usuarioAutenticado -> {
            Platform.runLater(() -> {
                if (usuarioAutenticado != null) {
                    lblMensaje.setStyle("-fx-text-fill: #16a34a;");
                    lblMensaje.setText("¡Acceso concedido!");
                    cargarDashboard(usuarioAutenticado);
                } else {
                    lblMensaje.setStyle("-fx-text-fill: #dc2626;");
                    lblMensaje.setText("Credenciales incorrectas o rol inválido.");
                }
            });
        });
    }

    private void cargarDashboard(Usuario usuario) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/gymManage/ui/MainView.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la vista principal para pasarle los datos
            MainController mainController = loader.getController();
            mainController.setDatosUsuario(usuario.getNombreCompleto(), "Plan Premium (" + usuario.getRol() + ")");

            // Cambiar la escena actual usando el nodo lblMensaje para obtener el Stage
            Stage stage = (Stage) lblMensaje.getScene().getWindow();
            stage.getScene().setRoot(root);
            stage.setTitle("MetaSistema - Dashboard " + usuario.getRol());

        } catch (IOException e) {
            e.printStackTrace();
            lblMensaje.setStyle("-fx-text-fill: #dc2626;");
            lblMensaje.setText("Error crítico al cargar el dashboard.");
        }
    }

    @FXML
    private void irACrearCuenta(ActionEvent event) {
        cambiarPantalla(event, "/com/gymManage/ui/crear_usuario.fxml", "MetaSistema - Crear Cuenta");
    }

    @FXML
    private void olvidePassword(ActionEvent event) {
        cambiarPantalla(event, "/com/gymManage/ui/recuperar_password.fxml", "MetaSistema - Recuperar Contraseña");
    }

    private void cambiarPantalla(ActionEvent event, String rutaFxml, String titulo) {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(rutaFxml)));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.getScene().setRoot(root);
            stage.setTitle(titulo);
        } catch (IOException e) {
            e.printStackTrace();
            lblMensaje.setStyle("-fx-text-fill: #dc2626;");
            lblMensaje.setText("Error al cargar la pantalla.");
        }
    }
}