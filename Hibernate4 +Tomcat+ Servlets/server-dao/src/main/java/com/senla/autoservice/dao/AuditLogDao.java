package com.senla.autoservice.dao;

import com.senla.autoservice.bean.AuditLog;
import com.senla.autoservice.dao.abstractdao.GenericDao;

public class AuditLogDao  extends GenericDao<AuditLog>{

    public AuditLogDao(){
        super(AuditLog.class);
    }
}
