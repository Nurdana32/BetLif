package com.example.android.homedashboard;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;


public class Hasil extends AppCompatActivity
{
    private String sakit;
    private DatabaseReference data;
    private EditText hasilRingan;
    private TextView mirip;
    private String persen;
    private Button Home;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hasil);

        Bundle extras = getIntent().getExtras();
        sakit = extras.getString("sakitMirip");
        persen = extras.getString("nilaiPersen");


        DatabaseReference data = FirebaseDatabase.getInstance().getReference().child("DataBase").child("TabelKasus").child(sakit);
        data.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               String hasilDiagnosis = dataSnapshot.child("Hasil").getValue().toString();
               hasilRingan = (EditText)findViewById(R.id.Hasil);
               hasilRingan.setText(" " +hasilDiagnosis);
               mirip =(TextView)findViewById(R.id.Mirip);
               mirip.setText(" Nilai Kemiripan " +persen+"%");
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });



    }

    public void OpenHome(View view) {
        Intent i;

        switch (view.getId()){
            case R.id.BackHome : i = new Intent(this, MainActivity.class);
            startActivity(i);
            default:break;
        }
    }
}
