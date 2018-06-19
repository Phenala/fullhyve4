package com.ux7.fullhyve.ui.adapters;

import android.app.Activity;
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
import com.ux7.fullhyve.services.Handlers.AppHandler;
import com.ux7.fullhyve.services.Models.Link;
import com.ux7.fullhyve.ui.activities.UserView;
import com.ux7.fullhyve.ui.data.ListNotification;
import com.ux7.fullhyve.ui.data.UserDetail;
import com.ux7.fullhyve.ui.util.CircleTransform;
import com.ux7.fullhyve.ui.util.U;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;



public class NotificationRecyclerViewAdapter extends RecyclerView.Adapter<NotificationRecyclerViewAdapter.ViewHolder> {

    private final List<ListNotification> mNotifications;
    public final List<String> mSelectedUsers;
    public Activity activity;
    OnNotificationRecyclerInteractionListener mListener;

    public NotificationRecyclerViewAdapter(List<ListNotification> items, Activity activity, OnNotificationRecyclerInteractionListener listener) {
        mNotifications = items;
        mSelectedUsers = new ArrayList<>();
        this.activity = activity;
        mListener = listener;
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

        if (holder.mNotification.image != null)

            Picasso.with(activity)
                    .load(U.getImageUrl(holder.mNotification.image))
                    .transform(new CircleTransform())
                    .into(holder.mNotificationImage);

        if (holder.mNotification.type == Link.LinkType.ASSIGNMENT) {
            holder.mNotificationNegative.setVisibility(View.GONE);
            holder.mNotificationPositive.setVisibility(View.GONE);
        }

        holder.mNotificationPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                notificationRespond(holder.mNotification, true);

            }
        });

        holder.mNotificationNegative.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                notificationRespond(holder.mNotification, false);

            }
        });

    }

    public void notificationRespond(ListNotification notification, boolean accept) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                mListener.onInteractNotification(notification);

            }
        };

        switch (notification.type) {

            case FRIEND_REQUEST:
                AppHandler.getInstance().loginHandler.replyFriendRequest(notification.id, accept, activity, runnable);
                break;

            case TEAM_REQUEST:
                AppHandler.getInstance().teamHandler.replyTeamJoinRequest(notification.id, accept, activity, runnable);
                break;

            case PROJECT_TEAM_REQUEST:
                AppHandler.getInstance().projectHandler.replyTeamContributorJoinRequest(notification.id, accept, activity, runnable);
                break;

            case PROJECT_INDIVIDUAL_REQUEST:
                AppHandler.getInstance().projectHandler.replyIndividualContributorJoinRequest(notification.id, accept, activity, runnable);
                break;

            case ASSIGNMENT:
                break;

        }

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
        public final ImageView mNotificationImage;
        public ListNotification mNotification;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mNotificationMessage = (TextView) view.findViewById(R.id.notification_message);
            mNotificationPositive = (Button) view.findViewById(R.id.notification_positive);
            mNotificationNegative = (Button) view.findViewById(R.id.notification_negative);
            mNotificationImage = (ImageView) view.findViewById(R.id.notification_image);
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }

    public String[] getSelectedUserIds() {

        return mSelectedUsers.toArray(new String[]{});

    }

    public interface OnNotificationRecyclerInteractionListener {

        void onInteractNotification(ListNotification notification);

    }


}
