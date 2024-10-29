package com.example.CRUD;

public enum permissionSets {
    SYS_ADM("sys_adm"),
    GERENTE("gerente"),
    FUNC("func"),
    USER("user");

    private String role;

    permissionSets(String nivelAcesso) {
        this.role = nivelAcesso;
    }

    public String getRole() {
        return role;
    }
}