package com.ux7.fullhyve.ui.activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;

import com.ux7.fullhyve.R;
import com.ux7.fullhyve.services.Handlers.AppHandler;
import com.ux7.fullhyve.services.Models.Task;
import com.ux7.fullhyve.services.Storage.AppData;
import com.ux7.fullhyve.ui.data.ListTaskSet;
import com.ux7.fullhyve.ui.data.TaskSetDetail;

import java.util.Date;

public class NewTaskView extends AppCompatActivity {

    EditText name, description;
    DatePicker deadline;
    int team, assignee;


    TaskSetDetail taskSetDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task_view);

        buildTaskSet();
        buildActionBar();
        buildViews();

    }

    public void buildActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("New Task Set");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    public void buildViews() {

        name = findViewById(R.id.new_task_name);
        description = findViewById(R.id.new_task_description);

    }

    public void buildTaskSet() {

        taskSetDetail = (TaskSetDetail) getIntent().getSerializableExtra("taskSetDetail");

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

    public void onActivityResult(int requestCode, int resultCode, Intent data)  {

        if (resultCode == RESULT_OK && requestCode == 2) {

            team = data.getIntExtra("teamId", 0);

        } else if (resultCode == RESULT_OK && requestCode == 2) {

            assignee = data.getIntExtra("assigneeId", 0);

        }

    }

    public void selectTeam(View view) {

        Intent intent = new Intent(this, AddMemberView.class);
        startActivityForResult(intent, 3);
    }

    public void selectAssignee(View view) {

        Intent intent = new Intent(this, AddMemberView.class);
        startActivityForResult(intent, 2);

    }

    public void createTask(View view) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                finish();
            }
        };

        long deadlineTIme = (new Date(deadline.getYear(), deadline.getMonth(), deadline.getDayOfMonth())).getTime();

        AppHandler.getInstance().projectHandler
                .newTask(name.getText().toString(),
                        description.getText().toString(),
                        deadlineTIme,
                        AppData.getCache().getIdentity().getId(),
                        assignee,
                        team,
                        taskSetDetail.id,
                        this, runnable);

    }
}
