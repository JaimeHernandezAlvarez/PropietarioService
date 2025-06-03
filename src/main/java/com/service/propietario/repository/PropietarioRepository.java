package com.service.propietario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.service.propietario.model.Propietario;

@Repository
public interface PropietarioRepository extends JpaRepository<Propietario,Long>{

}
