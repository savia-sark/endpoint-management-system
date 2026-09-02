package com.example.iaccs.contoller;

import com.example.iaccs.dto.AgencyTypeRequestDTO;
import com.example.iaccs.dto.AgencyTypeResponseDTO;
import com.example.iaccs.service.AgencyTypeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agencyTypes")
public class AgencyTypeController {

    @Autowired
    private AgencyTypeService service;

    @PostMapping
    public AgencyTypeResponseDTO createAgencyType(
            @Valid @RequestBody AgencyTypeRequestDTO requestDTO) {

        return service.createAgencyType(requestDTO);
    }

    @GetMapping
    public List<AgencyTypeResponseDTO> getAllAgencyTypes(){
        return service.getAllAgencyTypes();
    }
    @GetMapping("/{id}")
    public AgencyTypeResponseDTO getAgencyType(@PathVariable Integer id)
    {
        return service.getAgencyTypeById(id);
    }

    // Update AgencyType
    @PutMapping("/{id}")
    public AgencyTypeResponseDTO updateAgencyType(
            @PathVariable Integer id,
            @Valid @RequestBody AgencyTypeRequestDTO requestDTO) {

        return service.updateAgencyType(id, requestDTO);
    }
    
    @DeleteMapping("/{id}")
    public String deleteAgencyType(
            @PathVariable Integer id) {

        service.deleteAgencyType(id);
        return "AgencyType deleted successfully.";
    }
}
