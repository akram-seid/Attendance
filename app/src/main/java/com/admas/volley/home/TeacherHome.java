package com.admas.volley.home;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.admas.volley.R;
import com.admas.volley.RetrofitClient;
import com.admas.volley.Selector_activity_taker;
import com.admas.volley.Selector_activity_viewer;
import com.admas.volley.misc.SharedPrefManager;
import com.admas.volley.welcome.UserHome;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

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
                    Toast.makeText(this, "refresh button", Toast.LENGTH_SHORT).show();
            } else {
                pass_changer();
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
    private void pass_changer() {
        String user_id = String.valueOf(SharedPrefManager.getInstance(getApplicationContext()).getUserId());
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        final View customLayout = getLayoutInflater().inflate(R.layout.alert_label_editor, null);
        alertDialog.setView(customLayout);
        EditText old_passT = customLayout.findViewById(R.id.old_field);
        EditText new_passT = customLayout.findViewById(R.id.new_field);
        EditText con_passT = customLayout.findViewById(R.id.confirm_field);
        Button save = customLayout.findViewById(R.id.changebtn);
        Button cancel = customLayout.findViewById(R.id.cancelbtn);
        AlertDialog alert = alertDialog.create();

        alert.show();
        save.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                String old_pass = old_passT.getText().toString().trim();
                String new_pass = new_passT.getText().toString().trim();
                String con_pass = con_passT.getText().toString().trim();

                if (old_pass.length() < 6 || old_pass.length() > 16) {
                    old_passT.setError("Password must between 6 and 16 characters");
                } else if (new_pass.length() < 6 || new_pass.length() > 16) {
                    new_passT.setError("Password must between 6 and 16 characters");
                } else if (con_pass.length() < 6 || con_pass.length() > 16) {
                    new_passT.setError("Password must between 6 and 16 characters");
                } else if (!con_pass.equals(new_pass)) {
                    con_passT.setError("Password Mismatch!");
                } else {
                    Call<ResponseBody> call = RetrofitClient
                            .getInstance()
                            .getApi()
                            .change_pass(user_id, "students", old_pass, con_pass);
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
                                    if (!jSONObject.getBoolean("error")) {

                                        Snackbar.make(findViewById(R.id.myCoordinatorLayout),jSONObject.getString("Message") ,
                                                Snackbar.LENGTH_SHORT)
                                                .show();
                                        alert.dismiss();

                                    } else {
                                        Toast.makeText(TeacherHome.this, jSONObject.getString("Message"), Toast.LENGTH_SHORT).show();

                                    }
                                } catch (JSONException jSONException) {
                                    jSONException.printStackTrace();
                                }
                            }
                        }


                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(TeacherHome.this, t.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alert.dismiss();
            }
        });






    }
    
}