package com.service.propietario.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.service.propietario.assemblers.PropietarioModelAssembler;
import com.service.propietario.model.Animal;
import com.service.propietario.model.Propietario;
import com.service.propietario.service.PropietarioService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;

import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/propietarios")
@Tag(name = "PropietariosV2", description = "Operaciones relacionadas con las carreras a traves de HATEOAS")
public class PropietarioControllerV2 {
    @Autowired
    private PropietarioService propietarioService;

    @Autowired
    private PropietarioModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener todos los propietarios", description = "Obtiene una lista de todos los propietarios")
    @ApiResponse(responseCode = "200", description = "Operación exitosa")
    public CollectionModel<EntityModel<Propietario>> getAllPropietarios() {
        List<EntityModel<Propietario>> propietarios = propietarioService.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(propietarios,
                linkTo(methodOn(PropietarioControllerV2.class).getAllPropietarios()).withSelfRel());
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear un propietario", description = "Se crea un propietario en el repositorio")
    @ApiResponse(responseCode = "201", description = "Operación exitosa")
    public ResponseEntity<EntityModel<Propietario>> createPropietario(@RequestBody Propietario propietario) {
        Propietario nuevo = propietarioService.save(propietario);
        if(propietario.getAnimales() != null){
            for(Animal animal : propietario.getAnimales()){
                animal.setPropietario(nuevo);
            }
        }
        return ResponseEntity
                .created(linkTo(methodOn(PropietarioControllerV2.class).getPropietarioById(nuevo.getId())).toUri())
                .body(assembler.toModel(nuevo));
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener un propietario", description = "Se obtiene un solo propietario en base a la id que se le da")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa"),
        @ApiResponse(responseCode = "404", description = "Propietario no encontrado")
    })
    public EntityModel<Propietario> getPropietarioById(@PathVariable int id) {
        Propietario propietario = propietarioService.findById(id);
        return assembler.toModel(propietario);
    }


    @PutMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar un propietario", description = "Se actualiza un solo propietario en base a la id que se le da")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Operación exitosa"),
        @ApiResponse(responseCode = "404", description = "Propietario no encontrado")
    })
    public ResponseEntity<EntityModel<Propietario>> actualizar(@PathVariable Integer id, @RequestBody Propietario propietario) {
        try {
            Propietario existente = propietarioService.findById(id);
            existente.setId(id);
            existente.setNombre(propietario.getNombre());
            existente.setApellido(propietario.getApellido());
            existente.setEdad(propietario.getEdad());
            existente.setTelefono(propietario.getTelefono());

            existente.getAnimales().clear();

            if (propietario.getAnimales() != null) {
                for (Animal animal : propietario.getAnimales()) {
                    animal.setPropietario(existente);
                    existente.getAnimales().add(animal);
                }
            }

            Propietario actualizado = propietarioService.save(existente);
            return ResponseEntity
                .ok(assembler.toModel(actualizado));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un propietario", description = "Se elimina un solo propietario en base a la id que se le da")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Operación exitosa"),
        @ApiResponse(responseCode = "404", description = "Propietario no encontrado")
    })
    public ResponseEntity<?> eliminar(@PathVariable Integer id) {
        try {
            propietarioService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
