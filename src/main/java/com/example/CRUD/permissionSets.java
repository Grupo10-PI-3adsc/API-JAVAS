package com.example.CRUD;

public enum permissionSets {
    SYS_ADM("sys_adm"),
    GERENTE("gerente"),
    FUNC("func"),
    USER("user");

    private String role;

    permissionSets(String nicelAcesso) {
        this.role = nicelAcesso;
    }

    public String getRole() {
        return role;
    }
}