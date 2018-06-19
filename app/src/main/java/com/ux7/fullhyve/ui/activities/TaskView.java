package com.ux7.fullhyve.ui.activities;

import android.app.Activity;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.ux7.fullhyve.R;
import com.ux7.fullhyve.services.Handlers.AppHandler;
import com.ux7.fullhyve.services.Storage.AppData;
import com.ux7.fullhyve.ui.data.ListProject;
import com.ux7.fullhyve.ui.data.ListTask;
import com.ux7.fullhyve.ui.data.ListTaskSet;
import com.ux7.fullhyve.ui.data.TaskDetail;
import com.ux7.fullhyve.ui.data.TaskSetDetail;
import com.ux7.fullhyve.ui.enums.TaskStatus;
import com.ux7.fullhyve.ui.util.CircleTransform;
import com.ux7.fullhyve.ui.util.U;

import java.util.ArrayList;
import java.util.List;

public class TaskView extends AppCompatActivity {

    TaskDetail taskDetail;
    TaskSetDetail taskSet;
    ListProject project;

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
    Button taskStateChange;
    Button taskRevise;
    TableRow taskTeamRow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_view);

        buildViews();
        buildTask();
        buildViewSet();
        buildActionBar();
    }


    public void buildTask() {
        taskDetail = (TaskDetail) getIntent().getSerializableExtra("task");
        project = (ListProject) getIntent().getSerializableExtra("project");
        taskSet = (TaskSetDetail) getIntent().getSerializableExtra("taskset");
    }

    public void buildViewSet() {

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

        Log.e("Team Id 0", taskDetail.teamId + "");

        if (taskDetail.teamId == -1) {
            taskTeamRow.setVisibility(View.GONE);
        }

        taskInstructions.setText(taskDetail.instructions);
        taskStateChange.setText(U.getButtonText(taskDetail.status));
        if (taskDetail.status == TaskStatus.PENDINGEVALUATION) {
            taskRevise.setVisibility(View.VISIBLE);
        } else {
            taskRevise.setVisibility(View.GONE);
        }

        if (taskDetail.status == TaskStatus.APPROVED) {
            taskStateChange.setVisibility(View.GONE);
        }

        if (AppData.getCache().getIdentity().getId() != U.getTaskButtonViewer(taskDetail.status, taskDetail.assignerId, taskDetail.assigneeId)) {
            taskStateChange.setVisibility(View.GONE);
        }


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
        taskStateChange = (Button)findViewById(R.id.task_change_status_button);
        taskTeamRow = (TableRow) findViewById(R.id.task_proxy_team_table_row);
        taskRevise = (Button) findViewById(R.id.task_revise_button);


    }

    public void buildActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Task " + taskDetail.number);
        actionBar.setSubtitle("" + taskDetail.name);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }


    public void setTaskStatus(View view) {

        final Activity activity = this;

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                List<ListTask> listTasks = new ArrayList<>();

                Runnable runnable2 = new Runnable() {
                    @Override
                    public void run() {
                        for (ListTask listTask : listTasks) {
                            if (listTask.id == taskDetail.id) {
                                taskDetail = listTask.detail;
                            }
                        }
                        buildViewSet();

                    }
                };

                AppHandler.getInstance().projectHandler.getTasks(project.id, taskSet.id, 0, 500, listTasks, activity, runnable2);

            }
        };

        switch (taskDetail.status) {

            case WAITING:
                AppHandler.getInstance().projectHandler.startTask(taskDetail.id, project.id, this, runnable);
                break;

            case INPROGRESS:
                AppHandler.getInstance().projectHandler.completeTask(taskDetail.id, project.id, this, runnable);
                break;

            case PENDINGREVISION:
                AppHandler.getInstance().projectHandler.changeTaskStatus(taskDetail.id, project.id, "approve", this, runnable);
                break;

            case PENDINGEVALUATION:
                AppHandler.getInstance().projectHandler.changeTaskStatus(taskDetail.id, project.id, "approve", this, runnable);
                break;

            default:
                break;

        }

    }

    public void setTaskRevise(View view) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {



            }
        };

        AppHandler.getInstance().projectHandler.changeTaskStatus(taskDetail.id, project.id, "revise", this, runnable);

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
