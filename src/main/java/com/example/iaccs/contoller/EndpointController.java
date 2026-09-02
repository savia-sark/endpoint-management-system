package com.example.iaccs.contoller;

import com.example.iaccs.dto.EndpointRequestDTO;
import com.example.iaccs.dto.EndpointResponseDTO;
import com.example.iaccs.service.EndpointService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/endpoints")
public class EndpointController {

    @Autowired
    private EndpointService service;

    @PostMapping
    public EndpointResponseDTO createEndpoint(
            @Valid @RequestBody EndpointRequestDTO requestDTO) {

        return service.createEndpoint(requestDTO);
    }

//    @GetMapping
//    public Page<EndpointResponseDTO> getEndpoints(
//            @RequestParam(required = false) String search,
//            @RequestParam(required = false) String status,
//            @RequestParam(required = false) String type,
//            @RequestParam(required = false) Integer commandId,
//            @PageableDefault(size = 20)Pageable pageable
//    ) {
//
//        return service.searchEndpoints(
//                search,
//                status,
//                type,
//                commandId,
//                pageable
//        );
//    }

//    //With Pagination
//    @GetMapping
//    public Page<EndpointResponseDTO> getEndpoints(
//            @PageableDefault(size = 20) Pageable pageable){
//
//        return service.getAllEndpoints(pageable);
//    }

    //Without Pagination
    @GetMapping
    public ResponseEntity<List<EndpointResponseDTO>> getAllEndpoints(){
        return ResponseEntity.ok(service.getAllEndpoints());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EndpointResponseDTO> getEndpoint(@PathVariable String id)
    {
        return ResponseEntity.ok(service.getEndpointById(id));
    }

    // Update Endpoint
    @PutMapping("/{id}")
    public ResponseEntity<EndpointResponseDTO> updateEndpoint(
            @PathVariable String id,
            @Valid @RequestBody EndpointRequestDTO requestDTO) {

        return ResponseEntity.ok(service.updateEndpoint(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEndpoint(
            @PathVariable String id) {
        service.deleteEndpoint(id);
        return ResponseEntity.ok("Endpoint deleted successfully.");
    }
}
