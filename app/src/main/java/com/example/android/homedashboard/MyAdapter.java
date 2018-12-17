package com.example.android.homedashboard;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Profile> profiles;
    ArrayList<Profile> gejalanya = new ArrayList<>();

    public MyAdapter(Context c, ArrayList<Profile> p){
        context = c;
        profiles = p;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.recicle_test,parent, false));
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.gejala.setText(profiles.get(position).getGejala());

        holder.gejala.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isChecked()){
                    gejalanya.add(profiles.get(position));
                }
                else {
                    gejalanya.remove(profiles.get(position));
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return profiles.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        CheckBox gejala;
        LinearLayout linearLayout;
        public MyViewHolder(View itemView){
            super(itemView);
            gejala = (CheckBox) itemView.findViewById(R.id.gejala);
        }
    }

}
