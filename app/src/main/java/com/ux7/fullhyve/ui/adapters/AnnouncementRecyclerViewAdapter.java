package com.ux7.fullhyve.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ux7.fullhyve.R;
import com.ux7.fullhyve.services.Storage.AppData;
import com.ux7.fullhyve.ui.activities.AnnouncementView;
import com.ux7.fullhyve.ui.data.ListAnnouncement;
import com.ux7.fullhyve.ui.data.ListTeam;
import com.ux7.fullhyve.ui.util.CircleTransform;
import com.ux7.fullhyve.ui.util.Images;
import com.ux7.fullhyve.ui.util.U;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hp on 4/17/2018.
 */

public class AnnouncementRecyclerViewAdapter extends RecyclerView.Adapter<AnnouncementRecyclerViewAdapter.ViewHolder> {

    public List<ListAnnouncement> mAnnouncements;
    public ListTeam team;
    public OnAnnouncmentRecyclerInteractionListener mListener;

    public AnnouncementRecyclerViewAdapter(List<ListAnnouncement> messageList, ListTeam team, OnAnnouncmentRecyclerInteractionListener listener) {
        mAnnouncements = messageList;
        this.team = team;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_announcement, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.mAnnouncement = mAnnouncements.get(position);
        holder.mAnnouncementContent.setText(holder.mAnnouncement.message);
        holder.mAnnouncementTime.setText(holder.mAnnouncement.sentTime);
        holder.mAnnouncerName.setText(holder.mAnnouncement.senderName);
        holder.mAnnouncerImage.setBackgroundResource(Images.USER);

        if (holder.mAnnouncement.senderImage != null) {

            Picasso.with(holder.mView.getContext())
                    .load(U.getImageUrl(holder.mAnnouncement.senderImage))
                    .transform(new CircleTransform())
                    .into(holder.mAnnouncerImage);

        } else {

            holder.mAnnouncerImage.setImageResource(Images.USER);

        }

        Context context = holder.mView.getContext();

        if (holder.mAnnouncement.sent) {
            ((ImageView)holder.mView.findViewById(R.id.callout_spike_receive)).setVisibility(View.GONE);
            ((ImageView)holder.mView.findViewById(R.id.announcer_image)).setVisibility(View.GONE);
            ((ImageView)holder.mView.findViewById(R.id.callout_spike_send)).setVisibility(View.VISIBLE);
            ((View)holder.mView.findViewById(R.id.callout_body)).setBackground(context.getResources().getDrawable(R.drawable.ripple_effect_sent));
            holder.mAnnouncementContent.setTextColor(holder.mView.getResources().getColor(R.color.textLight));
            //holder.mView.findViewById(R.id.messages_loading_spinner).setVisibility(View.GONE);
        } else {
            ((ImageView)holder.mView.findViewById(R.id.callout_spike_receive)).setVisibility(View.VISIBLE);
            ((ImageView)holder.mView.findViewById(R.id.announcer_image)).setVisibility(View.VISIBLE);
            ((ImageView)holder.mView.findViewById(R.id.callout_spike_send)).setVisibility(View.GONE);
            ((View)holder.mView.findViewById(R.id.callout_body)).setBackground(context.getResources().getDrawable(R.drawable.ripple_effect_received));
            holder.mAnnouncementContent.setTextColor(holder.mView.getResources().getColor(R.color.textdark));
            //holder.mView.findViewById(R.id.messages_loading_spinner).setVisibility(View.VISIBLE);
        }

        if (holder.mAnnouncement.replies == 0) {
            holder.getmAnnouncementReplyCount.setVisibility(View.GONE);
            holder.getmAnnouncementReplyCount.setText(holder.mAnnouncement.replies + " replies");
        } else {
            holder.getmAnnouncementReplyCount.setVisibility(View.VISIBLE);
            if (holder.mAnnouncement.replies == 1) {
                holder.getmAnnouncementReplyCount.setText("1 reply");
            } else {
                holder.getmAnnouncementReplyCount.setText(holder.mAnnouncement.replies + " replies");
            }
        }

        LinearLayout body = (LinearLayout) holder.mView.findViewById(R.id.callout_body);
        final ListAnnouncement announcement = holder.mAnnouncement;

        body.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                PopupMenu popup = new PopupMenu(view.getContext(), view);
                MenuInflater inflater = popup.getMenuInflater();

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.announcement_option_edit:
                                mListener.onEditAnnouncement(announcement);
                                break;

                            case R.id.announcement_option_delete:
                                mListener.onDeleteAnnouncement(announcement);
                                break;

                        }

                        return false;
                    }
                });

                if (announcement.sent) {
                    inflater.inflate(R.menu.menu_announcement_self, popup.getMenu());
                } else {
                    if (team.detail.leaderId == AppData.getCache().getIdentity().getId()) {
                        inflater.inflate(R.menu.menu_announcements_admin_others, popup.getMenu());
                    }
                }
                popup.show();

                return false;
            }
        });


        holder.mAnnouncementTrigger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToAnnouncement(holder.mView.getContext(), holder.mAnnouncement);
            }
        });

    }

    public void goToAnnouncement(Context context, ListAnnouncement announcement) {
        Intent intent = new Intent(context, AnnouncementView.class);
        intent.putExtra("announcement", (Serializable) announcement);
        intent.putExtra("team", team);
        context.startActivity(intent);
    }


    public void update() {

        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return mAnnouncements.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final ImageView mAnnouncerImage;
        public final TextView mAnnouncementContent;
        public final TextView mAnnouncementTime;
        public final TextView mAnnouncerName;
        public final TextView getmAnnouncementReplyCount;
        public final LinearLayout mAnnouncementTrigger;

        public ListAnnouncement mAnnouncement;


        public ViewHolder(View view) {

            super(view);
            mView = view;
            mAnnouncementContent = (TextView)view.findViewById(R.id.announcement_content);
            mAnnouncementTime = (TextView)view.findViewById(R.id.announcement_time);
            mAnnouncerImage = (ImageView)view.findViewById(R.id.announcer_image);
            mAnnouncerName = (TextView)view.findViewById(R.id.announcer_name);
            mAnnouncementTrigger = (LinearLayout)view.findViewById(R.id.callout_body);
            getmAnnouncementReplyCount = view.findViewById(R.id.announcement_reply_count);


        }

    }

    public interface OnAnnouncmentRecyclerInteractionListener {

        public void onEditAnnouncement(ListAnnouncement announcement);

        public void onDeleteAnnouncement(ListAnnouncement announcement);

    }

}
