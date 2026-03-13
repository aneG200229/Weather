package org.aneg.dao;

import org.aneg.model.User;

import java.util.Optional;

public interface UserDao {
    void save(User user);

    Optional<User> findByLogin(String login);
}
