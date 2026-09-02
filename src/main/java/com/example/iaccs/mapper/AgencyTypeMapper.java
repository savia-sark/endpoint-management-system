package com.example.iaccs.mapper;

import com.example.iaccs.dto.AgencyTypeRequestDTO;
import com.example.iaccs.dto.AgencyTypeResponseDTO;
import com.example.iaccs.entity.AgencyType;
import org.springframework.stereotype.Component;

@Component
public class AgencyTypeMapper {

//    public AgencyTypeRequestDTO requestDTO(AgencyType agencyType) {
//        return new AgencyTypeRequestDTO(
//                agencyType.getTypeName()
//        );
//    }

    public AgencyType requestEntity(AgencyTypeRequestDTO dto) {

        AgencyType agencyType = new AgencyType();
        agencyType.setTypeName(dto.getAgencyTypeName());
        return agencyType;
    }

    public AgencyTypeResponseDTO responseDTO(AgencyType agencyType) {
        return new AgencyTypeResponseDTO(
                agencyType.getAgencyTypeId(),
                agencyType.getTypeName()
        );
    }


//    public AgencyType responseEntity(AgencyTypeResponseDTO dto) {
//        AgencyType agencyType = new AgencyType();
//        agencyType.setAgencyTypeId(dto.getAgencyTypeId());
//        agencyType.setTypeName(dto.getAgencyTypeName());
//
//        return agencyType;
//    }
}
