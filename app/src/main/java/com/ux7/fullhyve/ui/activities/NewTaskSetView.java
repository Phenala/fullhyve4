package com.ux7.fullhyve.ui.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.ux7.fullhyve.R;
import com.ux7.fullhyve.services.Handlers.AppHandler;
import com.ux7.fullhyve.ui.data.ListProject;
import com.ux7.fullhyve.ui.fragments.TaskSetFragment;

import java.util.Date;

public class NewTaskSetView extends AppCompatActivity {

    EditText name, description;
    DatePicker deadline;
    ListProject project;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task_set_view);

        buildActionBar();
        buildViews();
        buildProject();
    }

    public void buildViews() {

        name = findViewById(R.id.new_task_set_name);
        deadline = findViewById(R.id.new_task_set_deadline);
        description = findViewById(R.id.new_task_set_description);

    }

    public void buildProject() {

        project = (ListProject) getIntent().getSerializableExtra("project");

    }


    public void buildActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("New Task Set");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
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

    public void createTaskSet(View view) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                TaskSetFragment.get = true;
                finish();

            }
        };

        Date newDate = new Date(deadline.getYear(), deadline.getMonth(), deadline.getDayOfMonth());

        AppHandler.getInstance().projectHandler.newTaskSet(name.getText().toString(), newDate, description.getText().toString(), project.id,this, runnable);

    }

}
