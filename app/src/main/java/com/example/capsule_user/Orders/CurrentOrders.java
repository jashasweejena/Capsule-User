package com.example.capsule_user.Orders;

import android.location.Location;
import android.os.Bundle;

import com.example.capsule_user.Database.Order;
import com.example.capsule_user.Location.LocationModel;
import com.example.capsule_user.Location.MapsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.capsule_user.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class CurrentOrders extends AppCompatActivity {
    final private static String TAG = "CurrentOrders";

    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_orders);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



    }

    private MutableLiveData<String> trackChanges() {
        final MutableLiveData<String> mutableLiveData = new MutableLiveData<>();

        DatabaseReference ref = myRef.child("location");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                LocationModel loc = dataSnapshot.getValue(LocationModel.class);
                String locString;
                locString = String.format(Locale.ENGLISH, "%f:%f", loc.getLatitude(), loc.getLongitude());

                Log.d(TAG, "onDataChange: " + locString);

                mutableLiveData.postValue(locString);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return mutableLiveData;

    }

    public void openFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
