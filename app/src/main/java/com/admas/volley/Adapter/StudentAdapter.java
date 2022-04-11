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

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    List<String> courseList;
    List<String> percentageList;




    public StudentAdapter(List<String> courseList, List<String> percentageList) {
        this.courseList = courseList;
        this.percentageList= percentageList;

    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new view, which defines the UI of the list item
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.student_row_header, viewGroup, false);


        return new ViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.course_name.setText(courseList.get(position));
            holder.percentage.setText(percentageList.get(position));


    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView course_name;
        public TextView percentage;


        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            course_name = itemView.findViewById(R.id.course_name);
            percentage = itemView.findViewById(R.id.percentage);


        }


    }

}
