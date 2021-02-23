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

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText mEtLoginEmail;
    private TextInputEditText mEtLoginPassword;
    private AppCompatTextView mTvNewAccount;
    private Button mBtnLogin;
    private ProgressBar progress;

    private SharedPreferences sp;
    private SharedPreferences.Editor spEditor;
    private String token;

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sp = getSharedPreferences("Resturant", MODE_PRIVATE);
        spEditor = sp.edit();
        token = sp.getString("token", "-1");
        Toast.makeText(this, "" + token, Toast.LENGTH_SHORT).show();
        if (!token.equals("-1")) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
        find();
        onClick();
    }

    private void find() {
        mEtLoginEmail = findViewById(R.id.et_login_email);
        mEtLoginPassword = findViewById(R.id.et_login_password);
        mBtnLogin = findViewById(R.id.btn_login);
        mTvNewAccount = findViewById(R.id.tv_new_account);
        progress = findViewById(R.id.Login_progress);
    }


    private void onClick() {

        mTvNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegisterActivity.class));
            }
        });

        mBtnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
    }

    private void signIn() {
        String email = mEtLoginEmail.getText().toString();
        String pass = mEtLoginPassword.getText().toString();

        progress.setVisibility(View.VISIBLE);
        mBtnLogin.setEnabled(false);
        if (TextUtils.isEmpty(email)) {
            mEtLoginEmail.setError(getResources().getString(R.string.Please_Enter_Your_Email));
            return;
        }
        if (TextUtils.isEmpty(pass)) {
            mEtLoginPassword.setError(getResources().getString(R.string.Please_Enter_Your_Password));
            return;
        }
        DatabaseAccess db = DatabaseAccess.getInstance(this);
        db.open();
        if (db.getUserCount() != 0) {
            if (db.checkUser(email, pass)) {
                spEditor.putString("token", email);
                Toast.makeText(LoginActivity.this, R.string.Login_Successful, Toast.LENGTH_SHORT).show();
                spEditor.apply();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            } else {
                Toast.makeText(LoginActivity.this, R.string.Login_Error, Toast.LENGTH_SHORT).show();
                mEtLoginEmail.setText("");
                mEtLoginPassword.setText("");
            }
        } else {
            Toast.makeText(LoginActivity.this, R.string.Account_Is_Empty, Toast.LENGTH_SHORT).show();
        }
        db.close();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}