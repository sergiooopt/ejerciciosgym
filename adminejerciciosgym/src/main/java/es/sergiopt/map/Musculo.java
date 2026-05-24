package es.sergiopt.map;

public class Musculo {
    private Integer idMusculo;
    private String nombre;
    private String descripcion;
    private String zona;
    private String grupo;

    public Musculo() {}
    
    public Musculo(Integer idMusculo, String nombre, String descripcion, String zona, String grupo) {
        this.idMusculo = idMusculo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.zona = zona;
        this.grupo = grupo;
    }

    public Integer getIdMusculo() {
        return idMusculo;
    }

    public void setIdMusculo(Integer idMusculo) {
        this.idMusculo = idMusculo;
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
