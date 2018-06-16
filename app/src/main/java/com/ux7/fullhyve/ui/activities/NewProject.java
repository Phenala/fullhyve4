package com.ux7.fullhyve.ui.activities;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.ux7.fullhyve.R;
import com.ux7.fullhyve.services.Handlers.AppHandler;

public class NewProject extends AppCompatActivity {

    EditText name, focus, description;

    String imageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_project);

        buildViews();
        buildActionBar();
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

    public void buildViews() {

        name = findViewById(R.id.new_project_name);
        focus = findViewById(R.id.new_project_focus);
        description = findViewById(R.id.new_project_description);

    }

    public void buildActionBar() {
        ActionBar actionBar = getSupportActionBar();
        setTitle("New Project");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }


    public void createProject(View view) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                finish();

            }
        };

        AppHandler.getInstance().projectHandler.newProject(name.getText().toString(), imageUrl, focus.getText().toString(), description.getText().toString(), this, runnable);

    }
}
