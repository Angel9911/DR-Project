package com.example.authservice.entity;

public class Roles {
    private Long role_id;
    private Role roleDescription;

    public Roles(Long role_id, Role roleDescription) {
        this.role_id = role_id;
        this.roleDescription = roleDescription;
    }

    public Long getRole_id() {
        return role_id;
    }

    public void setRole_id(Long role_id) {
        this.role_id = role_id;
    }

    public Role getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(Role roleDescription) {
        this.roleDescription = roleDescription;
    }
}
