package com.GTAD.repositories;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.GTAD.entities.AccidentData;

public interface AccidentDataRepository extends JpaRepository<AccidentData, Long>, JpaSpecificationExecutor<AccidentData> {
    // Custom queries if needed
    List<AccidentData> findByYear(short year);
    List<AccidentData> findByLand(short land);
    Optional<AccidentData> findById(Long id);

}
