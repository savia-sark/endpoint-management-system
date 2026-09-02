package com.example.iaccs.mapper;

import com.example.iaccs.dto.AgencyRequestDTO;
import com.example.iaccs.dto.AgencyResponseDTO;
import com.example.iaccs.entity.Agency;
import org.springframework.stereotype.Component;

@Component
public class AgencyMapper {

    public Agency requestEntity(AgencyRequestDTO dto) {
        //AgencyTye and Ntn are assigned inside Service Layer Itself
        Agency agency = new Agency();
        agency.setAgencyName(dto.getAgencyName());
        return agency;
    }

    public AgencyResponseDTO responseDTO(Agency agency) {
        return new AgencyResponseDTO(
                agency.getAgencyId(),
                agency.getAgencyName(),
                agency.getAgencyType().getAgencyTypeId(),
                agency.getAgencyType().getTypeName(),
                agency.getNtn().getNtnId(),
                agency.getNtn().getNtnName());
    }
}
