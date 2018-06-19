package com.ux7.fullhyve.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ux7.fullhyve.R;
import com.ux7.fullhyve.services.Storage.AppData;
import com.ux7.fullhyve.ui.activities.TaskView;
import com.ux7.fullhyve.ui.data.ListProject;
import com.ux7.fullhyve.ui.data.ListTask;
import com.ux7.fullhyve.ui.data.ListTaskSet;
import com.ux7.fullhyve.ui.data.TaskDetail;
import com.ux7.fullhyve.ui.data.TaskSetDetail;
import com.ux7.fullhyve.ui.interfaces.OnHomeInteractionListener;
import com.ux7.fullhyve.ui.util.CircleTransform;
import com.ux7.fullhyve.ui.util.Images;
import com.ux7.fullhyve.ui.util.U;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {} and makes a call to the
 * specified {@link OnHomeInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class TaskRecyclerViewAdapter extends RecyclerView.Adapter<TaskRecyclerViewAdapter.ViewHolder> {

    private final List<ListTask> mTasks;
    public ListProject project;
    public TaskSetDetail taskSet;
    public boolean myTasks = false;

    public TaskRecyclerViewAdapter(List<ListTask> items, ListProject project, TaskSetDetail taskSet) {
        mTasks = items;
        this.project = project;
        this.taskSet = taskSet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mTask = getFilteredList(mTasks).get(position);
        holder.mTaskNumberView.setText("Task " + holder.mTask.number);
        holder.mTaskNameView.setText(holder.mTask.name);
        holder.mTaskStatusView.setImageResource(U.getTaskStatusIcon(holder.mTask.status));

        if (holder.mTask.assigneeImage != null) {

            Picasso.with(holder.itemView.getContext())
                    .load(U.getImageUrl(holder.mTask.assigneeImage))
                    .transform(new CircleTransform())
                    .into(holder.mTaskAssigneeView);

        } else {

            holder.mTaskAssigneeView.setImageResource(Images.USER);

        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTask(holder.mView.getContext(), holder.mTask);
                notifyItemChanged(position);
            }
        });
    }

    public void goToTask(Context context, ListTask task) {

        Intent intent = new Intent(context, TaskView.class);
        intent.putExtra("task", task.detail);
        intent.putExtra("project", project);
        intent.putExtra("taskset", taskSet);
        context.startActivity(intent);

    }


    public void update() {

        notifyDataSetChanged();

    }

    public List<ListTask> getFilteredList(List<ListTask> tasks) {

        if (!myTasks)
            return tasks;

        List<ListTask> listTasks = new ArrayList<>();

        for (ListTask task : tasks) {

            if (task.assigneeId == AppData.getCache().getIdentity().getId()) {
                listTasks.add(task);
            }

        }

        return listTasks;

    }

    public void setFilterTasks(boolean value) {

        myTasks = value;
        update();

    }

    @Override
    public int getItemCount() {
        return getFilteredList(mTasks).size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTaskNumberView;
        public final TextView mTaskNameView;
        public final ImageView mTaskAssigneeView;
        public final ImageView mTaskStatusView;
        public ListTask mTask;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTaskAssigneeView = (ImageView) view.findViewById(R.id.task_assignee_picture);
            mTaskStatusView = (ImageView) view.findViewById(R.id.task_status);
            mTaskNameView = (TextView)view.findViewById(R.id.task_name);
            mTaskNumberView = (TextView)view.findViewById(R.id.task_number);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
