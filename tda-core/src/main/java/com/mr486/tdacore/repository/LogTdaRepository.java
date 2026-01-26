package com.mr486.tdacore.repository;

import com.mr486.tdacore.persistance.LogTda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface LogTdaRepository extends JpaRepository<LogTda, UUID> {
    List<LogTda> findAllByOrderByDateCreationDesc();
}
