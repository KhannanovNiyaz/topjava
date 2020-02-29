package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserUtils {

    public static final List<User> USERS = Arrays.asList(
            new User(2, "Niyaz", "niyaz@mail.ru", "qwerty", Role.ROLE_USER),
            new User(1, "Admin", "admin@mail.ru", "admin", Role.ROLE_ADMIN),
            new User(3, "Vasya", "vasya@mail.ru", "vasya123", Role.ROLE_USER)
    );

    public static List<User> sortedAllUser(Collection<User> users) {
        return users.stream()
                .sorted((o1, o2) -> o1.getName().equals(o2.getName())
                        ? o1.getEmail().compareTo(o2.getEmail())
                        : o1.getName().compareTo(o2.getName()))
                .collect(Collectors.toList());
    }

}
