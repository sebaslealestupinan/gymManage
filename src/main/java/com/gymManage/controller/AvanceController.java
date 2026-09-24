package com.gymManage.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;

import java.util.concurrent.CompletableFuture;

public class AvanceController {

    @FXML private BarChart<String, Number> barChartAsistencia;

    @FXML
    public void initialize() {
        cargarEstadisticas();
    }

    private void cargarEstadisticas() {
        // Ejecución asíncrona para no congelar la UI al consultar datos
        CompletableFuture.supplyAsync(() -> {

            // Aquí llamarías a tu GymService para consultar la tabla de asistencias/progreso.
            // Para este ejemplo, construimos la serie de datos estadísticos:
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Días entrenados");

            series.getData().add(new XYChart.Data<>("Sem 1", 3));
            series.getData().add(new XYChart.Data<>("Sem 2", 4));
            series.getData().add(new XYChart.Data<>("Sem 3", 2));
            series.getData().add(new XYChart.Data<>("Sem 4", 5));

            return series;

        }).thenAccept(series -> {
            // Actualización del gráfico obligatoriamente en el hilo de JavaFX
            Platform.runLater(() -> {
                barChartAsistencia.getData().clear();
                barChartAsistencia.getData().add(series);
            });
        }).exceptionally(ex -> {
            ex.printStackTrace();
            return null;
        });
    }
}