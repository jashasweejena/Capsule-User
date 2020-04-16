package com.example.capsule_user.Database;

public class Shop {
    Medicine medicine;

    public Shop() {
    }

    public Shop(Medicine medicine) {
        this.medicine = medicine;
    }

    public Medicine getMedicine() {
        return medicine;
    }

    public void setMedicine(Medicine medicine) {
        this.medicine = medicine;
    }
}
