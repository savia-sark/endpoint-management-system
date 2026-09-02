package com.example.iaccs.repository;

import com.example.iaccs.entity.AgencyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgencyTypeRepository extends JpaRepository<AgencyType, Integer> {
    Optional<AgencyType> findByTypeNameIgnoreCase(String typeName);

    boolean existsByTypeName(String name);
}
