package com.example.iaccs.repository;

import com.example.iaccs.entity.Command;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandRepo extends JpaRepository<Command,Integer>{

    boolean existsByCommandName(String name);
}
