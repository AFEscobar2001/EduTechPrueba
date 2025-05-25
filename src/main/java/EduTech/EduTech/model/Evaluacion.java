package EduTech.EduTech.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Evaluacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double nota; 
    private String observacion;

    @ManyToOne
    @JoinColumn(name = "contenido_id")
    private Contenido contenido;

    public Evaluacion() {
        this.nota = 0.0;
        this.observacion = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public Contenido getContenido() {
        return contenido;
    }

    public void setContenido(Contenido contenido) {
        this.contenido = contenido;
    }

    

}
