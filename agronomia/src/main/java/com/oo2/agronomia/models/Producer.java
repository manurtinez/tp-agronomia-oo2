package com.oo2.agronomia.models;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Producer extends User {
    private String cuil;

    @OneToMany
    private List<Bolson> bolsones;

    public Producer() {
    }

    public Producer(String name, String email, String password, String cuil) {
        super(name, email, password);
        this.cuil = cuil;
        this.bolsones = new ArrayList<Bolson>();
    }

    public String getCuil() {
        return cuil;
    }

    public void setCuil(String cuil) {
        this.cuil = cuil;
    }

    public List<Bolson> getBolsones() {
        return bolsones;
    }

    public void setBolsones(List<Bolson> bolsones) {
        this.bolsones = bolsones;
    }
}
