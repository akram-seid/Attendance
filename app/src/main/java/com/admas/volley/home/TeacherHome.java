package com.admas.volley.home;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.admas.volley.R;
import com.admas.volley.Selector_activity_taker;
import com.admas.volley.Selector_activity_viewer;
import com.admas.volley.misc.SharedPrefManager;
import com.admas.volley.welcome.UserHome;

public class TeacherHome extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;

    LinearLayout taker;

    LinearLayout viewer;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_home);
        taker = findViewById(R.id.ticker);
        viewer = findViewById(R.id.viewer);
        taker.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
                startActivity(new Intent(TeacherHome.this, Selector_activity_taker.class));

            }
        });
        viewer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View param1View) {
                startActivity(new Intent(TeacherHome.this, Selector_activity_viewer.class));

            }
        });
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
                    Toast.makeText(this, "seetings button", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Change password", Toast.LENGTH_SHORT).show();
            }
        } else {
            logouter();
        }
        return true;
    }
    public void logouter() {
        final AlertDialog alertDialog = (new AlertDialog.Builder(this)).create();
        alertDialog.setTitle("Logout");
        alertDialog.setMessage("Are you sure you want to logout?");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface param1DialogInterface, int param1Int) {
                SharedPrefManager.getInstance(getApplicationContext()).Logout();
                startActivity(new Intent(TeacherHome.this, UserHome.class));
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
    
}