package es.sergiopt.models;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({"id_ejercicio", "nombre", "descripcion", "ruta_imagen", "peso_minimo", "peso_maximo"})
public class Ejercicio {
      
    @JsonbProperty("id_ejercicio")
    private Integer id;    
    private String nombre;    
    private String descripcion;    
    @JsonbProperty("ruta_imagen")
    private String rutaImagen;    
    @JsonbProperty("peso_minimo")
    private Integer pesoMinimo;    
    @JsonbProperty("peso_maximo")
    private Integer pesoMaximo;    
    
    public Ejercicio() {}
    
    public Ejercicio(Integer id, String nombre, String descripcion, String rutaImagen, Integer pesoMinimo, Integer pesoMaximo) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.rutaImagen = rutaImagen;
        this.pesoMinimo = pesoMinimo;
        this.pesoMaximo = pesoMaximo;
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

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public Integer getPesoMinimo() {
        return pesoMinimo;
    }

    public void setPesoMinimo(Integer pesoMinimo) {
        this.pesoMinimo = pesoMinimo;
    }

    public Integer getPesoMaximo() {
        return pesoMaximo;
    }

    public void setPesoMaximo(Integer pesoMaximo) {
        this.pesoMaximo = pesoMaximo;
    }

    @Override
    public String toString() {
        return "Ejercicio{" + "id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", rutaImagen=" + rutaImagen + ", pesoMinimo=" + pesoMinimo + ", pesoMaximo=" + pesoMaximo + '}';
    }
}
