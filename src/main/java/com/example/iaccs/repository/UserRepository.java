package com.example.iaccs.repository;

import com.example.iaccs.entity.Endpoint;
import com.example.iaccs.entity.User;
import com.example.iaccs.entity.enums.Role;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    long deleteByUsername(String username);

    List<User> findByRoleIn(List<Role> roles);


    List<User> findByAgency_AgencyIdAndRoleIn(Integer agencyId, List<Role> roles);

    List<User> findByAgency_Ntn_NtnIdAndRoleIn(Integer ntnId, List<Role> roles);

    List<User> findByAgency_Ntn_Command_CommandIdAndRoleIn(Integer commandId,List<Role> roles);
    List<User> findByAgency_Ntn_Command_CommandIdAndRole(Integer commandId,Role role);
    List<User> findByNtn_Command_CommandIdAndRole(Integer commandId,Role role);



}
