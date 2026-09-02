package com.example.iaccs.contoller;

import com.example.iaccs.dto.AgencyRequestDTO;
import com.example.iaccs.dto.AgencyResponseDTO;
import com.example.iaccs.service.AgencyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agencies")
public class AgencyController {

    @Autowired
    private AgencyService service;

    @PostMapping
    public AgencyResponseDTO createAgency(
            @Valid @RequestBody AgencyRequestDTO requestDTO) {

        return service.createAgency(requestDTO);
    }

    @GetMapping
    public List<AgencyResponseDTO> getAllAgencies(){
        return service.getAllAgencies();
    }
    @GetMapping("/{id}")
    public AgencyResponseDTO getAgency(@PathVariable Integer id)
    {
        return service.getAgencyById(id);
    }

    // Update Agency
    @PutMapping("/{id}")
    public AgencyResponseDTO updateAgency(
            @PathVariable Integer id,
            @Valid @RequestBody AgencyRequestDTO requestDTO) {

        return service.updateAgency(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    public String deleteAgency(
            @PathVariable Integer id) {

        service.deleteAgency(id);
        return "Agency deleted successfully.";
    }
}
