package com.example.iaccs.entity.enums;

import java.util.List;

public enum Role {
    SUPER_ADMIN(5),
    ADMIN(4),
    COMMAND_HEAD(3),
    NTN_HEAD(2),
    AGENCY_HEAD(1);

    private final int level;

    Role(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public boolean isSuperiorTo(Role other) {
        return this.level > other.level;
    }

        public List<Role> manageableRoles() {
            return switch (this) {
                case SUPER_ADMIN -> List.of(
                        ADMIN,
                        COMMAND_HEAD,
                        NTN_HEAD,
                        AGENCY_HEAD
                );

                case ADMIN -> List.of(
                        COMMAND_HEAD,
                        NTN_HEAD,
                        AGENCY_HEAD
                );

                case COMMAND_HEAD -> List.of(
                        NTN_HEAD,
                        AGENCY_HEAD
                );

                case NTN_HEAD -> List.of(
                        AGENCY_HEAD
                );

                case AGENCY_HEAD -> List.of();
            };
    }

}