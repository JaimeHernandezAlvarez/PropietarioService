package com.service.propietario.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="animal")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Animal {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "animal_seq")
    @SequenceGenerator(name = "animal_seq",sequenceName = "ANIMALES_SEQ",allocationSize = 1)
    @Column(name = "id_animal")
    private Integer id;

    @Column(name = "nombre_animal")
    private String nombre;

    @Column(name = "especie")
    private String especie;

    @Column(name = "genero")
    private String genero;

    @Column(name = "estado_animal")
    private String estado;

    @ManyToOne
    @JoinColumn(name = "id_propietario", referencedColumnName = "id_propietario")
    @JsonBackReference
    private Propietario propietario;
}
