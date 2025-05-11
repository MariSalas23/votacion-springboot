
package com.vote.demo.model;

public class Person {
    private String name;
    private int age;
    private Gender gender;
    private boolean alive;

    // Constructor vacío requerido por Spring
    public Person() {}

    public Person(String name, int age, Gender gender, boolean alive) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.alive = alive;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }
}
