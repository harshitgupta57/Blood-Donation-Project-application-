package com.example.blooddonationapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.blooddonationapp.Model.users;
import com.example.blooddonationapp.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class useradapter extends RecyclerView.Adapter<useradapter.viewholder> {

    private Context context;
    private List<users> usersList;

    public useradapter(Context context, List<users> usersList) {
        this.context = context;
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(context).inflate(R.layout.user_displayed_layout,parent,false);
       return new viewholder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
     final users users = usersList.get(position);


     holder.profileadaptername.setText(users.getType());
     holder.profilebloodgroupadaptor.setText(users.getType());
     holder.profilephonenumberadapter.setText(users.getType());
     holder.profileemailidadapter.setText(users.getType());
     holder.profiletypeadapter.setText(users.getType());
     if (users.getType().equals("donor"))
     {
         holder.emaildonor.setVisibility(View.GONE);
     }

    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class viewholder extends RecyclerView.ViewHolder
    {
       public CircleImageView adapterimage;
       public TextView profiletypeadapter,profileadaptername,profileemailidadapter,profilephonenumberadapter,profilebloodgroupadaptor;
       public Button emaildonor;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            adapterimage = itemView.findViewById(R.id.adapterimage);
            profiletypeadapter = itemView.findViewById(R.id.profiletypeadapter);
            profileemailidadapter = itemView.findViewById(R.id.profileemailidadapter);
            profilephonenumberadapter= itemView.findViewById(R.id.profilephonenumberadapter);
            profilebloodgroupadaptor = itemView.findViewById(R.id.profilebloodgroupadaptor);
            emaildonor=itemView.findViewById(R.id.emaildonoradapter);
            profileadaptername=itemView.findViewById(R.id.profileadaptername);


        }
    }
}
