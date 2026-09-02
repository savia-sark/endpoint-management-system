package com.example.iaccs.mapper;

import com.example.iaccs.dto.NtnRequestDTO;
import com.example.iaccs.dto.NtnResponseDTO;
import com.example.iaccs.entity.Command;
import com.example.iaccs.entity.Ntn;
import org.springframework.stereotype.Component;

@Component
public class NtnMapper {

    public Ntn requestEntity(NtnRequestDTO dto) {
        //Command assignment ia already handled in service Layer
        Ntn ntn = new Ntn();
        ntn.setNtnName(dto.getNtnName());
        return ntn;
    }

    public NtnResponseDTO responseDTO(Ntn ntn) {
        return new NtnResponseDTO(
                ntn.getNtnId(),
                ntn.getNtnName(),
                ntn.getCommand().getCommandId(),
                ntn.getCommand().getCommandName()
        );
    }
    
}
