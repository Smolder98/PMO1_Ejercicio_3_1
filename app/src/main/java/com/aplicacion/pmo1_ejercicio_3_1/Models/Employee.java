package com.aplicacion.pmo1_ejercicio_3_1.Models;

public class Employee {
    private Long id;
    private String names, surnames, direction, job;
    private int age;

    public Employee() {
    }

    public Employee(Long id, String names, String surnames, String direction, String job, int age) {
        this.id = id;
        this.names = names;
        this.surnames = surnames;
        this.direction = direction;
        this.job = job;
        this.age = age;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getSurnames() {
        return surnames;
    }

    public void setSurnames(String surnames) {
        this.surnames = surnames;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
