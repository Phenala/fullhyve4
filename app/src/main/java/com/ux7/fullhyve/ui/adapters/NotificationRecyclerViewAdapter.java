package com.ux7.fullhyve.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ux7.fullhyve.R;
import com.ux7.fullhyve.ui.activities.UserView;
import com.ux7.fullhyve.ui.data.ListNotification;
import com.ux7.fullhyve.ui.data.UserDetail;
import com.ux7.fullhyve.ui.util.CircleTransform;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;



public class NotificationRecyclerViewAdapter extends RecyclerView.Adapter<NotificationRecyclerViewAdapter.ViewHolder> {

    private final List<ListNotification> mNotifications;
    public final List<String> mSelectedUsers;

    public NotificationRecyclerViewAdapter(List<ListNotification> items) {
        mNotifications = items;
        mSelectedUsers = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.mNotification = mNotifications.get(position);
        holder.mNotificationMessage.setText(holder.mNotification.message);
        holder.mNotificationPositive.setText(holder.mNotification.positiiveName);
        holder.mNotificationNegative.setText(holder.mNotification.negativeName);

    }


    public void update() {

        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return mNotifications.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mNotificationMessage;
        public final Button mNotificationPositive;
        public final Button mNotificationNegative;
        public ListNotification mNotification;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNotificationMessage = (TextView) view.findViewById(R.id.notification_message);
            mNotificationPositive = (Button) view.findViewById(R.id.notification_positive);
            mNotificationNegative = (Button) view.findViewById(R.id.notification_negative);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }

    public String[] getSelectedUserIds() {

        return mSelectedUsers.toArray(new String[]{});

    }


}
