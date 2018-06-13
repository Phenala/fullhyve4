package com.ux7.fullhyve.ui.activities;

import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.ux7.fullhyve.R;

public class RegisterView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_view);

        buildViews();
        buildActionBar();
    }

    public void buildViews() {

        ((Button)findViewById(R.id.register_button)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                register();

            }
        });

    }

    public void buildActionBar() {

        setTitle("Register");

    }

    public void register() {

        redirectToLogin();


        //handleRegisterLogic

    }

    public void redirectToLogin() {

        Toast.makeText(this, "You have successfully been registered.", Toast.LENGTH_LONG).show();
        finish();

    }
}
