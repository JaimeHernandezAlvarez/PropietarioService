package com.service.propietario.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.service.propietario.model.Propietario;
import java.util.List;
import com.service.propietario.model.Animal;


@Repository
public interface PropietarioRepository extends JpaRepository<Propietario,Long>{
    long count();
    List<Propietario> findByAnimales(List<Animal> animales);
    List<Propietario> findByAnimalesId(long AnimalId);
}
