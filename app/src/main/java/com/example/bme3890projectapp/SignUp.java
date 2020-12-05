package com.example.bme3890projectapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {

    private EditText et_newPassword;
    private EditText et_newUsername;
    private android.widget.Button create;
    public static final String NAME_EXTRA = "com.example.bme3890projectapp.EXTRA.NAME";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        et_newUsername = (EditText) findViewById(R.id.et_newUsername);
        et_newPassword = (EditText) findViewById(R.id.et_newPassword);
        create = (Button) findViewById(R.id.btn_createAccount);


    }

    public void createAccount(View view) {

        SharedPreferences loginInfo = getSharedPreferences("logins", Context.MODE_PRIVATE);;
        SharedPreferences.Editor loginEditor2 = loginInfo.edit();

        String newUsername = et_newUsername.getText().toString();
        String newPassword = et_newPassword.getText().toString();

        if (loginInfo.contains(newUsername)) {
            Toast.makeText(getApplicationContext(),"Username already on file. Please pick a different username",Toast.LENGTH_LONG).show();
        }
        else {
            loginEditor2.putString(newUsername, newPassword);
            loginEditor2.apply();
            Toast.makeText(getApplicationContext(),"Account created!",Toast.LENGTH_LONG).show();
            Intent loginToApp = new Intent (SignUp.this, SecondActivity.class);
            loginToApp.putExtra(NAME_EXTRA, newUsername);
            startActivity(loginToApp);

        }



    }
}