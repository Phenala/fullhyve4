package com.ux7.fullhyve.ui.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ux7.fullhyve.R;
import com.ux7.fullhyve.ui.activities.ContactView;
import com.ux7.fullhyve.ui.data.ListContact;
import com.ux7.fullhyve.ui.interfaces.OnHomeInteractionListener;
import com.ux7.fullhyve.ui.util.CircleTransform;
import com.ux7.fullhyve.ui.util.Images;
import com.ux7.fullhyve.ui.util.U;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a DummyItem and makes a call to the
 * specified .
 * TODO: Replace the implementation with code for your data type.
 */
public class ContactRecyclerViewAdapter extends RecyclerView.Adapter<ContactRecyclerViewAdapter.ViewHolder> {

    private final List<ListContact> mContacts;
    private final OnHomeInteractionListener mListener;

    public ContactRecyclerViewAdapter(List<ListContact> items, OnHomeInteractionListener listener) {
        mContacts = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_contact, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mContact = mContacts.get(position);
        holder.mName.setText(holder.mContact.name);
        holder.mMessage.setText(holder.mContact.lastMessage);
        if (holder.mContact.lastMessageSent) {
            holder.mLastMessageSent.setVisibility(View.VISIBLE);
        } else {
            holder.mLastMessageSent.setVisibility(View.GONE);
        }
        holder.mMessageCount.setText(String.valueOf(holder.mContact.newMessages));
        if (holder.mContact.newMessages == 0)
            holder.mNewMessageCountFrame.setVisibility(View.GONE);
        else
            holder.mNewMessageCountFrame.setVisibility(View.VISIBLE);


        holder.mPicture.setBackgroundResource(Images.USER);

        if (holder.mContact.image != null) {

            Picasso.with(holder.itemView.getContext())
                    .load(U.getImageUrl(holder.mContact.image))
                    .transform(new CircleTransform())
                    .into(holder.mPicture);

        } else {

                holder.mPicture.setImageResource(Images.USER);

        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    openMessages(holder.mContact);
                }
            }
        });
    }

    public void openMessages(ListContact contact) {
        Intent intent = new Intent(mListener.getHomeContext(), ContactView.class);
        intent.putExtra("contact", contact);
        contact.newMessages = 0;
        mListener.onStartNewActivity(intent);
    }

    public void update() {

        notifyDataSetChanged();

    }


    @Override
    public int getItemCount() {
        return mContacts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final ImageView mPicture;
        public final TextView mName;
        public final TextView mMessage;
        public final TextView mMessageCount;
        public final ImageView mLastMessageSent;
        public final FrameLayout mNewMessageCountFrame;
        public ListContact mContact;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mPicture = (ImageView) view.findViewById(R.id.profilePicture);
            mName = (TextView) view.findViewById(R.id.profileName);
            mMessage = (TextView) view.findViewById(R.id.message) ;
            mMessageCount = (TextView) view.findViewById(R.id.newMessageCount);
            mLastMessageSent = (ImageView) view.findViewById(R.id.contact_last_message_sent);
            mNewMessageCountFrame = (FrameLayout) view.findViewById(R.id.contact_last_message_seen_frame);
        }

        @Override
        public String toString() {
            return super.toString() + " ";
        }
    }
}
