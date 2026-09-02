package com.example.iaccs.dto;

import jakarta.validation.constraints.NotBlank;

public class CommandRequestDTO {
    @NotBlank(message = "CommandName is required")
    private String commandName;

    public CommandRequestDTO(String commandName) {
        this.commandName = commandName;
    }

    public CommandRequestDTO() {
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }
}
