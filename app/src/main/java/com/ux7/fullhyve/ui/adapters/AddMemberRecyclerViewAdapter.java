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
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ux7.fullhyve.R;
import com.ux7.fullhyve.ui.activities.UserView;
import com.ux7.fullhyve.ui.data.ListMember;
import com.ux7.fullhyve.ui.data.UserDetail;
import com.ux7.fullhyve.ui.fragments.MemberFragment.OnListFragmentInteractionListener;
import com.ux7.fullhyve.ui.util.CircleTransform;
import com.ux7.fullhyve.ui.util.Images;
import com.ux7.fullhyve.ui.util.U;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class AddMemberRecyclerViewAdapter extends RecyclerView.Adapter<AddMemberRecyclerViewAdapter.ViewHolder> {

    private final List<ListMember> mMembers;
    public final List<Integer> mSelectedUsers;

    public AddMemberRecyclerViewAdapter(List<ListMember> items) {
        mMembers = items;
        mSelectedUsers = new ArrayList<>();
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

                goToUser(context, holder.mMember.id, holder.mMember.name, holder.mMember.image);

                // mListener.onListFragmentInteraction(holder.mMember);
                notifyItemChanged(position);

            }

        });

        holder.mAddMember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (b)

                    mSelectedUsers.add(holder.mMember.id);

                else

                    mSelectedUsers.remove(mSelectedUsers.indexOf(holder.mMember.id));

            }
        });
    }


    public void update() {

        notifyDataSetChanged();

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
        public ListMember mMember;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mMemberPictureView = (ImageView) view.findViewById(R.id.member_picture);
            mMemberNameView = (TextView) view.findViewById(R.id.member_name);
            mAddMember = (CheckBox) view.findViewById(R.id.add_member_check);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mMemberNameView.getText() + "'";
        }
    }

    public void goToUser(Context context, int id, String name, String image) {

        Intent intent = new Intent(context, UserView.class);
        intent.putExtra("id", id);
        intent.putExtra("name", name);
        intent.putExtra("image", image);
        context.startActivity(intent);


    }

    public int[] getSelectedUserIds() {

        int[] outids = new int[mSelectedUsers.size()];

        for (int i = 0; i < mSelectedUsers.size(); i++) {

            outids[i] = mSelectedUsers.get(i);

        }

        return outids;

    }


}
