package com.example.iaccs.repository;

import com.example.iaccs.entity.Agency;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgencyRepository extends JpaRepository<Agency,Integer> {
    Optional<Agency> findByAgencyNameIgnoreCase(String name);

    @Query("""
    SELECT ag
    FROM Agency ag
    JOIN FETCH ag.agencyType
    JOIN FETCH ag.ntn
    """)
    List<Agency> findAllWithNtnAndType();

    List<Agency> findByNtn_NtnId(Integer ntnId);

    List<Agency> findByNtn_Command_CommandId(Integer commandId);

    boolean existsByAgencyName(String name);
}
