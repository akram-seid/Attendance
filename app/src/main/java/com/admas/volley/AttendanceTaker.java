package com.admas.volley;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.admas.volley.Adapter.CustomAdapter;
import com.admas.volley.home.TeacherHome;
import com.admas.volley.misc.SharedPrefManager;
import com.admas.volley.models.Attendance;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class AttendanceTaker extends AppCompatActivity implements Present {
    public List<String> preList = new ArrayList<>();
    public List<String> perList = new ArrayList<>();
    public List<String> seslist = new ArrayList<>();
    Attendance attendance = new Attendance();
    ProgressDialog progressDialog;
    private final List<String> nameList = new ArrayList<>();
    private final List<String> idList = new ArrayList<>();
    private String course = "";
    private String section = "";
    private String instructor;
    private String currentDate;
    private RecyclerView recyclerView;
    private Button btn;
    private String res = "";
    private String reso = null;
    private String rest = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_taker);
        recyclerView = findViewById(R.id.studentlist);
        Intent intent = getIntent();
        course = intent.getStringExtra("course");
        section = intent.getStringExtra("section");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait!");

        currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        instructor = String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserId());

        name_fetcher(section);
        id_fetcher(section);
        progressDialog.show();


    }


    private void save_attendance(Attendance attendance) {


        res = StringUtils.join(attendance.getPreList(), ",");


        if (attendance.getPerList().size() != 0) {

            reso = StringUtils.join(attendance.getPerList(), ",");

        }


        rest = StringUtils.join(attendance.getSeslist(), ",");

        System.out.println(res);
        System.out.println(reso);
        System.out.println(rest);


        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .save_attendance(rest, res, reso);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {

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
                if (s != null) {
                    try {
                        JSONObject jSONObject = new JSONObject(s);
                        if (!jSONObject.getBoolean("error")) {

                            Toast.makeText(AttendanceTaker.this, jSONObject.getString("Message"), Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(AttendanceTaker.this, TeacherHome.class);

                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(AttendanceTaker.this, jSONObject.getString("Message"), Toast.LENGTH_SHORT).show();

                        }
                    } catch (JSONException jSONException) {
                        jSONException.printStackTrace();
                    }
                }
                progressDialog.dismiss();

            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AttendanceTaker.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    public void id_fetcher(String section) {


        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .fetch_id_stu(section);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull retrofit2.Response<ResponseBody> response) {

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
                if (s != null) {
                    try {
                        JSONObject jSONObject = new JSONObject(s);
                        JSONArray jArray = jSONObject.getJSONArray("idlist");
                        for (int j = 0; j < jArray.length(); j++) {
                            idList.add(jArray.getString(j));

                        }
                    } catch (JSONException jSONException) {
                        jSONException.printStackTrace();
                    }
                }


            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AttendanceTaker.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void name_fetcher(String section) {


        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .fetch_name_stu(section);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {

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
                if (s != null) {
                    try {
                        JSONObject jSONObject = new JSONObject(s);
                        JSONArray jArray = jSONObject.getJSONArray("namelist");

                        for (int j = 0; j < jArray.length(); j++) {
                            nameList.add(jArray.getString(j));
                            //System.out.println(jArray.getString(j));
                        }
                    } catch (JSONException jSONException) {
                        jSONException.printStackTrace();
                    }
                }
                show_students();
                progressDialog.dismiss();

            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AttendanceTaker.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void show_students() {
        CustomAdapter customAdapter = new CustomAdapter(nameList, idList, this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(customAdapter);
    }

    @Override
    public void Studentlist(List<String> presentlist) {
        System.out.println(presentlist);
        preList = presentlist;
    }

    @Override
    public void Permissionlist(List<String> permissionlist) {
        System.out.println(permissionlist + "got permission!");
        perList = permissionlist;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_save, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add: {
                seslist.clear();
                seslist.add(currentDate);
                seslist.add(course);
                seslist.add(instructor);
                seslist.add(section);

                if (preList.size() > 0) {
                    attendance.setPreList(preList);
                    attendance.setPerList(perList);
                    attendance.setSeslist(seslist);
                    save_attendance(attendance);
                } else {
                    Toast.makeText(this, "No Student is present, Please select present Students", Toast.LENGTH_SHORT).show();
                }


                return true;
            }


        }
        return super.onOptionsItemSelected(item);
    }


}