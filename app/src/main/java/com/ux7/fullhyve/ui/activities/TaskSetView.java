package com.ux7.fullhyve.ui.activities;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;
import com.ux7.fullhyve.R;
import com.ux7.fullhyve.ui.adapters.TaskRecyclerViewAdapter;
import com.ux7.fullhyve.ui.data.ListTask;
import com.ux7.fullhyve.ui.data.TaskSetDetail;
import com.ux7.fullhyve.ui.util.ActionBarTarget;
import com.ux7.fullhyve.ui.util.CircleTransform;

import java.util.ArrayList;

public class TaskSetView extends AppCompatActivity {

    TaskSetDetail taskSetDetail;
    LinearLayout taskDetailsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_set_view);

        buildViews();
        buildTaskSet();
        buildActionBar();
        buildRecyclerView();
    }

    public void buildViews() {

        taskDetailsLayout = (LinearLayout)findViewById(R.id.task_set_details_layout);

    }

    public void buildRecyclerView() {

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.task_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        ArrayList<ListTask> tasks = new ArrayList<>();
        for (int i = 0; i < 20; i++) {

            tasks.add(new ListTask());

        }

        final FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.task_add_task_fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), NewTaskView.class);
                startActivity(intent);
            }
        });

        final LinearLayout taskSetDets = taskDetailsLayout;

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(final RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int itemPosition = ((LinearLayoutManager)recyclerView.getLayoutManager()).findFirstVisibleItemPosition();

                    if (itemPosition == (0)) {
                        // here you can fetch new data from server.
                        fab.show();
                    } else {
                        fab.hide();
                    }
                }
            }
        });

        recyclerView.setAdapter(new TaskRecyclerViewAdapter(tasks));

    }

    public void buildTaskSet() {

        taskSetDetail = (TaskSetDetail) getIntent().getSerializableExtra("taskset");

    }

    public void buildActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Task Set " + taskSetDetail.number);
        actionBar.setSubtitle("" + taskSetDetail.name);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task_set, menu);
        return true;
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
