package com.example.bme3890projectapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText et_username;
    public static final String NAME_EXTRA = "com.example.bme3890projectapp.EXTRA.NAME";
    public static final String EXTRA_USER_NAME = "com.example.rememberme.EXTRA_USER_NAME";
    private EditText et_password;
    public String name;
    private android.widget.Button login;
    private android.widget.Button signUp;
    private TextView loginError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        login = (Button) findViewById(R.id.button_login);
        signUp = (Button) findViewById(R.id.button_signUp);
        loginError = (TextView) findViewById(R.id.tv_loginError);



    }

    public void onClick(View view) {
        String username = et_username.getText().toString();
        String password = et_password.getText().toString();

        SharedPreferences loginInfo = getSharedPreferences("logins",
                Context.MODE_PRIVATE);
        SharedPreferences.Editor loginEditor = loginInfo.edit();


        //check if login already exists  ----------
        //name is the key and password is the value
        //TODO: get login name

        if (!loginInfo.contains(username)) {
            Toast.makeText(getApplicationContext(),"Username not on file. Please sign up to make a new account!",Toast.LENGTH_LONG).show();
        }
        else {
            //TODO: load value from preferences (set default return value)
            String nullReturnValue = "";
            String lastLogin = loginInfo.getString(username, nullReturnValue);

            //if exists, respond with last log-in information and update with new login info
            //if doesn't exist, create new log-in
            if (lastLogin == nullReturnValue || !password.equals(loginInfo.getString(username, ""))) {
                //set welcome message
                Toast.makeText(getApplicationContext(), "Wrong password, please go back and try logging in again!", Toast.LENGTH_LONG).show();

            } else {
                //set welcome message
                Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();
                // loginEditor.putString(loginName,passcode);
                // loginEditor.apply();
                Intent loginToApp = new Intent(MainActivity.this, SecondActivity.class);

                loginToApp.putExtra(NAME_EXTRA, username);
                startActivity(loginToApp);

            }
        }
    }


    public void forgotPassword(View view){

        Intent intent = new Intent(MainActivity.this, ForgotPassword.class);
        startActivity(intent);
    }

    public void signUp(View view) {

        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);

    }
}
