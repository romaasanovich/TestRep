package com.senla.autoservice.dao;

import com.senla.autoservice.bean.User;
import com.senla.autoservice.dao.abstractdao.GenericDao;

public class UserDao extends GenericDao<User> {

    public UserDao() {
        super(User.class);
    }


}
