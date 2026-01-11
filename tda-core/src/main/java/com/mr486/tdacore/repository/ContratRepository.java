package com.mr486.tdacore.repository;


import com.mr486.tdacore.model.Contrat;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContratRepository {

    List<Contrat> findAll();

    Optional<Contrat> findById(Integer id);
}
