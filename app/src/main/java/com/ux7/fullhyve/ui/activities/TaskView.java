package com.ux7.fullhyve.ui.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ux7.fullhyve.R;
import com.ux7.fullhyve.ui.data.TaskDetail;
import com.ux7.fullhyve.ui.util.CircleTransform;
import com.ux7.fullhyve.ui.util.U;

public class TaskView extends AppCompatActivity {

    TaskDetail taskDetail;

    ImageView taskStatusPicture;
    TextView taskStatus;
    TextView taskAssignedOn;
    TextView taskDueDate;
    TextView taskInProgress;
    ImageView taskAssignedByPicture;
    TextView taskAssignedByName;
    ImageView taskAssignedToPicture;
    TextView taskAssignedToName;
    ImageView taskAssignedViaTeamPicture;
    TextView taskAssignedViaTeamName;
    TextView taskInstructions;
    TextView taskResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);

        buildViews();
        buildTask();
        buildActionBar();
    }


    public void buildTask() {

        taskDetail = (TaskDetail) getIntent().getSerializableExtra("task");

        taskStatus.setText(U.getTaskStatusString(taskDetail.status));
        taskStatusPicture.setImageResource(U.getTaskStatusIcon(taskDetail.status));

        taskAssignedOn.setText(taskDetail.assignedOn);
        taskDueDate.setText(taskDetail.deadline);
        taskInProgress.setText(taskDetail.inProgress);
        taskAssignedByName.setText(taskDetail.assignerName);
        taskAssignedToName.setText(taskDetail.assigneeName);
        taskAssignedViaTeamName.setText(taskDetail.teamName);

        Picasso.with(this).load(U.getImageUrl(taskDetail.assignerImage)).transform(new CircleTransform()).into(taskAssignedByPicture);
        Picasso.with(this).load(U.getImageUrl(taskDetail.assigneeImage)).transform(new CircleTransform()).into(taskAssignedToPicture);
        Picasso.with(this).load(U.getImageUrl(taskDetail.teamImage)).transform(new CircleTransform()).into(taskAssignedViaTeamPicture);

        taskInstructions.setText(taskDetail.instructions);
        taskResults.setText(taskDetail.results);


    }

    public void buildViews() {

        taskStatusPicture = (ImageView) findViewById(R.id.task_status_picture);
        taskStatus = (TextView) findViewById(R.id.task_status);
        taskAssignedOn = (TextView) findViewById(R.id.task_assigned_on);
        taskDueDate = (TextView) findViewById(R.id.task_due_date);
        taskInProgress = (TextView) findViewById(R.id.task_in_progress);
        taskAssignedByPicture = (ImageView) findViewById(R.id.task_assigned_by_picture);
        taskAssignedByName = (TextView) findViewById(R.id.task_assigned_by_name);
        taskAssignedToPicture = (ImageView) findViewById(R.id.task_assigned_to_picture);
        taskAssignedToName = (TextView) findViewById(R.id.task_assigned_to_name);
        taskAssignedViaTeamPicture = (ImageView) findViewById(R.id.task_assigned_via_team_picture);
        taskAssignedViaTeamName = (TextView) findViewById(R.id.task_assigned_via_team_name);
        taskInstructions = (TextView) findViewById(R.id.task_instructions);
        taskResults = (TextView) findViewById(R.id.task_results);


    }

    public void buildActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Task " + taskDetail.number);
        actionBar.setSubtitle("" + taskDetail.name);
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
}
