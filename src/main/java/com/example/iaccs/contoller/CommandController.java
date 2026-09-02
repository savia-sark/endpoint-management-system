package com.example.iaccs.contoller;

import com.example.iaccs.dto.CommandRequestDTO;
import com.example.iaccs.dto.CommandResponseDTO;
import com.example.iaccs.service.CommandService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commands")
public class CommandController {

    @Autowired
    private CommandService service;

    @PostMapping
    public CommandResponseDTO createCommand(
            @Valid @RequestBody CommandRequestDTO requestDTO) {

        return service.createCommand(requestDTO);
    }

    @GetMapping
    public List<CommandResponseDTO> getAllCommands(){
        return service.getAllCommands();
    }
    @GetMapping("/{id}")
    public CommandResponseDTO getCommand(@PathVariable Integer id)
    {
        return service.getCommandById(id);
    }

    // Update Command
    @PutMapping("/{id}")
    public CommandResponseDTO updateCommand(
            @PathVariable Integer id,
            @Valid @RequestBody CommandRequestDTO requestDTO) {

        return service.updateCommand(id, requestDTO);
    }

    @DeleteMapping("/{id}")
    public String deleteCommand(
            @PathVariable Integer id) {

        service.deleteCommand(id);
        return "Command deleted successfully.";
    }

}
