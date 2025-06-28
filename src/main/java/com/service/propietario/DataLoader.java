package com.service.propietario;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.service.propietario.model.Animal;
import com.service.propietario.model.Propietario;
import com.service.propietario.repository.PropietarioRepository;

import net.datafaker.Faker;

@Profile("test")
@Component
public class DataLoader implements CommandLineRunner{
    @Autowired
    private PropietarioRepository propietarioRepository;

    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();

        //Generar Propietarios
        for(int i = 0; i < 10; i++){
            Propietario propietario = new Propietario();
            propietario.setNombre(faker.name().firstName());
            propietario.setApellido(faker.name().lastName());
            propietario.setEdad(faker.number().randomDigit());
            propietario.setTelefono(faker.phoneNumber().cellPhone());
            propietario.setUsuario(faker.number().numberBetween(0, 99999));
            List<Animal> animales = new ArrayList<>();

            //Generar animales del propietario
            for(int j = 0; j < random.nextInt(0,9); j++){
                Animal animal = new Animal();
                animal.setEspecie(faker.animal().name());
                animal.setNombre(faker.funnyName().name());
                animal.setGenero(faker.gender().shortBinaryTypes());
                animal.setEstado(faker.medical().symptoms());
                animales.add(animal);
                animal.setPropietario(propietario);
            }
            propietario.setAnimales(animales);
            propietarioRepository.save(propietario);
        }
    }
}