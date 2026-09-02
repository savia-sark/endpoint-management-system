package com.example.iaccs.contoller;

import com.example.iaccs.dto.NtnRequestDTO;
import com.example.iaccs.dto.NtnResponseDTO;
import com.example.iaccs.service.NtnService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ntns")
public class NtnController {

    @Autowired
    private NtnService service;

    @PostMapping
    public NtnResponseDTO createNtn(
            @Valid @RequestBody NtnRequestDTO requestDTO) {

        return service.createNtn(requestDTO);
    }

    @GetMapping
    public List<NtnResponseDTO> getAllNtns(){
        return service.getAllNtns();
    }
    @GetMapping("/{id}")
    public NtnResponseDTO getNtn(@PathVariable Integer id)
    {
        return service.getNtnById(id);
    }

    // Update Ntn
    @PutMapping("/{id}")
    public NtnResponseDTO updateNtn(
            @PathVariable Integer id,
            @Valid @RequestBody NtnRequestDTO requestDTO) {

        return service.updateNtn(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    public String deleteNtn(
            @PathVariable Integer id) {

        service.deleteNtn(id);
        return "Ntn deleted successfully.";
    }
}
