package com.senla.autoservice.bean;

import com.senla.autoservice.bean.aentity.AEntity;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "`master`")
public class Master extends AEntity {

    @Type(type = "org.hibernate.type.NumericBooleanType")
    @Column(name = "isWork")
    private Boolean isWork;
    @Column(name = "nameMaster", length = 45)
    private String nameMaster;

    public Master(Integer id, String name, boolean isWork) {
        super(id);
        setNameMaster(name);
        this.isWork = isWork;
    }

    public Master() {
    }

    public String getNameMaster() {
        return nameMaster;
    }

    public void setNameMaster(String name) {
        this.nameMaster = name;
    }

    public void setIsWork(boolean isWork) {
        this.isWork = isWork;
    }

    public boolean getIsWork() {
        return isWork;
    }

    public String toString() {
        String message = getId() + "," + getNameMaster() + "," + getIsWork();
        return message;
    }
}
