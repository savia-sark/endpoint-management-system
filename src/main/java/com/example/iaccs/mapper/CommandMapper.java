package com.example.iaccs.mapper;

import com.example.iaccs.dto.CommandRequestDTO;
import com.example.iaccs.dto.CommandResponseDTO;
import com.example.iaccs.entity.Command;
import org.springframework.stereotype.Component;

@Component
public class CommandMapper {

//    public CommandRequestDTO requestDTO(Command command) {
//        return new CommandRequestDTO(
//                command.getCommandName()
//        );
//    }

    public Command requestEntity(CommandRequestDTO dto) {

        Command command = new Command();
        command.setCommandName(dto.getCommandName());
        return command;
    }

    public CommandResponseDTO responseDTO(Command command) {
        return new CommandResponseDTO(
                command.getCommandId(),
                command.getCommandName()
        );
    }


//    public Command responseEntity(CommandResponseDTO dto) {
//        Command command = new Command();
//        command.setCommandId(dto.getCommandId());
//        command.setCommandName(dto.getCommandName());
//
//        return command;
//    }
}
