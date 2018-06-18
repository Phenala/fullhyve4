package com.ux7.fullhyve.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ux7.fullhyve.R;
import com.ux7.fullhyve.ui.activities.AddMemberView;
import com.ux7.fullhyve.ui.activities.UserView;
import com.ux7.fullhyve.ui.data.ListMember;
import com.ux7.fullhyve.ui.data.UserDetail;
import com.ux7.fullhyve.ui.fragments.MemberFragment.OnListFragmentInteractionListener;
import com.ux7.fullhyve.ui.util.CircleTransform;
import com.ux7.fullhyve.ui.util.Images;
import com.ux7.fullhyve.ui.util.U;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.in;


public class AddMemberRecyclerViewAdapter extends RecyclerView.Adapter<AddMemberRecyclerViewAdapter.ViewHolder> {

    private final List<ListMember> mMembers;
    public final List<ListMember> mSelectedUsers;
    public final List<ListMember> mSelectedTeams;
    public ListMember selectedUser;
    public ListMember selectedTeam;
    public AddMemberView.AddUserType type;
    public RadioButton lastRadioButton;
    public OnAddMemberInteractionListener mListener;

    public AddMemberRecyclerViewAdapter(List<ListMember> items, AddMemberView.AddUserType type, OnAddMemberInteractionListener listener) {
        mMembers = items;
        this.type = type;
        mListener = listener;
        mSelectedUsers = new ArrayList<>();
        mSelectedTeams = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_add_member, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mMember = mMembers.get(position);
        holder.mMemberNameView.setText(mMembers.get(position).name);
        holder.mMemberPictureView.setBackgroundResource(Images.USER);

        if (type == AddMemberView.AddUserType.ASSIGN_TASK_TEAM || type == AddMemberView.AddUserType.ASSIGN_TASK_USER) {

            holder.mAddMemberRadio.setVisibility(View.VISIBLE);
            holder.mAddMember.setVisibility(View.GONE);

        } else {

            holder.mAddMemberRadio.setVisibility(View.GONE);
            holder.mAddMember.setVisibility(View.VISIBLE);

        }

        if (selectedTeam != null && holder.mMember.team && holder.mMember.id != selectedTeam.id)
            holder.mAddMemberRadio.setChecked(false);

        if (selectedUser != null && !holder.mMember.team && holder.mMember.id != selectedUser.id)
            holder.mAddMemberRadio.setChecked(false);

        if (!holder.mMember.team && !mSelectedUsers.contains(holder.mMember.id))
            holder.mAddMember.setChecked(false);

        if (holder.mMember.team && !mSelectedTeams.contains(holder.mMember.id))
            holder.mAddMember.setChecked(false);

        if (holder.mMember.image != null) {

            Picasso.with(holder.itemView.getContext())
                    .load(U.getImageUrl(holder.mMember.image))
                    .transform(new CircleTransform())
                    .into(holder.mMemberPictureView);
        } else {

            holder.mMemberPictureView.setImageResource(Images.USER);

        }

        final Context context = holder.mView.getContext();

        holder.mView.setOnClickListener(
                new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.

                goToUser(context, holder.mMember.userDetail);

                // mListener.onListFragmentInteraction(holder.mMember);
                notifyItemChanged(position);

            }

        });

        holder.mAddMemberRadio.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (holder.mMember.team) {
                    selectedTeam = holder.mMember;
                    selectedUser = null;
                } else {
                    selectedUser = holder.mMember;
                    selectedTeam = null;
                }


                if (lastRadioButton != null && lastRadioButton != holder.mAddMemberRadio) {
                    lastRadioButton.setChecked(false);
                }

                lastRadioButton = holder.mAddMemberRadio;

            }
        });

        holder.mAddMember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                List<ListMember> ids;

                if (holder.mMember.team)
                    ids = mSelectedTeams;
                else
                    ids = mSelectedUsers;

                if (b)
                    ids.add(holder.mMember);
                else
                    ids.remove(holder.mMember);

            }
        });
    }


    public void update() {

        notifyDataSetChanged();

    }

    public void resetSelections() {



    }

    public ListMember getSelectedUser() {
        return selectedUser;
    }

    public ListMember getSelectedTeam() {
        return selectedTeam;
    }

    @Override
    public int getItemCount() {
        return mMembers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mMemberPictureView;
        public final TextView mMemberNameView;
        public final CheckBox mAddMember;
        public final RadioButton mAddMemberRadio;
        public ListMember mMember;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mMemberPictureView = (ImageView) view.findViewById(R.id.member_picture);
            mMemberNameView = (TextView) view.findViewById(R.id.member_name);
            mAddMember = (CheckBox) view.findViewById(R.id.add_member_check);
            mAddMemberRadio = (RadioButton) view.findViewById(R.id.add_member_radio);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mMemberNameView.getText() + "'";
        }
    }

    public void goToUser(Context context, UserDetail userDetail) {

        Intent intent = new Intent(context, UserView.class);
        intent.putExtra("userDetail", userDetail);
        context.startActivity(intent);


    }

    public List<ListMember> getSelectedUsers() {

        return mSelectedUsers;

    }

    public List<ListMember> getSelectedTeams() {

        return mSelectedTeams;

    }

    public interface OnAddMemberInteractionListener {

        public void onUnselectOthers(Runnable runnable);

    }

}
