package com.artededan.crud.azure.crudazure.controller;

import com.artededan.crud.azure.crudazure.model.Person;
import com.artededan.crud.azure.crudazure.exception.UserNotFound;
import com.artededan.crud.azure.crudazure.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/users")
public class PersonController {

    @Autowired
    private PersonRepository personRepository;

    @PostMapping
    public ResponseEntity<Person> createUser (@RequestBody Person person) {

        Person newPerson = new Person(
                person.getFirstName(),
                person.getLastName(),
                person.getAge()
        );

        personRepository.save(newPerson);
        return new ResponseEntity<>(newPerson, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Person> updateUser
            (@PathVariable("id") Long id, @RequestBody Person person) {

        Optional<Person> userdata = personRepository.findById(id);

        if (userdata.isPresent()) {
            Person _person = userdata.get();
            _person.setFirstName(person.getFirstName());
            _person.setLastName(person.getLastName());
            _person.setAge(person.getAge());

            return new ResponseEntity<>(personRepository.save(_person), HttpStatus.OK);
        } else {
            throw new UserNotFound("User ID inválido");
        }
    }


    // Get
    // Get all users
    public ResponseEntity<List<Person>> getAllUsers() {

        List<Person> people = new ArrayList<Person>();
        personRepository.findAll().forEach(people::add);
        return new ResponseEntity<>(people, HttpStatus.OK);
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<Person> getUserByID(@PathVariable("id") Long id) {

        Optional<Person> userdata = personRepository.findById(id);

        if(userdata.isPresent()) {
            return new ResponseEntity<>(userdata.get(), HttpStatus.OK);
        } else {
            throw new UserNotFound("User ID inválido");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Person> deleteUser(@PathVariable("id") Long id) {

        Optional<Person> userdata = personRepository.findById(id);

        if (userdata.isPresent()) {
           personRepository.deleteById(id);
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            throw new UserNotFound("User ID inválido");
        }
    }

}
