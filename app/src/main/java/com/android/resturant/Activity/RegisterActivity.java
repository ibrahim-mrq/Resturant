package com.android.resturant.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.resturant.DB.DatabaseAccess;
import com.android.resturant.Model.User;
import com.android.resturant.R;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText mEtRegisterName, mEtRegisterEmail,
            mEtRegisterPassword;
    private AppCompatTextView mTvHaveAccount;
    private ProgressBar reg_progress;
    private Button mBtnRegister;

    private SharedPreferences sp;
    private SharedPreferences.Editor spEditor;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        sp = getSharedPreferences("Resturant", MODE_PRIVATE);
        spEditor = sp.edit();

        find();
        onClick();

        reg_progress.setVisibility(View.GONE);
    }

    private void find() {
        mEtRegisterName = findViewById(R.id.et_register_FirstName);
        reg_progress = findViewById(R.id.reg_progress);
        mEtRegisterEmail = findViewById(R.id.et_register_email);
        mEtRegisterPassword = findViewById(R.id.et_register_password);
        mBtnRegister = findViewById(R.id.btn_register);
        mTvHaveAccount = findViewById(R.id.tv_have_account);
    }

    private void onClick() {

        mTvHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });
    }

    private void signup() {
        final String email = mEtRegisterEmail.getText().toString();
        final String pass = mEtRegisterPassword.getText().toString();
        final String firstName = mEtRegisterName.getText().toString();

        reg_progress.setVisibility(View.VISIBLE);
        mBtnRegister.setEnabled(false);

        if (TextUtils.isEmpty(firstName)) {
            mEtRegisterName.setError(getResources().getString(R.string.Please_Enter_Your_Name));
            return;
        }
        if (TextUtils.isEmpty(email)) {
            mEtRegisterEmail.setError(getResources().getString(R.string.Please_Enter_Your_Email));
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            mEtRegisterPassword.setError(getResources().getString(R.string.Please_Enter_Your_Password));
            return;
        }
        if (pass.length() < 6) {
            mEtRegisterPassword.setError(getResources().getString(R.string.Password_is_short));
        }
        DatabaseAccess db = DatabaseAccess.getInstance(this);
        db.open();
        db.insertUser(new User(firstName, email, pass));
        Toast.makeText(RegisterActivity.this, getResources().getString(R.string.Register_Successful), Toast.LENGTH_SHORT).show();
        spEditor.putString("token", email);
        spEditor.apply();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();
        db.close();
    }

}