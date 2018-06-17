package com.ux7.fullhyve.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ux7.fullhyve.R;
import com.ux7.fullhyve.ui.activities.TaskSetView;
import com.ux7.fullhyve.ui.data.ListProject;
import com.ux7.fullhyve.ui.data.ListTaskSet;
import com.ux7.fullhyve.ui.data.TaskSetDetail;
import com.ux7.fullhyve.ui.interfaces.OnHomeInteractionListener;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {} and makes a call to the
 * specified {@link OnHomeInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class TaskSetRecyclerViewAdapter extends RecyclerView.Adapter<TaskSetRecyclerViewAdapter.ViewHolder> {

    private final List<ListTaskSet> mTaskSets;
    ListProject project;

    public TaskSetRecyclerViewAdapter(List<ListTaskSet> items, ListProject listProject) {
        mTaskSets = items;
        project = listProject;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_task_set, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mTaskSet = mTaskSets.get(position);
        holder.mTaskSetNumberView.setText("Task Set " + holder.mTaskSet.number);
        holder.mTaskSetNameView.setText(holder.mTaskSet.name);
        holder.mTaskSetCompletionView.setText(holder.mTaskSet.completion + "%");
        holder.mTaskSetAssignmentsView.setText("" + holder.mTaskSet.assigments);
        holder.mTaskSetCompletionPercentView.setProgress(holder.mTaskSet.completion);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToTaskSet(holder.mView.getContext(), holder.mTaskSet);
                notifyItemChanged(position);
            }
        });
    }

    public void goToTaskSet(Context context, ListTaskSet taskSet) {

        Intent intent = new Intent(context, TaskSetView.class);
        intent.putExtra("taskset", taskSet.detail);
        intent.putExtra("project", project);
        context.startActivity(intent);

    }



    public void update() {

        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return mTaskSets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTaskSetNumberView;
        public final TextView mTaskSetNameView;
        public final TextView mTaskSetCompletionView;
        public final TextView mTaskSetAssignmentsView;
        public final ProgressBar mTaskSetCompletionPercentView;
        public ListTaskSet mTaskSet;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTaskSetAssignmentsView = (TextView)view.findViewById(R.id.task_set_assignments);
            mTaskSetCompletionView = (TextView)view.findViewById(R.id.task_set_completion);
            mTaskSetNameView = (TextView)view.findViewById(R.id.task_set_name);
            mTaskSetNumberView = (TextView)view.findViewById(R.id.task_set_number);
            mTaskSetCompletionPercentView = (ProgressBar) view.findViewById(R.id.task_set_completion_percent);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
