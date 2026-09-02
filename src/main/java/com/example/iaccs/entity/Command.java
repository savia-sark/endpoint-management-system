package com.example.iaccs.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "commands")
public class Command {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "command_id")
    private Integer commandId;

    @Column(name="command_name",nullable = false, unique = true)
    private String commandName;

    // constructors
    public Command(Integer commandId, String commandName) {
        this.commandId = commandId;
        this.commandName = commandName;
    }

    public Command() {
    }

    // getters
    public Integer getCommandId() {
        return commandId;
    }

    public String getCommandName() {
        return commandName;
    }

    // setters
    public void setCommandId(Integer commandId) {
        this.commandId = commandId;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    @Override
    public String toString() {
        return "Command{" +
                "commandId=" + commandId +
                ", commandName='" + commandName + '\'' +
                '}';
    }
}
