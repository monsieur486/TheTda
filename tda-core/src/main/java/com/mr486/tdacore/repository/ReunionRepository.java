package com.mr486.tdacore.repository;


import com.mr486.tdacore.persistance.Reunion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ReunionRepository extends JpaRepository<Reunion, UUID> {
    Optional<Reunion> findFirstByEstActiveTrue();
}
