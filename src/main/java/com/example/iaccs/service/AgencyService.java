package com.example.iaccs.service;

import com.example.iaccs.dto.AgencyRequestDTO;
import com.example.iaccs.dto.AgencyResponseDTO;
import com.example.iaccs.entity.*;
import com.example.iaccs.exception.DuplicateResourceException;
import com.example.iaccs.exception.ResourceNotFoundException;
import com.example.iaccs.mapper.AgencyMapper;
import com.example.iaccs.repository.AgencyRepository;
import com.example.iaccs.repository.AgencyTypeRepository;
import com.example.iaccs.repository.NtnRepository;
import com.example.iaccs.security.AccessControlService;
import com.example.iaccs.security.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class AgencyService {
    private final AgencyRepository repository;
    private final AgencyTypeRepository agencyTypeRepository;
    private final NtnRepository ntnRepository;

    @Autowired
    AgencyMapper mapper;

    @Autowired
    private SecurityUtils securityUtils;

    @Autowired
    AccessControlService accessControlService;


    public AgencyService(AgencyRepository repository, AgencyTypeRepository agencyTypeRepository, NtnRepository ntnRepository) {
        this.repository = repository;
        this.agencyTypeRepository = agencyTypeRepository;
        this.ntnRepository = ntnRepository;
    }

    public List<AgencyResponseDTO> getAllAgencies(){
        User currentUser = securityUtils.getCurrentUser();

        switch (currentUser.getRole()) {

            case SUPER_ADMIN:
            case ADMIN:
                return repository.findAllWithNtnAndType().stream().map(mapper::responseDTO).toList();

            case COMMAND_HEAD:
                return repository.findByNtn_Command_CommandId(
                                currentUser.getCommand().getCommandId())
                        .stream().map(mapper::responseDTO).toList();


            case NTN_HEAD:
                return repository.findByNtn_NtnId(
                                currentUser.getNtn().getNtnId())
                        .stream().map(mapper::responseDTO).toList();

            default:
                throw new AccessDeniedException("Access denied");
        }}

    public AgencyResponseDTO getAgencyById(Integer id){
        Agency agency=repository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Agency not found with id: " + id));
        accessControlService.checkCanAccessAgency(agency);
        return mapper.responseDTO(agency);
    }

    public AgencyResponseDTO getAgencyByName(String name) {
        Agency agency= repository.findByAgencyNameIgnoreCase(name)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Agency not found with name: " + name));
        accessControlService.checkCanAccessAgency(agency);
        return mapper.responseDTO(agency);
    }

    public AgencyResponseDTO createAgency(AgencyRequestDTO agencyRequestDTO){
        if(repository.existsByAgencyName(agencyRequestDTO.getAgencyName()))
        {
            throw new DuplicateResourceException("Agency Already exists.");
        }
        Agency agency=mapper.requestEntity(agencyRequestDTO);
        Ntn ntn=ntnRepository.findById(agencyRequestDTO.getNtnId())
                .orElseThrow(()->new ResourceNotFoundException("Ntn not found with id: "+ agencyRequestDTO.getNtnId()));

        accessControlService.checkCanCreateAgency(ntn);

        AgencyType agencyType=agencyTypeRepository.findById(agencyRequestDTO.getAgencyTypeId())
                .orElseThrow(()->new ResourceNotFoundException("AgencyType not found with id: "+ agencyRequestDTO.getAgencyTypeId()));
        agency.setNtn(ntn);
        agency.setAgencyType(agencyType);
        return mapper.responseDTO(repository.save(agency));
    }

    public AgencyResponseDTO updateAgency(Integer id, AgencyRequestDTO agencyRequestDTO){
        Agency existing=repository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Agency not found with id: " + id));
        accessControlService.checkCanUpdateAgency(existing);
        AgencyType agencyType=agencyTypeRepository.findById(agencyRequestDTO.getAgencyTypeId())
                .orElseThrow(()->new ResourceNotFoundException("Agency Type not found with id: " + agencyRequestDTO.getAgencyTypeId()));;existing.setAgencyName(agencyRequestDTO.getAgencyName());

        Ntn ntn=ntnRepository.findById(agencyRequestDTO.getNtnId())
                        .orElseThrow(()->new ResourceNotFoundException("Ntn not found with id: "+ agencyRequestDTO.getNtnId()));
        existing.setAgencyName(agencyRequestDTO.getAgencyName());
        existing.setAgencyType(agencyType);
        existing.setNtn(ntn);

        return  mapper.responseDTO(repository.save(existing));
    }

    public void deleteAgency(Integer id){
        Agency agency = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Agency not found"));
        accessControlService.checkCanDeleteAgency(agency);
        repository.deleteById(id);
    }
}
