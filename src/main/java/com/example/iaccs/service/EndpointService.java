package com.example.iaccs.service;

import com.example.iaccs.dto.EndpointRequestDTO;
import com.example.iaccs.dto.EndpointResponseDTO;
import com.example.iaccs.entity.Agency;
import com.example.iaccs.entity.AgencyType;
import com.example.iaccs.entity.Endpoint;
import com.example.iaccs.entity.User;
import com.example.iaccs.exception.DuplicateResourceException;
import com.example.iaccs.exception.ResourceNotFoundException;
import com.example.iaccs.mapper.EndpointMapper;
import com.example.iaccs.repository.AgencyRepository;
import com.example.iaccs.repository.EndpointRepository;
import com.example.iaccs.security.AccessControlService;
import com.example.iaccs.security.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.iaccs.entity.enums.Role.ADMIN;
import static com.example.iaccs.entity.enums.Role.SUPER_ADMIN;

@Service
public class EndpointService {
    private final EndpointRepository repository;
    private final AgencyRepository agencyRepository;

    @Autowired
    private EndpointMapper mapper;

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    private AccessControlService accessControlService;
    public EndpointService(AgencyRepository agencyRepository, EndpointRepository repository) {
        this.agencyRepository = agencyRepository;
        this.repository = repository;
    }

//    public Page<EndpointResponseDTO> searchEndpoints(
//            String search,
//            String status,
//            String type,
//            Integer commandId,
//            Pageable pageable
//    ) {
//
//        return repository.searchEndpoints(
//                normalize(search),
//                normalize(status),
//                normalize(type),
//                commandId,
//                pageable
//        ).map(mapper::responseDTO);
//    }
//    private String normalize(String value) {
//        return (value == null || value.isBlank() || value.equals("ALL"))
//                ? null
//                : value;
//    }

//      //With Pagination
//    public Page<EndpointResponseDTO> getAllEndpoints(Pageable pageable){
//
//        return repository.findAllWithAgency(pageable)
//                .map(mapper::responseDTO);
//    }

    //Without Pagination
//    public List<EndpointResponseDTO> getAllEndpoints(){
//        return repository.findAllWithAgency().stream().map(mapper::responseDTO).toList();}

    public List<EndpointResponseDTO> getAllEndpoints() {
        User currentUser = securityUtils.getCurrentUser();

        switch (currentUser.getRole()) {

            case SUPER_ADMIN:
            case ADMIN:
                return repository.findAllWithAgency().stream().map(mapper::responseDTO).toList();

            case COMMAND_HEAD:
                return repository.findByAgency_Ntn_Command_CommandId(
                        currentUser.getCommand().getCommandId())
                        .stream().map(mapper::responseDTO).toList();


            case NTN_HEAD:
                return repository.findByAgency_Ntn_NtnId(
                                currentUser.getNtn().getNtnId())
                        .stream().map(mapper::responseDTO).toList();

            case AGENCY_HEAD:
                return repository.findByAgency_AgencyId(
                                currentUser.getAgency().getAgencyId())
                                .stream().map(mapper::responseDTO).toList();

            default:
                throw new AccessDeniedException("Access denied");
        }
    }

    public EndpointResponseDTO getEndpointById(String id){
        Endpoint endpoint= repository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Endpoint not Found with id: "+id));
        accessControlService.checkCanAccessEndpoint(endpoint);
        return mapper.responseDTO(endpoint);
    }

    public EndpointResponseDTO createEndpoint(EndpointRequestDTO endpointRequestDTO){
        if(repository.existsById(endpointRequestDTO.getEndpointId()))
        {
            System.out.println(endpointRequestDTO);
            throw new DuplicateResourceException("Endpoint already exists.");
        }
        Endpoint endpoint=mapper.requestEntity(endpointRequestDTO);
        Agency agency=agencyRepository.findById(endpointRequestDTO.getAgencyId())
                .orElseThrow(()-> new ResourceNotFoundException("Agency not found with id: "+endpointRequestDTO.getAgencyId()));
        endpoint.setAgency(agency);
        accessControlService.checkCanCreateEndpoint(agency);
        return mapper.responseDTO(repository.save(endpoint));
    }

    public EndpointResponseDTO updateEndpoint(String id, EndpointRequestDTO endpointRequestDTO){
        Endpoint existing=repository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Endpoint not Found with id: "+id));

        accessControlService.checkCanUpdateEndpoint(existing);

        existing.setEndpointType(endpointRequestDTO.getEndpointType());
        existing.setStatus(endpointRequestDTO.getStatus());
        existing.setRemarks(endpointRequestDTO.getRemarks());
        existing.setSystemOwner(endpointRequestDTO.getSystemOwner());
        existing.setImRaised(endpointRequestDTO.getImRaised());
        existing.setAgency(existing.getAgency());

        return  mapper.responseDTO(repository.save(existing));
    }

    public void deleteEndpoint(String id){
        Endpoint endpoint = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Endpoint not found"));

        accessControlService.checkCanDeleteEndpoint(endpoint);

        repository.deleteById(id);
    }
}
