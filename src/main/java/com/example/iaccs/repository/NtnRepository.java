package com.example.iaccs.repository;

import com.example.iaccs.entity.Ntn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NtnRepository extends JpaRepository<Ntn,Integer> {

    @Query("""
SELECT nt
FROM Ntn nt
JOIN FETCH nt.command
""")
    List<Ntn> findAllWithCommand();

    boolean existsByNtnName(String name);
}
