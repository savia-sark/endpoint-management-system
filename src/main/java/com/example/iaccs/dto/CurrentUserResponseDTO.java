package com.example.iaccs.dto;

import com.example.iaccs.entity.enums.Role;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CurrentUserResponseDTO {

    private String username;
    private Role role;

    private Integer commandId;
    private String commandName;

    private Integer ntnId;
    private String ntnName;

    private Integer agencyId;
    private String agencyName;

    private Integer agencyTypeId;
    private String agencyTypeName;

    // getters and setters

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
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

    public Integer getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(Integer agencyId) {
        this.agencyId = agencyId;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public Integer getAgencyTypeId() {
        return agencyTypeId;
    }

    public void setAgencyTypeId(Integer agencyTypeId) {
        this.agencyTypeId = agencyTypeId;
    }

    public String getAgencyTypeName() {
        return agencyTypeName;
    }

    public void setAgencyTypeName(String agencyTypeName) {
        this.agencyTypeName = agencyTypeName;
    }
}
