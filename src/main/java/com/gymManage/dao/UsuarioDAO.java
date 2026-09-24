package com.gymManage.dao;
import com.fasterxml.jackson.core.type.TypeReference;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.gymManage.config.DBConnection;
import com.gymManage.model.Usuario;

public class UsuarioDAO {

    /**
     * Inserta un usuario en la tabla 'usuarios' utilizando la API REST de Supabase de forma asíncrona.
     */
    public static CompletableFuture<Boolean> crearUsuario(Usuario usuario) {
        try {
            // Convertir objeto Usuario a formato JSON
            String jsonBody = DBConnection.JSON.writeValueAsString(usuario);

            // Petición POST asíncrona al endpoint
            return DBConnection.postAsync("usuarios", jsonBody)
                    .thenApply(respuestaJson -> {
                        // Si Supabase devuelve el registro creado, la creación fue exitosa
                        return respuestaJson != null && !respuestaJson.trim().isEmpty();
                    })
                    .exceptionally(ex -> {
                        ex.printStackTrace();
                        return false; // Retorna false si hay un error HTTP o de red
                    });

        } catch (Exception e) {
            e.printStackTrace();
            return CompletableFuture.completedFuture(false);
        }
    }

    public CompletableFuture<Usuario> autenticarUsuario(String usuario, String password, String rol) {
        try {
            String usuarioCodificado = URLEncoder.encode(usuario, StandardCharsets.UTF_8.toString());
            String passwordCodificado = URLEncoder.encode(password, StandardCharsets.UTF_8.toString());
            String rolCodificado = URLEncoder.encode(rol, StandardCharsets.UTF_8.toString());

            String path = "usuarios?nombre_completo=eq." + usuarioCodificado +
                    "&password=eq." + passwordCodificado +
                    "&rol=eq." + rolCodificado + "&select=*";

            System.out.println("URL solicitada a Supabase: " + path);

            return DBConnection.getAsync(path)
                    .thenApply(json -> {
                        // 1. VER LA RESPUESTA REAL DE LA BASE DE DATOS
                        System.out.println("Respuesta cruda de Supabase: " + json);

                        try {
                            List<Usuario> resultados = DBConnection.JSON.readValue(json, new TypeReference<List<Usuario>>() {});
                            System.out.println("Usuarios encontrados tras el mapeo: " + resultados.size());
                            return resultados.isEmpty() ? null : resultados.get(0);
                        } catch (Exception e) {
                            // 2. DETECTAR ERRORES DE JACKSON (Mapeo de JSON a Objeto)
                            System.err.println("Error al convertir el JSON a la clase Usuario. Verifica que los nombres de las variables coincidan:");
                            e.printStackTrace();
                            return null;
                        }
                    })
                    .exceptionally(ex -> {
                        // 3. DETECTAR ERRORES DE RED O DE LA API
                        System.err.println("Error en la petición a Supabase:");
                        ex.printStackTrace();
                        return null;
                    });

        } catch (Exception e) {
            e.printStackTrace();
            return CompletableFuture.completedFuture(null);
        }
    }
}