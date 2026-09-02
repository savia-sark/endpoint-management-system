package com.example.iaccs.dto;

public class NtnResponseDTO {
    private Integer ntnId;
    private String ntnName;
    private Integer commandId;
    private String commandName;
    public NtnResponseDTO() {
    }

    public NtnResponseDTO(Integer ntnId, String ntnName, Integer commandId,String commandName) {
        this.ntnId = ntnId;
        this.ntnName = ntnName;
        this.commandId = commandId;
        this.commandName=commandName;

    }

    public Integer getNtnId() {
        return ntnId;
    }

    public void setNtnId(Integer ntnId) {
        this.ntnId = ntnId;
    }

    public String getNtnName() {
        return ntnName;
    }

    public void setNtnName(String ntnName) {
        this.ntnName = ntnName;
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
