package com.admas.volley;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.admas.volley.misc.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class Selector_activity_taker extends AppCompatActivity {

    private final List<String> nameList = new ArrayList<>();
    private final List<String> idList = new ArrayList<>();
    ProgressDialog progressDialog;
    // Lists used for initializing array adapter
    private List<String> sec_list;
    private List<String> cou_list;
    //String variables to store user selective
    private String current_course;
    private String current_section;
    private String instructor;
    //Drop down menu declaration
    private AutoCompleteTextView course_spinner;
    private AutoCompleteTextView section_spinner;
    //Adapters
    private ArrayAdapter<String> adapter_course;
    private ArrayAdapter<String> adapter_section;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selector);

        //Views Declaration
        course_spinner = findViewById(R.id.course_spinner);
        section_spinner = findViewById(R.id.section_spinner);
        //submit button for the chosen Items
        Button submit = findViewById(R.id.submit_btn);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait!");

        course_fetcher();


        course_spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                current_course = adapter_course.getItem(i);
                section_fetcher(current_course);

                adapter_section = new ArrayAdapter<>(Selector_activity_taker.this, R.layout.dropdown_item, sec_list);
                adapter_section.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                section_spinner.setAdapter(adapter_section);

            }
        });
        section_spinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                current_section = adapter_section.getItem(i);
            }


        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Selector_activity_taker.this, AttendanceTaker.class);
                intent.putExtra("course", current_course);
                intent.putExtra("section", current_section);
                startActivity(intent);
                finish();


            }
        });


    }

    private void section_fetcher(String current_course) {
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .fetch_course_ins(current_course);
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
                        JSONArray jArray = jSONObject.getJSONArray("sectionlist");
                        for (int j = 0; j < jArray.length(); j++) {
                            sec_list.add(jArray.getString(j));
                            System.out.println(jArray.getString(j));
                        }
                    } catch (JSONException jSONException) {
                        jSONException.printStackTrace();
                    }
                }

            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Selector_activity_taker.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }


    public void course_fetcher() {
        instructor = String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserId());
        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .fetch_course_ins(instructor);
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
                        JSONArray jArray = jSONObject.getJSONArray("courselist");
                        for (int j = 0; j < jArray.length(); j++) {
                            cou_list.add(jArray.getString(j));
                            System.out.println(jArray.getString(j));
                        }
                    } catch (JSONException jSONException) {
                        jSONException.printStackTrace();
                    }
                }
                show_list();
                progressDialog.dismiss();
            }


            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(Selector_activity_taker.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    public void show_list() {
        adapter_course = new ArrayAdapter<>(Selector_activity_taker.this, R.layout.dropdown_item, cou_list);
        adapter_course.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        course_spinner.setAdapter(adapter_course);
    }


}
