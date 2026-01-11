package com.mr486.tdacore.repository;


import com.mr486.tdacore.model.Reunion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ReunionRepository extends JpaRepository<Reunion, UUID> {
    Optional<Reunion> findFirstByEstActiveTrue();
}
