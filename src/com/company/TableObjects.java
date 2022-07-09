package com.company;

import java.util.*;
import java.util.stream.Collectors;

class Group {
    public int id;
    public String num;

    public Group(int id, String num) {
        this.id = id;
        this.num = num;
    }

    public static Group findGroupById(int id) {
        try {
            return TableObjectsBox.groups.stream()
                    .filter(g -> g.id==id)
                    .toArray(Group[]::new)[0];
        } catch (Exception e) {
            return null;
        }
    }
}

class User {
    public int id;
    public String login;
    public String password;
    public int id_group;

    TreeSet<Integer> controlledUserId = new TreeSet<>();

    public User(int id, String login, String password, int id_group) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.id_group = id_group;
    }

    // установить/обновить список подчиненных пользователей
    public void updateControlledUserId(Main main) {
        controlledUserId = new TreeSet<>();

        main.table.updateUserRoles();
        main.table.updateUsers();

        ArrayList<UserRole> userRoles = TableObjectsBox.userRoles;
        ArrayList<Role> roles = TableObjectsBox.roles;
        ArrayList<User> users = TableObjectsBox.users;

        for (UserRole ur : userRoles) {
            if (ur.id_user == id) {
                if (ur.id_role == Role.findIdByName("super_user")) {
                    controlledUserId.addAll(users.stream()
                            .map(u -> u.id)
                            .collect(Collectors.toSet()));
                    return;
                }
                if (ur.id_role == Role.findIdByName(roles, "verificator")) {
                    controlledUserId.addAll(users.stream()
                            .filter(u -> u.id_group==ur.verify_group_id)
                            .map(u -> u.id)
                            .collect(Collectors.toSet()));
                }
                if (ur.id_role == Role.findIdByName(roles, "default_user")) {
                    controlledUserId.add(id);
                }
            }
        }
        System.out.println(Arrays.toString(controlledUserId.toArray()));
    }

    public static User findUserById(int id) {
        try {
            return TableObjectsBox.users.stream()
                    .filter(u -> u.id==id)
                    .toArray(User[]::new)[0];
        } catch (Exception e) {
            return null;
        }
    }
}

class Quote {
    public int id;
    public String subject;
    public String teacher;
    public String quote;
    public Date date;
    public int user_id;

    public Quote(int id, String subject, String teacher, String quote, Date date, int user_id) {
        this.id = id;
        this.subject = subject;
        this.teacher = teacher;
        this.quote = quote;
        this.date = date;
        this.user_id = user_id;
    }
}

class UserRole {
    public int id_user;
    public int id_role;
    public Integer verify_group_id;

    public UserRole(int id_user, int id_role, Integer verify_group_id) {
        this.id_user = id_user;
        this.id_role = id_role;
        this.verify_group_id = verify_group_id;
    }
}

class Role {
    public int id;
    public String name;

    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public static int findIdByName(ArrayList<Role> roles, String name) {
        try {
            return roles.stream()
                    .filter(r -> r.name.equals(name))
                    .toArray(Role[]::new)[0].id;
        } catch (Exception e) {
            return -1;
        }
    }

    public static int findIdByName(String name) {
        try {
            return TableObjectsBox.roles.stream()
                    .filter(r -> r.name.equals(name))
                    .toArray(Role[]::new)[0].id;
        } catch (Exception e) {
            return -1;
        }
    }
}