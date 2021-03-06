package com.ux7.fullhyve.ui.activities;

import android.app.Application;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import android.support.test.espresso.idling.CountingIdlingResource;

import com.ux7.fullhyve.R;
import com.ux7.fullhyve.services.Handlers.AppHandler;

public class LoginView extends AppCompatActivity {

    String username;
    String password;

    public static boolean changedUser = false;

    public static boolean loggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_view);

        buildViews();
    }

    public void getCredentials() {

        username = ((EditText)findViewById(R.id.username)).getText().toString();
        password = ((EditText)findViewById(R.id.password)).getText().toString();

    }

    public void buildViews() {

        ((TextView)findViewById(R.id.login_sign_up)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                goToRegisterView();

            }
        });

    }

    @Override
    public void onBackPressed() {

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }


    public void goToHomeView() {

        Intent intent = new Intent(this, HomeView.class);
        startActivity(intent);

    }

    public void goToRegisterView() {

        Intent intent = new Intent(this, RegisterView.class);
        startActivity(intent);

    }

    public void loginFailNotify() {

        Toast.makeText(this, "Username or Password is incorrect.", Toast.LENGTH_LONG).show();

    }


    public void login(View view) {

        getCredentials();

        LoginRunnable runnable = new LoginRunnable();


        AppHandler.getInstance().loginHandler.signin(username, password, this, runnable);
    }

    public class LoginRunnable implements Runnable {

        public boolean loginSuccess;

        @Override
        public void run() {

            if (loginSuccess)
                goToHomeView();
            else
                loginFailNotify();


        }
    }

}
