package com.service.propietario.model;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "propietario")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Propietario {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq")
    @SequenceGenerator(name = "propietario_seq", sequenceName = "SEQ_PROPIETARIO", allocationSize = 1)
    @Column(name = "id_propietario")
    private Integer id;

    @Column(name = "nombre_propietario", length = 50)
    private String nombre;

    @Column(name = "apellido_propietario", length = 50)
    private String apellido;

    @Column(name = "edad")
    private Integer edad;

    @Column(name = "telefono")
    private Integer telefono;

    @OneToMany(mappedBy = "propietario", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Animal> animales = new ArrayList<>();
}
