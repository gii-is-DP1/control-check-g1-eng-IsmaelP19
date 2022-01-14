package org.springframework.samples.petclinic.vacination;

import java.util.List;
import java.util.Optional;


import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccinationRepository extends CrudRepository<Vaccination, Integer> {
    List<Vaccination> findAll();

    @Query("SELECT v FROM Vaccine v")
    List<Vaccine> findAllVaccines();

    @Query("SELECT v FROM Vaccine v WHERE v.name = ?1")
    Vaccine getVaccine(String name);

    Optional<Vaccination> findById(int id);

    @Transactional
    Vaccination save(Vaccination p);
}
