package com.mr486.tdacore.repository;

import com.mr486.tdacore.persistance.Ami;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmiRepository extends JpaRepository<Ami, Integer> {
}
