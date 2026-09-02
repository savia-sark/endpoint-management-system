package com.example.iaccs.repository;

import com.example.iaccs.entity.Endpoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EndpointRepository extends JpaRepository<Endpoint,String> {
//    //With Pagination
//    @Query(
//            value = """
//        SELECT e FROM Endpoint e
//        JOIN FETCH e.agency
//        """,
//            countQuery = """
//        SELECT COUNT(e) FROM Endpoint e
//        """
//    )
//    Page<Endpoint> findAllWithAgency(Pageable pageable);

//    @Query(
//            value = """
//SELECT e
//FROM Endpoint e
//JOIN FETCH e.agency a
//JOIN a.ntn n
//JOIN n.command c
//WHERE
//(:search IS NULL OR
// LOWER(e.endpointId) LIKE LOWER(CONCAT('%', :search, '%'))
// OR LOWER(e.systemOwner) LIKE LOWER(CONCAT('%', :search, '%'))
// OR LOWER(e.remarks) LIKE LOWER(CONCAT('%', :search, '%'))
//)
//AND (:status IS NULL OR e.status = :status)
//AND (:type IS NULL OR e.endpointType = :type)
//AND (:commandId IS NULL OR c.commandId = :commandId)
//""",
//            countQuery = """
//SELECT COUNT(e)
//FROM Endpoint e
//JOIN FETCH e.agency a
//JOIN a.ntn n
//JOIN n.command c
//WHERE
//(:search IS NULL OR
// LOWER(e.endpointId) LIKE LOWER(CONCAT('%', :search, '%'))
// OR LOWER(e.systemOwner) LIKE LOWER(CONCAT('%', :search, '%'))
// OR LOWER(e.remarks) LIKE LOWER(CONCAT('%', :search, '%'))
//)
//AND (:status IS NULL OR e.status = :status)
//AND (:type IS NULL OR e.endpointType = :type)
//AND (:commandId IS NULL OR c.commandId = :commandId)
//"""
//    )
//    Page<Endpoint> searchEndpoints(
//            @Param("search") String search,
//            @Param("status") String status,
//            @Param("type") String type,
//            @Param("departmentId") Integer departmentId,
//            Pageable pageable
//    );

    //Without Pagination
@Query("""
SELECT ep
FROM Endpoint ep
JOIN FETCH ep.agency
""")
List<Endpoint> findAllWithAgency();


    List<Endpoint> findByAgency_AgencyId(Integer agencyId);

    List<Endpoint> findByAgency_Ntn_NtnId(Integer ntnId);

    List<Endpoint> findByAgency_Ntn_Command_CommandId(Integer commandId);
}


