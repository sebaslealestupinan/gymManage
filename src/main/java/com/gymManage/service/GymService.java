package com.gymManage.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.gymManage.config.DBConnection;
import com.gymManage.model.Clase;
import com.gymManage.model.Rutina;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GymService {

    public CompletableFuture<List<Rutina>> obtenerRutinaUsuario(long idUsuario) {
        return DBConnection.getAsync("rutinas?id_usuario=eq." + idUsuario)
                .thenApply(json -> {
                    try {
                        return DBConnection.JSON.readValue(json, new TypeReference<List<Rutina>>() {});
                    } catch (Exception e) {
                        throw new RuntimeException("Error parseando rutina", e);
                    }
                });
    }

    public CompletableFuture<List<Clase>> obtenerClasesDisponibles() {
        return DBConnection.getAsync("clases?select=*")
                .thenApply(json -> {
                    try {
                        return DBConnection.JSON.readValue(json, new TypeReference<List<Clase>>() {});
                    } catch (Exception e) {
                        throw new RuntimeException("Error parseando clases", e);
                    }
                });
    }
}