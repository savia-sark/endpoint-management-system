package com.example.iaccs.service;

import com.example.iaccs.dto.CommandRequestDTO;
import com.example.iaccs.dto.CommandResponseDTO;
import com.example.iaccs.entity.Command;
import com.example.iaccs.exception.DuplicateResourceException;
import com.example.iaccs.exception.ResourceNotFoundException;
import com.example.iaccs.mapper.CommandMapper;
import com.example.iaccs.repository.CommandRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommandService {

    private final CommandRepo repository;

    @Autowired
    private CommandMapper mapper;

    public CommandService(CommandRepo repository) {
        this.repository = repository;
    }

    public List<CommandResponseDTO> getAllCommands() {

         return repository.findAll()
                .stream()
                .map(mapper::responseDTO)
                .toList();
    }

    public CommandResponseDTO getCommandById(Integer id) {
        Command com= repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Command not found with id: "+id));

        return mapper.responseDTO(com);
    }

    public CommandResponseDTO createCommand(CommandRequestDTO commandRequestDTO) {
        if( repository.existsByCommandName(commandRequestDTO.getCommandName()))
        {
            throw new DuplicateResourceException("Command already exists.");
        }

        Command com=mapper.requestEntity(commandRequestDTO);
        return mapper.responseDTO(repository.save(com));
    }

    public CommandResponseDTO updateCommand(Integer id, CommandRequestDTO command) {

        Command existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Command not found with id: "+id));

        existing.setCommandName(command.getCommandName());

        return mapper.responseDTO(repository.save(existing));
    }

    public void deleteCommand(Integer id) {
        repository.deleteById(id);
    }
}
