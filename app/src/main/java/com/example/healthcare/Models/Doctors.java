package com.example.healthcare.Models;

public class Doctors{
    private int id;
    private String FIO;
    private String methods;
    private String education;
    private int id_clinic;
    private Object avatar_name;
    private int id_direction;
    private String work_experience;
    private Clinic clinic;

    public Direction getDirections() {
        return directions;
    }

    public void setDirections(Direction directions) {
        this.directions = directions;
    }

    private Direction directions;

    public Clinic getClinic() {
        return clinic;
    }

    public void setClinic(Clinic clinic) {
        this.clinic = clinic;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getMethods() {
        return methods;
    }

    public void setMethods(String methods) {
        this.methods = methods;
    }

    public int getId_clinic() {
        return id_clinic;
    }

    public void setId_clinic(int id_clinic) {
        this.id_clinic = id_clinic;
    }

    public Object getAvatar_name() {
        return avatar_name;
    }

    public void setAvatar_name(Object avatar_name) {
        this.avatar_name = avatar_name;
    }

    public int getId_direction() {
        return id_direction;
    }

    public void setId_direction(int id_direction) {
        this.id_direction = id_direction;
    }

    public String getWork_experience() {
        return work_experience;
    }

    public void setWork_experience(String work_experience) {
        this.work_experience = work_experience;
    }
}
