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
import com.ux7.fullhyve.ui.activities.ProjectView;
import com.ux7.fullhyve.ui.data.ListProject;
import com.ux7.fullhyve.ui.interfaces.OnHomeInteractionListener;
import com.ux7.fullhyve.ui.util.CircleTransform;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {} and makes a call to the
 * specified {@link OnHomeInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class ProjectsRecyclerViewAdapter extends RecyclerView.Adapter<ProjectsRecyclerViewAdapter.ViewHolder> {

    private final List<ListProject> mProjects;
    private final OnHomeInteractionListener mListener;

    public ProjectsRecyclerViewAdapter(List<ListProject> items, OnHomeInteractionListener listener) {
        mProjects = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_project, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mProject = mProjects.get(position);
        holder.mNameView.setText(mProjects.get(position).name);

        Picasso.with(holder.mView.getContext())
                .load(holder.mProject.image)
                .transform(new CircleTransform())
                .into(holder.mPicture);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goToProject(holder.mView.getContext(), holder.mProject);
                //mListener.onProjectListFragmentInteraction(holder.mProject);
                notifyItemChanged(position);

            }
        });
    }

    public void goToProject(Context context, ListProject project) {

        Intent intent = new Intent(context, ProjectView.class);
        intent.putExtra("name", project.name);
        intent.putExtra("image", project.image);
        context.startActivity(intent);

    }

    @Override
    public int getItemCount() {
        return mProjects.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mPicture;
        public final TextView mNameView;
        public ListProject mProject;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mPicture = (ImageView) view.findViewById(R.id.projectPicture);
            mNameView = (TextView) view.findViewById(R.id.projectName);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
