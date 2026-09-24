package com.gymManage.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Clase {
    private Long id;
    private String nombre;
    private String horario;
    private String sala;

    @JsonProperty("capacidad_maxima")
    private Integer capacidadMaxima;

    public Clase() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }
    public String getSala() { return sala; }
    public void setSala(String sala) { this.sala = sala; }
    public Integer getCapacidadMaxima() { return capacidadMaxima; }
    public void setCapacidadMaxima(Integer capacidadMaxima) { this.capacidadMaxima = capacidadMaxima; }
}