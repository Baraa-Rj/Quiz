package com.example.baraarj;

import java.time.LocalDate;

public class Martyr implements Comparable<Martyr> {
    private String name;
    private int age;
    private String location;
    private LocalDate dateOfDeath;
    private String district;
    private String gender;

    public Martyr() {
    }

    public Martyr(String name, LocalDate dateOfDeath, int age, String location, String district, String gender) {
        this.name = name;
        this.age = age;
        this.location = location;
        this.dateOfDeath = dateOfDeath;
        this.district = district;
        this.gender = gender;
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
        if (age < 0||age > 100)
            System.out.println("Error: Invalid age: " + age);
        else
            this.age = age;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LocalDate getDateOfDeath() {
        return dateOfDeath;
    }

    public void setDateOfDeath(LocalDate dateOfDeath) {
        this.dateOfDeath = dateOfDeath;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        if (gender.equalsIgnoreCase("male") || gender.equalsIgnoreCase("female"))
            this.gender = gender;
        else
            System.out.println("Error: Invalid gender: " + gender);
    }

    @Override
    public int compareTo(Martyr o) {
        return this.age - o.age;
    }


    @Override
    public String toString() {
        return "Martyr{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", location='" + location + '\'' +
                ", dateOfDeath=" + dateOfDeath +
                ", district='" + district + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
