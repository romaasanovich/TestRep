package com.senla.autoservice.bean;

import com.senla.autoservice.bean.aentity.AEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "`place`")
public class Place extends AEntity {
    @Column(name = "placeName", length = 45)
    private String placeName;
    @Column(name = "isBusy")
    private Boolean isBusy;

    public Place(Integer id, String name) {
        super(id);
        this.placeName = name;
        isBusy = false;
    }

    public Place(Integer id, String name, boolean isBusy) {
        super(id);
        this.placeName = name;
        this.isBusy = isBusy;
    }

    public Place(String line) {
        super(0);
        String[] temp = line.split(",");
        setId(Integer.valueOf(temp[0]));
        this.placeName = temp[1];
        isBusy = false;
    }

    public Place() {
    }

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public Boolean getIsBusy() {
        return isBusy;
    }

    public void setIsBusy(Boolean busy) {
        isBusy = busy;
    }

    @Override
    public String toString() {
        String s = getId() + "," + getPlaceName() + "," + getIsBusy();
        return s;
    }
}
