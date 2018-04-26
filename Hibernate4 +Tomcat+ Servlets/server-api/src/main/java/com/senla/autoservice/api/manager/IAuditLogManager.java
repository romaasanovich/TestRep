package com.senla.autoservice.api.manager;

import com.senla.autoservice.bean.AuditLog;

import java.util.List;

public interface IAuditLogManager {
    void add(AuditLog entity);

    AuditLog get(int id);

    List<AuditLog> getAll(String column, String value);
}
