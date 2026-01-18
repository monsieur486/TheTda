package com.mr486.tdacore.repository;

import com.mr486.tdacore.persistance.Trombine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrombineRepository extends JpaRepository<Trombine, Integer> {
}
