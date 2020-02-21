package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.List;

public class UsersUtil {

    public static final List<User> USERS = Arrays.asList(
            new User(null, "Вася", "email1.com", "password1", Role.ROLE_USER),
            new User(null, "Коля", "email2.com", "password2", Role.ROLE_USER),
            new User(null, "Петя", "email3.com", "password3", Role.ROLE_ADMIN),
            new User(null, "Иван", "email4.com", "password4", Role.ROLE_USER),
            new User(null, "Вова", "email5.com", "password5", Role.ROLE_USER)
    );
}
