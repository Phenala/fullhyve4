package com.ux7.fullhyve.ui.activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ux7.fullhyve.R;
import com.ux7.fullhyve.services.Handlers.AppHandler;
import com.ux7.fullhyve.services.Storage.AppData;

public class RegisterView extends AppCompatActivity {

    EditText username, password, firstname, lastname, confirmPassword;

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

        username = findViewById(R.id.register_username);
        password = findViewById(R.id.register_password);
        firstname = findViewById(R.id.register_first_name);
        lastname = findViewById(R.id.register_last_name);
        confirmPassword = findViewById(R.id.register_confirm_password);

    }

    public void buildActionBar() {

        setTitle("Register");

    }

    public void register() {

        String usernameString = username.getText().toString();
        String passwordString = password.getText().toString();
        String firstName = firstname.getText().toString();
        String lastName = lastname.getText().toString();

        if (!password.getText().toString().matches(confirmPassword.getText().toString())) {

            AlertDialog.Builder amb = new AlertDialog.Builder(this);
            amb.setMessage("Password not confirmed correctly.").setCancelable(true).show();
            return;

        }

        if (password.getText().toString().length() < 4) {

            AlertDialog.Builder amb = new AlertDialog.Builder(this);
            amb.setMessage("Password length should be greater than 3").setCancelable(true).show();
            return;

        }

        if (username.getText().toString().length() < 6) {

            AlertDialog.Builder amb = new AlertDialog.Builder(this);
            amb.setMessage("Username length should be greater than 5").setCancelable(true).show();
            return;

        }

        if (password.getText().toString().length() > 30) {

            AlertDialog.Builder amb = new AlertDialog.Builder(this);
            amb.setMessage("Password length should be less than 30").setCancelable(true).show();
            return;

        }

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Registering ...");
        pd.show();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                pd.dismiss();
                redirectToLogin();

            }
        };

        Activity activity = this;

        Runnable noUserrname = new Runnable() {
            @Override
            public void run() {
                pd.dismiss();
                AlertDialog.Builder amb = new AlertDialog.Builder(activity);
                amb.setMessage("Username is already taken.").setCancelable(true).show();
            }
        };

        AppHandler.getInstance().loginHandler.signup(firstName, lastName, "", usernameString, passwordString, this, runnable, noUserrname);

        //handleRegisterLogic

    }

    public void redirectToLogin() {

        Toast.makeText(this, "You have successfully been registered.", Toast.LENGTH_LONG).show();
        finish();

    }
}
