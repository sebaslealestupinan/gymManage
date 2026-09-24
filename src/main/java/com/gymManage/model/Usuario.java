package com.gymManage.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Usuario {
    private Integer id;
    private String rol;

    @JsonProperty("nombre_completo")
    private String nombreCompleto;

    private String correo;
    private String usuario;
    private String password;

    public Usuario() {}

    public Usuario(String rol, String nombreCompleto, String correo, String usuario, String password) {
        this.rol = rol;
        this.nombreCompleto = nombreCompleto;
        this.correo = correo;
        this.usuario = usuario;
        this.password = password;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}