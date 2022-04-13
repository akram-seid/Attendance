package com.admas.volley.Adapter;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.admas.volley.Present;
import com.admas.volley.R;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {

    List<String> listname;
    List<String> listid;
    List<String> presentlist = new ArrayList<>();
    Present present;
    List<String> permislist = new ArrayList<>();


    public CustomAdapter(List<String> listname, List<String> listid, Present present) {
        this.listname = listname;
        this.listid = listid;
        this.present = present;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.taker_row2, viewGroup, false);


        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        holder.name.setText(listname.get(position));
        holder.id.setText(listid.get(position));

        if (listid != null && listid.size() > 0) {
            holder.status_box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (holder.status_box.isChecked()) {
                        presentlist.add(listid.get(position));

                    } else {
                        presentlist.remove(listid.get(position));
                    }
                    present.Studentlist(presentlist);
                }
            });
            holder.permis.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (holder.permis.isChecked()) {
                        permislist.add(listid.get(position));

                    } else {
                        permislist.remove(listid.get(position));
                    }
                    present.Permissionlist(permislist);
                }
            });

        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return listname.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView id;
        public RadioButton permis;
        public RadioButton status_box;

        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            name = itemView.findViewById(R.id.name_view);
            id = itemView.findViewById(R.id.id_view);
            permis = itemView.findViewById(R.id.perm_btn);
            status_box = itemView.findViewById(R.id.status);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    System.out.println("Item clicked" + getAdapterPosition());


                }
            });

        }


    }

}
