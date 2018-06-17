package com.ux7.fullhyve.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ux7.fullhyve.R;
import com.ux7.fullhyve.ui.activities.TeamView;
import com.ux7.fullhyve.ui.data.ListTeam;
import com.ux7.fullhyve.ui.interfaces.OnHomeInteractionListener;
import com.ux7.fullhyve.ui.util.CircleTransform;
import com.ux7.fullhyve.ui.util.Images;
import com.ux7.fullhyve.ui.util.U;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {} and makes a call to the
 * specified {@link OnHomeInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class TeamsRecyclerViewAdapter extends RecyclerView.Adapter<TeamsRecyclerViewAdapter.ViewHolder> {

    private final List<ListTeam> mTeams;
    private final OnHomeInteractionListener mListener;

    public TeamsRecyclerViewAdapter(List<ListTeam> items, OnHomeInteractionListener listener) {
        mTeams = items;
        mListener = listener;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_team, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mTeam = mTeams.get(position);
        holder.mNameView.setText(mTeams.get(position).name);
        holder.mPicture.setBackgroundResource(Images.TEAM);

        Log.e("Retrieving image", "" + holder.mTeam.image + " for team " + holder.mTeam.name);

        if (holder.mTeam.image != null) {

            Picasso.with(holder.mView.getContext())
                    .load(U.getImageUrl(holder.mTeam.image))
                    .transform(new CircleTransform())
                    .into(holder.mPicture);

        } else {

            holder.mPicture.setImageResource(Images.TEAM);

        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    goToTeam(holder.mView.getContext(), holder.mTeam);
                    mListener.onTeamListFragmentInteraction(holder.mTeam);
                    notifyItemChanged(position);
                }
            }
        });
    }

    public void goToTeam(Context context, ListTeam team) {

        Intent intent = new Intent(context, TeamView.class);
        intent.putExtra("team", team);
        context.startActivity(intent);

    }

    public void update() {

        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return mTeams.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mPicture;
        public final TextView mNameView;
        public ListTeam mTeam;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mPicture = (ImageView) view.findViewById(R.id.teamPicture);
            mNameView = (TextView) view.findViewById(R.id.teamName);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
}
