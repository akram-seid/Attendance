package com.admas.volley.home;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;

import android.widget.Toast;

import com.admas.volley.Adapter.StudentAdapter;
import com.admas.volley.R;
import com.admas.volley.RetrofitClient;
import com.admas.volley.misc.SharedPrefManager;
import com.admas.volley.welcome.UserHome;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class StudentHome extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;
    RecyclerView recyclerView;
    List<String> courseList;
    List<String> countList;
    List<String> percentList;
    String student="";
    ProgressDialog progressDialog;
    StudentAdapter studentAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        recyclerView= findViewById(R.id.attended_list);

        courseList=new ArrayList<>();
        countList = new ArrayList<>();
        percentList = new ArrayList<>();
        progressDialog= new ProgressDialog(this);
        progressDialog.setMessage("please wait");

        fetch_attendance();


    }

    private void fetch_attendance() {
        student= String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserId());

        progressDialog.show();

        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .fetch_attendance(student);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                progressDialog.dismiss();
                String s = null;
                try {
                    if (response.code() == 201) {
                        s = response.body().string();

                    } else {
                        s = response.errorBody().string();

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (s!=null){
                    try {
                        JSONObject jSONObject = new JSONObject(s);
                        JSONArray jArray= jSONObject.getJSONArray("courselist");
                        JSONArray jArray1= jSONObject.getJSONArray("percentage");
                        for (int j = 0; j < jArray.length(); j++) {
                            courseList.add(jArray.getString(j));
                            countList.add(jArray1.getString(j));
                            String per = jArray1.getString(j);
                            String [] nums=per.split("/");
                            Double x = Double.parseDouble(nums[0]);
                            Double y = Double.parseDouble(nums[1]);
                            Double z = ((x / y) * 100.0);
                            percentList.add(String.format("%.1f", z) +"%");
                        }
                    } catch (JSONException jSONException) {
                        jSONException.printStackTrace();
                    }
                }
                setlist();
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(StudentHome.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }
    public void setlist(){

        studentAdapter = new StudentAdapter(courseList, countList,percentList);
        recyclerView.setAdapter(studentAdapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

    }

    public void logouter() {
        final AlertDialog alertDialog = (new AlertDialog.Builder(this)).create();
        alertDialog.setTitle("Logout");
        alertDialog.setMessage("Are you sure you want to logout?");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface param1DialogInterface, int param1Int) {
                SharedPrefManager.getInstance(getApplicationContext()).Logout();
                startActivity(new Intent(StudentHome.this, UserHome.class));
                finish();
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface param1DialogInterface, int param1Int) {
                alertDialog.dismiss();
            }
        });
        alertDialog.show();
    }

    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
        (new Handler(Looper.getMainLooper())).postDelayed(new Runnable() {
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        },  2000L);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
        int i = paramMenuItem.getItemId();
        if (i != R.id.logoutbtn) {
            if (i != R.id.pass_cng) {
                if (i == R.id.refresh)
                    Toast.makeText(this, "Refreshing...", Toast.LENGTH_SHORT).show();
                setlist();
            } else {
                Toast.makeText(this, "Change password", Toast.LENGTH_SHORT).show();
            }
        } else {
            logouter();
        }
        return true;
    }

}