package com.example.android.homedashboard;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test extends AppCompatActivity {


    private DatabaseReference reference;
    RecyclerView recyclerView;
    ArrayList<Profile> list;
    MyAdapter adapter;
    private Button button;
    float min =0 ;
    float treshold = (float) 0.75;
    private String kode;
    final List<String> kabehKode= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        recyclerView = (RecyclerView)findViewById(R.id.RecyTest);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<Profile>();


        reference = FirebaseDatabase.getInstance().getReference().child("DataBase").child("TabelGejala");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                    Profile p = dataSnapshot1.getValue(Profile.class);
                    list.add(p);
                }
                adapter = new MyAdapter(Test.this, list);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Test.this, "data rakono", Toast.LENGTH_SHORT).show();
            }
        });

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHasil();
            }
        });

    }

    public void openHasil(){

        if (adapter.gejalanya.size()>0){
            final Map<String, Float> hasilKemiripan = new HashMap<>();

            for (Profile profile : adapter.gejalanya){
                kabehKode.add(profile.getID_Gejala());
            }

            DatabaseReference kasus = FirebaseDatabase.getInstance().getReference().child("DataBase").child("TabelKasus");
            kasus.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                        float kemiripan = 0;
                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.child("Gejala").getChildren()){
                            if (kabehKode.contains(dataSnapshot2.getValue().toString())){
                                kemiripan++;
                            }
                        }
                        float pembagi = Math.max((float) (dataSnapshot.child("Gejala").getChildrenCount()), kabehKode.size());
                        float hasil_bagi = kemiripan/pembagi;

                        hasilKemiripan.put(dataSnapshot1.getKey(), hasil_bagi);
                    }

                    for (Map.Entry<String, Float> entry : hasilKemiripan.entrySet()){

                        if (entry.getValue() > min){
                            min = entry.getValue();
                            kode = entry.getKey();
                        }
                    }

                    if (kode!=null){
                        System.out.println(kode+"-"+min);
                        diagnosa(kode, min);
                    }
                    else {
                        Toast.makeText(Test.this, "Kasus Yang Mirip Tidak Ditemukan", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }


    private void diagnosa(String kode, float min) {
        final String kode_kasus = kode;
        final float nilai_kemiripan = min*100;

        String nilai = String.valueOf(nilai_kemiripan);

        System.out.println(kode_kasus+" & "+nilai_kemiripan);

        Intent intent = new Intent(Test.this, Hasil.class);
        Bundle extras = new Bundle();
        extras.putString("sakitMirip", kode_kasus);
        extras.putString("nilaiPersen", nilai);
        extras.putString("solusi", String.valueOf(kabehKode));
        intent.putExtras(extras);
        startActivity(intent);
        finish();

    }

}
