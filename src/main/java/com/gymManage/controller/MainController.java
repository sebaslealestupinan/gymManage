package com.gymManage.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MainController {

    @FXML private StackPane centerStack;
    @FXML private ToggleButton btnTuDia;
    @FXML private ToggleButton btnAvance;
    @FXML private ToggleButton btnAgenda;
    @FXML private Label lblNombreUsuario;
    @FXML private Label lblEstadoMembresia;

    private final ToggleGroup navGroup = new ToggleGroup();
    private final Map<String, Node> vistasCache = new HashMap<>();

    @FXML
    public void initialize() {
        btnTuDia.setToggleGroup(navGroup);
        btnAvance.setToggleGroup(navGroup);
        btnAgenda.setToggleGroup(navGroup);

        btnTuDia.setSelected(true);

        // Eventos de Navegación
        btnTuDia.setOnAction(e -> cambiarVista("/com/gymManage/ui/TuDiaView.fxml", "TU_DIA"));
        btnAvance.setOnAction(e -> cambiarVista("/com/gymManage/ui/AvanceView.fxml", "AVANCE"));
        btnAgenda.setOnAction(e -> cambiarVista("/com/gymManage/ui/AgendaView.fxml", "AGENDA"));

        // Carga inicial perezosa (TuDía)
        cambiarVista("/com/gymManage/ui/TuDiaView.fxml", "TU_DIA");
    }

    public void setDatosUsuario(String nombre, String estado) {
        lblNombreUsuario.setText(nombre);
        lblEstadoMembresia.setText("Estado: " + estado);
    }

    private void cambiarVista(String fxmlPath, String key) {
        try {
            if (!vistasCache.containsKey(key)) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                Node vista = loader.load();
                vistasCache.put(key, vista);
            }
            centerStack.getChildren().setAll(vistasCache.get(key));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}