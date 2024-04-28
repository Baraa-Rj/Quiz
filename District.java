package com.example.baraarj;

import java.time.LocalDate;

public class District implements Comparable<District> {
    private String district;
    private LinkedList<Location> locationList = new LinkedList<>();

    public District() {
    }

    public District(String district) {
        this.district = district;
    }

    @Override
    public int compareTo(District o) {
        return this.district.compareToIgnoreCase(o.district);
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public LinkedList<Location> getLocationList() {
        return locationList;
    }

    public int getTotalMartyrs() {
        int totalMartyrs = 0;
        Node<Location> current = locationList.getHead().getNext();
        while (current != null) {
            totalMartyrs += current.getData().getMartyrsList().length();
            current = current.getNext();
        }
        return totalMartyrs;
    }

    public int getTotalFemale() {
        int totalFemale = 0;
        Node<Location> current = locationList.getHead().getNext();
        while (current != null) {
            Node<Martyr> currentMartyr = current.getData().getMartyrsList().getHead().getNext();
            if (currentMartyr != null && currentMartyr.getData().getGender().equals("Female")) {
                totalFemale++;
            }
        }
        return totalFemale;
    }

    public int getTotalMale() {
        int totalMales = 0;
        Node<Location> current = locationList.getHead().getNext();
        while (current != null) {
            Node<Martyr> currentMartyr = current.getData().getMartyrsList().getHead().getNext();
            if (currentMartyr != null && currentMartyr.getData().getGender().equals("Male")) {
                totalMales++;
            }
        }
        return totalMales;
    }

    public LocalDate mostFrequncedDate() {
        int[] dates = new int[31];
        Node<Location> current = locationList.getHead().getNext();
        while (current != null) {
            Node<Martyr> currentMartyr = current.getData().getMartyrsList().getHead().getNext();
            while (currentMartyr != null) {
                dates[currentMartyr.getData().getDateOfDeath().getDayOfMonth()]++;
                currentMartyr = currentMartyr.getNext();
            }
            current = current.getNext();
        }
        int max = 0;
        int maxIndex = 0;
        for (int i = 0; i < dates.length; i++) {
            if (dates[i] > max) {
                max = dates[i];
                maxIndex = i;
            }
        }
        if (maxIndex == 0)
            return LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);

        return LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), maxIndex);
    }

    public int getAverageAge() {
        int totalAge = 0;
        int totalMartyrs = 0;
        Node<Location> current = locationList.getHead().getNext();
        while (current != null) {
            Node<Martyr> currentMartyr = current.getData().getMartyrsList().getHead().getNext();
            while (currentMartyr != null) {
                totalAge += currentMartyr.getData().getAge();
                totalMartyrs++;
                currentMartyr = currentMartyr.getNext();
            }
            current = current.getNext();
        }
        if (totalMartyrs == 0) {
            return 0;
        }
        return totalAge / totalMartyrs;
    }

    @Override
    public String toString() {
        return district + " ";
    }
}
