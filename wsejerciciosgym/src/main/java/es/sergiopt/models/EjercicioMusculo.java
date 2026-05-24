package es.sergiopt.models;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.json.bind.annotation.JsonbPropertyOrder;

@JsonbPropertyOrder({"idEjercicio", "idMusculo", "descripcion", "esDirecto", "porcentajeActivacion"})
public class EjercicioMusculo {
    
    @JsonbProperty("id_ejercicio")
    private Integer idEjercicio;    
    @JsonbProperty("id_musculo")
    private Integer idMusculo;    
    private String descripcion;
    @JsonbProperty("es_directo")
    private Boolean esDirecto;
    @JsonbProperty("porcentaje_activacion")
    private Integer porcentajeActivacion;
    
    public EjercicioMusculo() {}
    
    public EjercicioMusculo(Integer idEjercicio, Integer idMusculo, String descripcion, Boolean esDirecto, Integer porcentajeActivacion) {
        this.idEjercicio = idEjercicio;
        this.idMusculo = idMusculo;
        this.descripcion = descripcion;
        this.esDirecto = esDirecto;
        this.porcentajeActivacion = porcentajeActivacion;
    }

    public Integer getIdEjercicio() {
        return idEjercicio;
    }

    public void setIdEjercicio(Integer idEjercicio) {
        this.idEjercicio = idEjercicio;
    }

    public Integer getIdMusculo() {
        return idMusculo;
    }

    public void setIdMusculo(Integer idMusculo) {
        this.idMusculo = idMusculo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Boolean getEsDirecto() {
        return esDirecto;
    }

    public void setEsDirecto(Boolean esDirecto) {
        this.esDirecto = esDirecto;
    }

    public Integer getPorcentajeActivacion() {
        return porcentajeActivacion;
    }

    public void setPorcentajeActivacion(Integer porcentajeActivacion) {
        this.porcentajeActivacion = porcentajeActivacion;
    }

    @Override
    public String toString() {
        return "EjercicioMusculo{" + "idEjercicio=" + idEjercicio + ", idMusculo=" + idMusculo + ", descripcion=" + descripcion + ", esDirecto=" + esDirecto + ", porcentajeActivacion=" + porcentajeActivacion + '}';
    }
}
