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

@Profile("dev")
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
            propietario.setUsuario(faker.number().randomNumber());
            //Generar animales del propietario
            List<Animal> animales = new ArrayList<>();
            for(int j = 0; j < random.nextInt(0,9); j++){
                Animal animal = new Animal();
                animal.setEspecie(faker.animal().species());
                animal.setNombre(faker.animal().name());
                animal.setGenero(faker.gender().shortBinaryTypes());
                animal.setEstado(faker.animal().genus());
                animales.add(animal);
            }
            propietario.setAnimales(animales);
            propietarioRepository.save(propietario);
        }
    }
}
/*
@Profile("dev")
@Component
public class DataLoader implements CommandLineRunner {
    @Autowired
    private CarreraRepository carreraRepository;
    @Autowired
    private EstudianteRepository estudianteRepository;
    @Autowired
    private ReservaRepository reservaRepository;
    @Autowired
    private SalaRepository salaRepository;
    @Autowired
    private TipoSalaRepository tipoSalaRepository;
    @Override
    public void run(String... args) throws Exception {
        Faker faker = new Faker();
        Random random = new Random();
        // Generar tipos de sala
        for (int i = 0; i < 3; i++) {
            TipoSala tipoSala = new TipoSala();
            tipoSala.setIdTipo(i + 1);
            tipoSala.setNombre(faker.book().genre());
            tipoSalaRepository.save(tipoSala);
        }
        // Generar carreras
        for (int i = 0; i < 5; i++) {
            Carrera carrera = new Carrera();
            carrera.setCodigo(faker.code().asin());
            carrera.setNombre(faker.educator().course());
            carreraRepository.save(carrera);
        }
        List<Carrera> carreras = carreraRepository.findAll();
        // Generar estudiantes
        for (int i = 0; i < 50; i++) {
            Estudiante estudiante = new Estudiante();
            estudiante.setId(i + 1);
            estudiante.setRun(faker.idNumber().valid());
            estudiante.setNombres(faker.name().fullName());
            estudiante.setCorreo(faker.internet().emailAddress());
            estudiante.setJornada(faker.options().option("D", "N").charAt(0));
            estudiante.setTelefono(faker.number().numberBetween(100000000, 999999999));
            estudiante.setCarrera(carreras.get(random.nextInt(carreras.size())));
            estudianteRepository.save(estudiante);
        }
        // Generar salas
        for (int i = 0; i < 10; i++) {
            Sala sala = new Sala();
            sala.setCodigo(i + 1);
            sala.setNombre(faker.university().name());
            sala.setCapacidad(faker.number().numberBetween(10, 100));
            sala.setIdInstituo(faker.number().randomDigit());
            sala.setTipoSala(tipoSalaRepository.findAll().get(random.nextInt(3)));
            salaRepository.save(sala);
        }
        List<Estudiante> estudiantes = estudianteRepository.findAll();
        List<Sala> salas = salaRepository.findAll();
        // Generar reservas
        for (int i = 0; i < 20; i++) {
            Reserva reserva = new Reserva();
            reserva.setId(i + 1);
            reserva.setEstudiante(estudiantes.get(random.nextInt(estudiantes.size())));
            reserva.setSala(salas.get(random.nextInt(salas.size())));
            reserva.setFechaSolicitada(new Date());
            reserva.setHoraSolicitada(new Date());
            reserva.setHoraCierre(new Date(System.currentTimeMillis() +
            faker.number().numberBetween(3600000, 7200000))); // 1-2 horas más
            reserva.setEstado(faker.number().numberBetween(0, 2));
            reservaRepository.save(reserva);
        }
    }
} */