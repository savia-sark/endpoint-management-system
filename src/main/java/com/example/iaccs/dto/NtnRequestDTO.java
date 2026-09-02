package com.example.iaccs.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class NtnRequestDTO {
    @NotBlank(message = "NTNName is required")
    private String ntnName;
    @NotNull(message = "CommandId is required")
    private Integer commandId;

    public NtnRequestDTO(String ntnName, Integer commandId) {
        this.ntnName = ntnName;
        this.commandId = commandId;
    }

    public NtnRequestDTO() {
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
}
