package com.example.healthcare.Models;

public class Direction {
    private int id;
    private String name;
    private int id_specialization;
    private Specialization specialization;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId_specialization() {
        return id_specialization;
    }

    public void setId_specialization(int id_specialization) {
        this.id_specialization = id_specialization;
    }

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }
}
