package com.example.iaccs.service;

import com.example.iaccs.dto.NtnRequestDTO;
import com.example.iaccs.dto.NtnResponseDTO;
import com.example.iaccs.entity.Command;
import com.example.iaccs.entity.Ntn;
import com.example.iaccs.exception.DuplicateResourceException;
import com.example.iaccs.exception.ResourceNotFoundException;
import com.example.iaccs.mapper.NtnMapper;
import com.example.iaccs.repository.CommandRepo;
import com.example.iaccs.repository.NtnRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NtnService {
    private final NtnRepository repository;
    private final CommandRepo commandRepo;

    @Autowired
    private NtnMapper mapper;

    public NtnService(NtnRepository repository, CommandRepo commandRepo) {
        this.repository = repository;
        this.commandRepo = commandRepo;
    }

    public List<NtnResponseDTO> getAllNtns(){
        return repository.findAllWithCommand()
                .stream()
                .map(mapper::responseDTO)
                .toList();
    }

    public NtnResponseDTO getNtnById(Integer id){
        Ntn ntn= repository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Ntn not Found with id: "+id));

        return mapper.responseDTO(ntn);
    }

    public NtnResponseDTO createNtn(NtnRequestDTO ntnRequestDTO){
        if(repository.existsByNtnName(ntnRequestDTO.getNtnName()))
        {
            throw new DuplicateResourceException("NTN already exists.");
        }
        Ntn ntn1= mapper.requestEntity(ntnRequestDTO);

        Command com=commandRepo.findById(ntnRequestDTO.getCommandId())
                .orElseThrow(() -> new ResourceNotFoundException("Command not found with id: "+ntnRequestDTO.getCommandId()));
        ntn1.setCommand(com);
        return mapper.responseDTO(repository.save(ntn1));
    }

    public NtnResponseDTO updateNtn(Integer id, NtnRequestDTO ntnRequestDTO){
        Ntn existing= repository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("Ntn not Found with id: "+id));

        Command com=commandRepo.findById(ntnRequestDTO.getCommandId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Command not found with id: " + ntnRequestDTO.getCommandId()));
        existing.setNtnName(ntnRequestDTO.getNtnName());
        existing.setCommand(com);

        return  mapper.responseDTO(repository.save(existing));
    }

    public void deleteNtn(Integer id){
        repository.deleteById(id);
    }
}
