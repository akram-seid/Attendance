package com.admas.volley.login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.admas.volley.R;
import com.admas.volley.RetrofitClient;
import com.admas.volley.home.StudentHome;
import com.admas.volley.home.StudentHome;
import com.admas.volley.misc.Constants;
import com.admas.volley.misc.MySingleton;
import com.admas.volley.misc.SharedPrefManager;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class StudentLogin extends AppCompatActivity {

    private Button login_btn;

    private EditText passtxt;

    private ProgressDialog progressDialog;

    private EditText usertxt;

    public void stu_login() {
        final String username = this.usertxt.getText().toString().trim();
        final String password = this.passtxt.getText().toString().trim();
        progressDialog.show();

        Call<ResponseBody> call = RetrofitClient
                .getInstance()
                .getApi()
                .student_login(username, password,"students");
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
                        JSONObject jsonObject = new JSONObject(s);
                        if (!jsonObject.getBoolean("error")) {
                            int i = jsonObject.getInt("id");
                            SharedPrefManager.getInstance(getApplicationContext()).UserLogin(i);
                            SharedPrefManager.loggedin("Student");
                            Toast.makeText(StudentLogin.this, jsonObject.getString("Message"), Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(StudentLogin.this, StudentHome.class);

                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(StudentLogin.this, jsonObject.getString("Message"), Toast.LENGTH_SHORT).show();

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(StudentLogin.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);
        login_btn =findViewById(R.id.login_btn);
        usertxt = findViewById(R.id.usern_field);
        passtxt = findViewById(R.id.passw_field);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait ...");
        login_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
                stu_login();
            }
        });
    }
}