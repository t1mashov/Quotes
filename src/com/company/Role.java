package com.company;

abstract class Role {}

class DefaultUser extends Role {}

class Verificator extends Role {
    private int id_group;

    public void setIdGroup(int id_group) {
        this.id_group = id_group;
    }

    public int getIdGroup() {
        return id_group;
    }
}

class SuperUser extends Role {}