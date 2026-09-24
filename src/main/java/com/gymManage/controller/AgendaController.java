package com.gymManage.controller;

import com.gymManage.model.Clase;
import com.gymManage.service.GymService;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AgendaController {

    @FXML private TableView<Clase> tablaClases;
    @FXML private TableColumn<Clase, String> colClase;
    @FXML private TableColumn<Clase, String> colHorario;
    @FXML private TableColumn<Clase, Integer> colCupos;

    private final GymService service = new GymService();
    private final ObservableList<Clase> listaClases = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        // Enlazar las columnas de la tabla con los atributos del modelo Clase.java
        colClase.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colHorario.setCellValueFactory(new PropertyValueFactory<>("horario"));
        colCupos.setCellValueFactory(new PropertyValueFactory<>("capacidadMaxima"));

        tablaClases.setItems(listaClases);

        cargarClases();
    }

    private void cargarClases() {
        // Llamada asíncrona a Supabase
        service.obtenerClasesDisponibles().thenAccept(clases -> {
            Platform.runLater(() -> {
                listaClases.clear();
                listaClases.addAll(clases);
            });
        }).exceptionally(ex -> {
            Platform.runLater(() -> {
                mostrarAlerta(Alert.AlertType.ERROR, "Error de Conexión", "No se pudieron cargar las clases disponibles.");
            });
            return null;
        });
    }

    @FXML
    private void inscribirseEnClase() {
        Clase seleccionada = tablaClases.getSelectionModel().getSelectedItem();

        if (seleccionada == null) {
            mostrarAlerta(Alert.AlertType.WARNING, "Selección requerida", "Por favor, selecciona una clase de la tabla primero.");
            return;
        }

        // Aquí puedes implementar el método en GymService para hacer un POST a una tabla 'reservas'
        // Por ahora simulamos el éxito de la reserva localmente:
        mostrarAlerta(Alert.AlertType.INFORMATION, "Reserva Confirmada",
                "Te has inscrito exitosamente en la clase de " + seleccionada.getNombre() + ".");
    }

    private void mostrarAlerta(Alert.AlertType tipo, String titulo, String mensaje) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}