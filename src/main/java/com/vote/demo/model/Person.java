package com.vote.demo.model;

public class Person {
    private long id; // Documento
    private String name;
    private int age;
    private Gender gender;
    private Boolean alive;

    public Person() {}

    public Person(long id, String name, int age, Gender gender, Boolean alive) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.alive = alive;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }

    public Boolean getAlive() { return alive; } 
    public void setAlive(Boolean alive) { this.alive = alive; } 
}
