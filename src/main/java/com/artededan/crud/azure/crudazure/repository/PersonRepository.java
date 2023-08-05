package com.artededan.crud.azure.crudazure.repository;

import com.artededan.crud.azure.crudazure.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

}
