package com.admas.volley.Adapter;


import android.annotation.SuppressLint;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.admas.volley.R;


import java.util.List;
import java.util.Random;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.ViewHolder> {

    List<String> courseList;
    List<String> countList;
    List<String> percentList;




    public StudentAdapter(List<String> courseList, List<String> countList, List<String> percentList) {
        this.courseList = courseList;
        this.countList = countList;
        this.percentList = percentList;

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
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {

            holder.percent.setText(percentList.get(position));
        holder.course_name.setText(courseList.get(position));
        holder.count.setText(countList.get(position));
        if(position%2 == 1) {
            holder.layout.setBackgroundResource(R.drawable.gradient);
        }
        else{
            holder.layout.setBackgroundResource(R.drawable.gradient2);
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return courseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView course_name;
        public TextView count;
        public TextView percent;
        public LinearLayout layout;


        public ViewHolder(View view) {
            super(view);
            // Define click listener for the ViewHolder's View

            course_name = itemView.findViewById(R.id.course_name);
            count = itemView.findViewById(R.id.count);
            percent= itemView.findViewById(R.id.percentage);
            layout = itemView.findViewById(R.id.student_row_layout);




        }


    }

}
