package com.admas.volley.welcome;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.admas.volley.R;
import com.admas.volley.login.StudentLogin;
import com.admas.volley.login.TeacherLogin;
import com.admas.volley.misc.SharedPrefManager;
import com.admas.volley.home.StudentHome;
import com.admas.volley.home.TeacherHome;

import java.util.Objects;

public class UserHome extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;

    LinearLayout studentBtn;

    LinearLayout teacherBtn;
    static SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);
        studentBtn =findViewById(R.id.student);
        teacherBtn = findViewById(R.id.teacher);
        SharedPrefManager.getInstance(getApplicationContext());
        if (Objects.equals(SharedPrefManager.whoLoggedIn(), "Student")) {
            startActivity(new Intent(getApplicationContext(), StudentHome.class));
            finish();
            return;
        }
        if (Objects.equals(SharedPrefManager.whoLoggedIn(), "Teacher")) {
            startActivity(new Intent(getApplicationContext(), TeacherHome.class));
            finish();
            return;
        }
        teacherBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
                startActivity(new Intent((Context)UserHome.this, TeacherLogin.class));
                finish();
            }
        });
        studentBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
                startActivity(new Intent((Context)UserHome.this, StudentLogin.class));
                finish();
            }
        });
        sharedPreferences =getSharedPreferences("server_ip",MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString("192.168.43.81",null);

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
}