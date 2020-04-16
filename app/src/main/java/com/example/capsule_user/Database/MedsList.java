package com.example.capsule_user.Database;

import java.util.List;

public class MedsList {
    private List<Medicine> medicines;
    private String name;

    public MedsList() {
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<Medicine> medicines) {
        this.medicines = medicines;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
