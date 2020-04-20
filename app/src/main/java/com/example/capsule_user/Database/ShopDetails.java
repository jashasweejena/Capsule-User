package com.example.capsule_user.Database;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capsule_user.Adapters.ShopRecyclerviewAdapter;
import com.example.capsule_user.Interfaces.OnItemCheckListener;
import com.example.capsule_user.Orders.CheckoutActivity;
import com.example.capsule_user.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShopDetails extends AppCompatActivity implements OnItemCheckListener {
    final static String TAG = "ShopDetails";

    private RecyclerView rv;
    private DatabaseReference myRef;
    private FirebaseDatabase database;
    private List<Medicine> selectedMeds;
    private String shopName;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initialize();

        Intent intent = getIntent();
        shopName = intent.getStringExtra(getString(R.string.shop_name));
        Toast.makeText(this, shopName, Toast.LENGTH_SHORT).show();
        getMeds();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick:  " + selectedMeds);
                Toast.makeText(ShopDetails.this, "" + selectedMeds, Toast.LENGTH_SHORT).show();

                //Send the selected list of medicines to CheckoutActivity.class
                Intent intent = new Intent(ShopDetails.this, CheckoutActivity.class);
                Bundle args = new Bundle();
                args.putSerializable(getString(R.string.selected_meds_list), (Serializable) selectedMeds);
                intent.putExtra("BUNDLE", args);
                intent.putExtra(getString(R.string.parent_database), key);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_refresh:
                getMeds();
        }

        return super.onOptionsItemSelected(item);
    }


    private void getMeds() {
        DatabaseReference ref = myRef.child("shop");
        ref.orderByChild("name").equalTo(shopName).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                key = dataSnapshot.getKey();

                MedsList m = dataSnapshot.getValue(MedsList.class);
                assert m != null;
                List<Medicine> medsList = m.getMedicines();

//                TODO: Proper implementation of rv reqd. This is a jugaad.
                ShopRecyclerviewAdapter adapter = new ShopRecyclerviewAdapter(medsList, ShopDetails.this, ShopDetails.this);
                if (rv != null) {
                    rv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(ShopDetails.this, "Rv null", Toast.LENGTH_SHORT).show();
                    rv = findViewById(R.id.recyclerview_meds);
                    rv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
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

        selectedMeds = new ArrayList<>();
    }

    @Override
    public void onItemCheck(Medicine item) {
        selectedMeds.add(item);
    }

    @Override
    public void onItemUncheck(Medicine item) {
        selectedMeds.remove(item);
    }


}
