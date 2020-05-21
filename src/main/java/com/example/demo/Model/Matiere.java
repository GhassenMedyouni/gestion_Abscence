package com.example.demo.Model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Matiere {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id ;

    @NonNull
    @Column(name = "label")
    private String label;

    @NonNull
    private double nombre_heure;

    @JsonIgnore
    @ManyToMany
    private List<Classe> classes =new ArrayList<Classe>();


    public String toString(){
        return label ;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getLabel() {
        return label;
    }

    public void setLabel(@NonNull String label) {
        this.label = label;
    }

    public double getNombre_heure() {
        return nombre_heure;
    }

    public void setNombre_heure(double nombre_heure) {
        this.nombre_heure = nombre_heure;
    }

    public List<Classe> getClasses() {
        return classes;
    }

    public void setClasses(List<Classe> classes) {
        this.classes = classes;
    }
}
