package com.mr486.tdacore.repository;

import com.mr486.tdacore.persistance.Joueur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface  JoueurRepository extends JpaRepository<Joueur, UUID> {
    List<Joueur> findAllByReunionUuid(UUID uuid);
}
