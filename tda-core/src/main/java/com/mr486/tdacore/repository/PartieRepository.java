package com.mr486.tdacore.repository;

import com.mr486.tdacore.persistance.Partie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PartieRepository extends JpaRepository<Partie, Integer> {

    Optional<Partie> findPartieByNumeroPartie(Integer numeroPartie);

    List<Partie> findAllByOrderByNumeroPartieAsc();

    Partie findByUuid(UUID uuid);
}
