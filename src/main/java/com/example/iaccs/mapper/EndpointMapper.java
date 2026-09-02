package com.example.iaccs.mapper;

import com.example.iaccs.dto.EndpointRequestDTO;
import com.example.iaccs.dto.EndpointResponseDTO;
import com.example.iaccs.entity.Endpoint;
import org.springframework.stereotype.Component;

@Component
public class EndpointMapper {
    public Endpoint requestEntity(EndpointRequestDTO dto) {
        //Agency assignment is to be handled in service Layer
        Endpoint endpoint = new Endpoint();
        endpoint.setEndpointId(dto.getEndpointId());
        endpoint.setEndpointType(dto.getEndpointType());
        endpoint.setStatus(dto.getStatus());
        endpoint.setRemarks(dto.getRemarks());
        endpoint.setSystemOwner(dto.getSystemOwner());
        endpoint.setImRaised(dto.getImRaised());
        return endpoint;
    }

    public EndpointResponseDTO responseDTO(Endpoint endpoint) {
        return new EndpointResponseDTO(
                endpoint.getEndpointId(),
                endpoint.getEndpointType(),
                endpoint.getStatus(),
                endpoint.getRemarks(),
                endpoint.getSystemOwner(),
                endpoint.getImRaised(),
                endpoint.getAgency().getAgencyId(),
                endpoint.getAgency().getAgencyName()
        );
    }
}
