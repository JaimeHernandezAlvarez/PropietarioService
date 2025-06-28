package com.service.propietario.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.service.propietario.controller.PropietarioControllerV2;
import com.service.propietario.model.Propietario;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class PropietarioModelAssembler implements RepresentationModelAssembler<Propietario, EntityModel<Propietario>>{
    @SuppressWarnings("null")
    @Override
    public EntityModel<Propietario> toModel(Propietario propietario){
        return EntityModel.of(propietario,
            linkTo(methodOn(PropietarioControllerV2.class).getPropietarioById(propietario.getId())).withSelfRel(),
            linkTo(methodOn(PropietarioControllerV2.class).getAllPropietarios()).withRel("propietarios"));
    }
}

/*
@Component
public class CarreraModelAssembler implements RepresentationModelAssembler<Carrera, EntityModel<Carrera>> {

    @Override
    public EntityModel<Carrera> toModel(Carrera carrera) {
        return EntityModel.of(carrera,
                linkTo(methodOn(CarreraControllerV2.class).getCarreraByCodigo(carrera.getCodigo())).withSelfRel(),
                linkTo(methodOn(CarreraControllerV2.class).getAllCarreras()).withRel("carreras"));
    }
}
 */
