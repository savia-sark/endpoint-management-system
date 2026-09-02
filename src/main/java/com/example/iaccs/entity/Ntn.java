package com.example.iaccs.entity;

import jakarta.persistence.*;

@Entity
@Table(name="ntns")
public class Ntn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ntn_id")
    private Integer ntnId;

    @Column(name="ntn_name",nullable = false, unique = true)
    private String ntnName;

    @ManyToOne
    @JoinColumn(name = "command_id")
    private Command command;

    public Ntn() {
    }

    public Integer getNtnId() {
        return ntnId;
    }

    public String getNtnName() {
        return ntnName;
    }

    public Command getCommand() {
        return command;
    }

    public void setNtnId(Integer ntnId) {
        this.ntnId = ntnId;
    }

    public void setNtnName(String ntnName) {
        this.ntnName = ntnName;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    @Override
    public String toString() {
        return "Ntn{" +
                "ntnId=" + ntnId +
                ", ntnName='" + ntnName + '\'' +
                ", command=" + command +
                '}';
    }
}
