package com.gymManage.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Rutina {
    private Long id;

    @JsonProperty("id_usuario")
    private Long idUsuario;

    @JsonProperty("nombre_enfoque")
    private String nombreEnfoque;

    private List<Ejercicio> ejercicios;

    public Rutina() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getIdUsuario() { return idUsuario; }
    public void setIdUsuario(Long idUsuario) { this.idUsuario = idUsuario; }
    public String getNombreEnfoque() { return nombreEnfoque; }
    public void setNombreEnfoque(String nombreEnfoque) { this.nombreEnfoque = nombreEnfoque; }
    public List<Ejercicio> getEjercicios() { return ejercicios; }
    public void setEjercicios(List<Ejercicio> ejercicios) { this.ejercicios = ejercicios; }
}