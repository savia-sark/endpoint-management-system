package com.example.iaccs.service;

import com.example.iaccs.dto.AgencyTypeRequestDTO;
import com.example.iaccs.dto.AgencyTypeResponseDTO;
import com.example.iaccs.entity.Agency;
import com.example.iaccs.entity.AgencyType;
import com.example.iaccs.exception.DuplicateResourceException;
import com.example.iaccs.exception.ResourceNotFoundException;
import com.example.iaccs.mapper.AgencyTypeMapper;
import com.example.iaccs.repository.AgencyTypeRepository;
import com.example.iaccs.security.AccessControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgencyTypeService {
    private final AgencyTypeRepository repository;

    @Autowired
    AgencyTypeMapper mapper;

    @Autowired
    AccessControlService accessControlService;

    public AgencyTypeService(AgencyTypeRepository repo){
        this.repository=repo;
    }

    public List<AgencyTypeResponseDTO> getAllAgencyTypes(){
        return repository.findAll()
            .stream()
            .map(mapper::responseDTO)
            .toList();}

    public AgencyTypeResponseDTO getAgencyTypeById(Integer id){
        AgencyType agencyType=repository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("AgencyType not found with id: " + id));

        return mapper.responseDTO(agencyType);
    }
    public AgencyTypeResponseDTO getAgencyTypeByName(String name) {
        AgencyType agencyType= repository.findByTypeNameIgnoreCase(name)
                .orElseThrow(() ->
                        new ResourceNotFoundException("AgencyType not found with name: " + name));
        return mapper.responseDTO(agencyType);
    }

    public AgencyTypeResponseDTO createAgencyType(AgencyTypeRequestDTO agencyTypeRequestDTO){
        if(repository.existsByTypeName(agencyTypeRequestDTO.getAgencyTypeName()))
        {
            System.out.println("can Access Agency");
            throw new DuplicateResourceException("AgencyType already exists");
        }
        accessControlService.checkCanCreateAgencyType();
        AgencyType agencyType=mapper.requestEntity(agencyTypeRequestDTO);
        return mapper.responseDTO(repository.save(agencyType));
    }

    public AgencyTypeResponseDTO updateAgencyType(Integer id, AgencyTypeRequestDTO agencyTypeRequestDTO){
        accessControlService.checkCanUpdateAgencyType();
        AgencyType existing= repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("AgencyType not found with name: " + id));
        existing.setTypeName(agencyTypeRequestDTO.getAgencyTypeName());
        return  mapper.responseDTO(repository.save(existing));
    }

//    public AgencyType updateAgencyTypeByName(String name, AgencyType agencyType){
//        AgencyType existing=getAgencyTypeByName(name);
//        existing.setTypeName(agencyType.getTypeName());
//        return  repository.save(existing);
//    }

    public void deleteAgencyType(Integer id){
        AgencyType agencyType = repository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Agency not found"));
        accessControlService.checkCanDeleteAgencyType();
        repository.deleteById(id);;
    }


}
