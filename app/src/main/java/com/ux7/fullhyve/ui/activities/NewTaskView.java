package com.ux7.fullhyve.ui.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;

import com.ux7.fullhyve.R;
import com.ux7.fullhyve.services.Handlers.AppHandler;
import com.ux7.fullhyve.services.Models.Task;
import com.ux7.fullhyve.services.Storage.AppData;
import com.ux7.fullhyve.ui.data.ListTaskSet;
import com.ux7.fullhyve.ui.data.TaskSetDetail;

public class NewTaskView extends AppCompatActivity {

    EditText name, description;
    MultiAutoCompleteTextView team, assignee;

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
        team = findViewById(R.id.new_task_proxy_team);
        assignee = findViewById(R.id.new_task_assignee);

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

    public void createTask(View view) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                finish();
            }
        };

        Task task = new Task();
        task.title = name.getText().toString();
        task.assigner = AppData.getCache().getIdentity();
        task.assignee = null;
        task.description = description.getText().toString();
        task.id = -1;
        task.proxyTeam = null;
        task.number = 0;
        task.status = 0;

        AppHandler.getInstance().projectHandler.newTask(task, taskSetDetail.id, this, runnable);

    }
}
