package com.senla.autoservice.api.manager;

import com.senla.autoservice.bean.User;

import java.util.List;

public interface IUserManager {

    void add(User entity);
    User getById(int id);
    List<User> getAll(String column, String value);
}
