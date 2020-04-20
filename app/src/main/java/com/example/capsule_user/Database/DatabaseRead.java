package com.example.capsule_user.Database;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.capsule_user.Adapters.DatabaseRecyclerviewAdapter;
import com.example.capsule_user.Location.MapsActivity;
import com.example.capsule_user.Orders.CurrentOrders;
import com.example.capsule_user.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DatabaseRead extends AppCompatActivity {

    private static final String TAG = "DatabaseRead";
    private static MutableLiveData<ArrayList<String>> mutableStrings = new MutableLiveData<>();
    DatabaseReference myRef;
    private FirebaseDatabase database;
    private RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database_read);

        Log.d(TAG, "onCreate: " + FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber() + " " + FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        initialize();

        refresh();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.action_refresh:
                refresh();
                break;


            case R.id.action_orders:{
                Intent intent = new Intent(this, MapsActivity.class);
                startActivity(intent);
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void initialize() {
        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference();
    }

    private Medicine writeMedicines() {
        Random rand = new Random();
        Medicine med = new Medicine();
        med.setDesc("" + System.currentTimeMillis());
        med.setPrice(rand.nextInt(1001));
        med.setQty(rand.nextInt(5001));

        return med;
    }

    //    TODO: For client app
    private void writeShops() {
        Medicine med1 = writeMedicines();
        Medicine med2 = writeMedicines();
        Medicine med3 = writeMedicines();

        Shop shop1 = new Shop();

        shop1.setMedicine(med1);

        MedsList meds = new MedsList();
        List<Medicine> medsList = new ArrayList<>();
        medsList.add(med1);
        medsList.add(med2);
        medsList.add(med3);
        meds.setMedicines(medsList);
        meds.setName("" + System.currentTimeMillis());
        myRef.child("shop").push().setValue(meds);
    }

    private MutableLiveData<ArrayList<String>> filter() {
        final ArrayList<String> nameList = new ArrayList<>();
        myRef.child("shop").orderByChild("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot next : dataSnapshot.getChildren()) {
                    String name = next.child("name").getValue(String.class);
                    nameList.add(name);
                }
                mutableStrings.postValue(nameList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return mutableStrings;

    }

    private void refresh(){
        rv = findViewById(R.id.recyclerview);
        rv.setLayoutManager(new LinearLayoutManager(this));
        filter().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                DatabaseRecyclerviewAdapter adapter = new DatabaseRecyclerviewAdapter(strings, DatabaseRead.this);
                rv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
