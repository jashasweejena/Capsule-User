package com.example.capsule_user.Orders;

import android.content.Intent;
import android.os.Bundle;

import com.example.capsule_user.Database.Medicine;
import com.example.capsule_user.Database.MedsList;
import com.example.capsule_user.Database.Order;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capsule_user.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CheckoutActivity extends AppCompatActivity {
    final private static String TAG = "CheckoutActivity";

    private EditText inputName;
    private EditText inputAddress;
    private TextView selectedMedsTextView;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private String databaseKey;
    private String userUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String selectedList = "";

        initialize();

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        final ArrayList<Medicine> selectedMeds = (ArrayList<Medicine>) args.getSerializable(getString(R.string.selected_meds_list));
        databaseKey = intent.getStringExtra(getString(R.string.parent_database));

        //Add meds to database
        for(Medicine medicine : selectedMeds){
            selectedList += medicine.getDesc() + " ";
        }

        //Show the list in textview
        selectedMedsTextView.setText(selectedList);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = inputName.getText().toString();
                String address = inputAddress.getText().toString();
                Log.d(TAG, "onClick: " + databaseKey);

                Log.d(TAG, "onClick: " + selectedMeds);
                Toast.makeText(CheckoutActivity.this, "" + selectedMeds, Toast.LENGTH_SHORT).show();

                if(name.isEmpty() || address.isEmpty()){
                    Snackbar.make(view, "Please fill up all the fields", Snackbar.LENGTH_SHORT).show();
                }
                else {
                    writeToDatabase(name, address, selectedMeds);
                }
            }
        });
    }

    private void initialize() {
        inputName = findViewById(R.id.input_checkout_name);
        inputAddress = findViewById(R.id.input_checkout_address);
        selectedMedsTextView = findViewById(R.id.text_checkout_meds);

        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("shop");
        userUID = FirebaseAuth.getInstance().getUid();
    }

    private void writeToDatabase(String name, String address, List<Medicine> medsList){
        DatabaseReference reference = myRef.child(databaseKey).child("orders");
        Order order = new Order(name, address, medsList);
        reference.child(userUID).setValue(order);

    }

}
