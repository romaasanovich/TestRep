package com.senla.autoservice.api.dao;

import com.senla.autoservice.bean.aentity.AEntity;
import org.hibernate.Session;

import java.sql.SQLException;

public interface IDao {
    public AEntity getById(int id, Session session) throws  SQLException;
}
