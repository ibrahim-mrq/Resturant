package com.android.resturant.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.resturant.DB.DatabaseAccess;
import com.android.resturant.R;

public class ProfileActivity extends AppCompatActivity {

    private Toolbar toolbarProfile;
    private TextView profileTvName;
    private TextView profileTvEmail;
    private TextView profileTvPassword;
    private String token;
    private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        sp = getSharedPreferences("Resturant", Context.MODE_PRIVATE);
        token = sp.getString("token", "-1");

        initView();
        getData();
        setSupportActionBar(toolbarProfile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initView() {
        toolbarProfile = findViewById(R.id.toolbar_profile);
        profileTvName = findViewById(R.id.profile_tv_name);
        profileTvEmail = findViewById(R.id.profile_tv_email);
        profileTvPassword = findViewById(R.id.profile_tv_password);
    }

    private void getData() {
        DatabaseAccess db = DatabaseAccess.getInstance(ProfileActivity.this);
        db.open();
        profileTvName.setText(db.searchUser(token).get(0).getName());
        profileTvEmail.setText(db.searchUser(token).get(0).getEmail());
        profileTvPassword.setText(db.searchUser(token).get(0).getPassword());
        db.close();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}