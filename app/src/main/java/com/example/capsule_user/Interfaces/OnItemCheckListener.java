package com.example.capsule_user.Interfaces;

import com.example.capsule_user.Database.Medicine;

public interface OnItemCheckListener {
    void onItemCheck(Medicine item);
    void onItemUncheck(Medicine item);
}