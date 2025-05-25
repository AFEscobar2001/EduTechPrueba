package EduTech.EduTech.model;

import java.util.List;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nombre;
    private String descripcion;

    @ManyToMany(mappedBy = "cursos")
    private List<Usuario> usuarios;

    @ManyToMany(mappedBy = "cursos")
    private List<Instructor> instructores;


    public Curso() {
        this.id = 0;
        this.nombre = "";
        this.descripcion = "";
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

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Instructor> getInstructores() {
        return instructores;
    }

    public void setInstructores(List<Instructor> instructores) {
        this.instructores = instructores;
    }
    
}

