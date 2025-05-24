package EduTech.EduTech.dto;

import java.util.List;

import EduTech.EduTech.model.Curso;

    public class CursoDTO {
        private int id;
        private String nombre;
        private String descripcion;
        private List<String> nombresUsuarios;

    public CursoDTO(Curso curso) {
        this.id = curso.getId();
        this.nombre = curso.getNombre();
        this.descripcion = curso.getDescripcion();

        this.nombresUsuarios = curso.getUsuarios().stream()
            .map(usuario -> usuario.getPersona() != null
                ? usuario.getPersona().getNombre() + " " + usuario.getPersona().getApellido()
                : "Sin nombre")
            .toList();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public List<String> getNombresUsuarios() {
        return nombresUsuarios;
    }

    public void setNombresUsuarios(List<String> nombresUsuarios) {
        this.nombresUsuarios = nombresUsuarios;
    }

    
}
