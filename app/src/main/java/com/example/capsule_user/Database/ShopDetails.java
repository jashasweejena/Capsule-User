package com.example.capsule_user.Database;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capsule_user.Adapters.ShopRecyclerviewAdapter;
import com.example.capsule_user.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ShopDetails extends AppCompatActivity {
    final static String TAG = "ShopDetails";

    private RecyclerView rv;
    private DatabaseReference myRef;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initialize();

        Intent intent = getIntent();
        String name = intent.getStringExtra(getString(R.string.shop_name));
        getMeds(name);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void getMeds(String shopName) {
        DatabaseReference ref = myRef.child("shop");
        ref.orderByChild("name").equalTo(shopName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                MedsList m = dataSnapshot.getValue(MedsList.class);

                assert m != null;
                List<Medicine> medsList = m.getMedicines();

//                TODO: Proper implementation of rv reqd. This is a jugaad.
                if (rv != null) {
                    rv.setAdapter(new ShopRecyclerviewAdapter(medsList, ShopDetails.this));
                } else {
                    Toast.makeText(ShopDetails.this, "Rv null", Toast.LENGTH_SHORT).show();
                    rv = findViewById(R.id.recyclerview_meds);
                }
                assert m != null;
                Log.d(TAG, "onChildAdded: " + m.getMedicines().get(0).getDesc());

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initialize() {
        rv = findViewById(R.id.recyclerview_meds);
        rv.setLayoutManager(new LinearLayoutManager(this));
        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
    }

}
