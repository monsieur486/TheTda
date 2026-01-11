package com.mr486.tdacore.repository;


import com.mr486.tdacore.model.Contrat;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ContratRepositoryImpl implements ContratRepository {

    private final List<Contrat> contrats = List.of(
            new Contrat(1, "Belge", "\uD83C\uDDE7\uD83C\uDDEA", 0),
            new Contrat(2, "Petite", "P", 20),
            new Contrat(3, "Garde", "G", 50),
            new Contrat(4, "Garde sans", "GS", 100),
            new Contrat(5, "Garde contre", "GC", 200)
    );

    @Override
    public List<Contrat> findAll() {
        return contrats;
    }

    @Override
    public Optional<Contrat> findById(Integer id) {
        return contrats.stream().filter(contrat -> contrat.getId().equals(id)).findFirst();
    }
}
