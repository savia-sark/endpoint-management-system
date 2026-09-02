package com.example.iaccs.dto;

public class CommandResponseDTO {

    private Integer commandId;
    private String commandName;

    public CommandResponseDTO() {
    }

    public CommandResponseDTO(Integer commandId, String commandName) {
        this.commandId = commandId;
        this.commandName = commandName;
    }

    public Integer getCommandId() {
        return commandId;
    }

    public void setCommandId(Integer commandId) {
        this.commandId = commandId;
    }

    public String getCommandName() {
        return commandName;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }
}
