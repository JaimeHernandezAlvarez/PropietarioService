package com.service.propietario.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.service.propietario.model.Propietario;
import com.service.propietario.repository.PropietarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PropietarioService {
    @Autowired
    private PropietarioRepository propietarioRepository;

    //Listar Propietarios
    public List<Propietario> findAll(){
        return propietarioRepository.findAll();
    }

    //Listar Propietarios por ID
    public Propietario findById(long id){
        return propietarioRepository.findById(id).get();
    }

    //guardar Propietarios por ID
    public Propietario save(Propietario usuario){
        return propietarioRepository.save(usuario);
    }

    //Eliminar Propietarios
    public void delete(long id){
        propietarioRepository.deleteById(id);
    }
}
