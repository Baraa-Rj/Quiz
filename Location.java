package com.example.baraarj;

import java.time.LocalDate;

public class Location implements Comparable<Location> {
    private String location;
    private LinkedList<Martyr> martyrsList = new LinkedList<>();

    public Location() {
    }

    public int countMartyrsByDate(LocalDate date) {
        Node<Martyr> current = martyrsList.getHead();
        int count = 0;
        while (current != null) {
            if (current.getData().getDateOfDeath().isEqual(date)) {
                count++;
            }
            current = current.getNext();
        }
        return count;
    }

    public Location(String location, LinkedList<Martyr> martyrsList) {
        this.location = location;
        this.martyrsList = martyrsList;
    }

    public Location(String location) {
        this.location = location;
    }

    @Override
    public int compareTo(Location o) {
        return this.location.compareToIgnoreCase(o.location);
    }

    public LinkedList<Martyr> getMartyrsList() {
        return martyrsList;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return location+" ";}
}
