package es.sergiopt.models;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({"id", "nombre", "descripcion", "zona", "grupo"})
public class Musculo {
    
    @JsonbProperty("id_musculo")
    private Integer id;
    private String nombre;
    private String descripcion;
    private String zona;
    private String grupo;

    public Musculo() {}
    
    public Musculo(Integer id, String nombre, String descripcion, String zona, String grupo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.zona = zona;
        this.grupo = grupo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getZona() {
        return zona;
    }

    public void setZona(String zona) {
        this.zona = zona;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String grupo) {
        this.grupo = grupo;
    }

    @Override
    public String toString() {
        // return "Musculo{" + "id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", zona=" + zona + ", grupo=" + grupo + '}';
        
        // Devolvemos nombre para combo box
        return nombre;
    }
}
