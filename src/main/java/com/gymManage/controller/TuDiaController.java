package com.gymManage.controller;

import com.gymManage.model.Ejercicio;
import com.gymManage.service.GymService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class TuDiaController {

    @FXML private Label lblEnfoque;
    @FXML private ListView<String> listEjercicios;

    private final GymService service = new GymService();

    @FXML
    public void initialize() {
        // ID estático de ejemplo (debes pasar el ID del usuario autenticado)
        long idUsuario = 1L;

        service.obtenerRutinaUsuario(idUsuario)
                .thenAccept(rutinas -> Platform.runLater(() -> {
                    if (!rutinas.isEmpty()) {
                        var rutina = rutinas.get(0);
                        lblEnfoque.setText("Enfoque: " + rutina.getNombreEnfoque());
                        listEjercicios.getItems().clear();
                        for (Ejercicio e : rutina.getEjercicios()) {
                            listEjercicios.getItems().add(e.getNombre() + " - " + e.getSeries() + "x" + e.getRepeticiones());
                        }
                    } else {
                        lblEnfoque.setText("Sin rutina asignada para hoy");
                    }
                }))
                .exceptionally(ex -> {
                    Platform.runLater(() -> lblEnfoque.setText("Error al cargar la rutina"));
                    return null;
                });
    }
}