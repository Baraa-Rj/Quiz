package com.example.baraarj;

public class Name implements Comparable<Name> {
    private String name;
    private Integer frequency;
    private char gender;

    public Name() {
    }

    public Name(String name, Integer frequency, char gender) {
        this.name = name;
        this.frequency = frequency;
        this.gender = gender;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getFrequency() {
        return frequency;
    }

    public void setFrequency(Integer frequency) {
        this.frequency = frequency;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    @Override
    public int compareTo(Name o) {
        String secondName = o.getName();
        int firstNameInt = Integer.parseInt(this.getName());
        int secondNameInt = Integer.parseInt(secondName);

        return firstNameInt - secondNameInt;
    }

    @Override
    public String toString() {
        return name + "," + frequency + "," + gender+"\n";
    }
}
