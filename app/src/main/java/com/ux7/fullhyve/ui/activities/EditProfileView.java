package com.ux7.fullhyve.ui.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;

import com.ux7.fullhyve.R;
import com.ux7.fullhyve.services.Handlers.AppHandler;
import com.ux7.fullhyve.services.Models.Identity;
import com.ux7.fullhyve.services.Storage.AppData;

public class EditProfileView extends AppCompatActivity {

    EditText firstName, lastName, title, bio;
    MultiAutoCompleteTextView skills;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_view);

        buildActionBar();
        getIdentity();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {

            case android.R.id.home:
                finish();
                return false;

        }

        return super.onOptionsItemSelected(item);
    }


    public void buildActionBar() {

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Edit Profile");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

    }

    public void updateProfile(View view) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                finish();

            }
        };

        Identity identity = AppData.getCache().getIdentity();

        identity.setFirstName(firstName.getText().toString());
        identity.setLastName(lastName.getText().toString());
        identity.setTitle(title.getText().toString());
        identity.setDescription(bio.getText().toString());
        identity.setSkills(skills.getText().toString().split(", "));


        AppHandler.getInstance().loginHandler.editProfile(identity, this, runnable);

    }

    public void getIdentity() {

        Identity identity = AppData.getCache().getIdentity();

        firstName = findViewById(R.id.edit_profile_first_name);
        lastName = findViewById(R.id.edit_profile_last_name);
        title = findViewById(R.id.edit_profile_title);
        bio = findViewById(R.id.edit_profile_bio);
        skills = findViewById(R.id.edit_profile_skills);

        firstName.setText(identity.getFirstName());
        lastName.setText(identity.getLastName());
        title.setText(identity.getTitle());
        bio.setText(identity.getDescription());
        String skillString = "";
        for (int i = 0; i < identity.getSkills().length - 1; i++) {
            skillString += ", " + identity.getSkills()[i];
        }
        if (skillString.length() > 2) {
            skillString = skillString.substring(2);
        }
        skills.setText(skillString);

    }

}
