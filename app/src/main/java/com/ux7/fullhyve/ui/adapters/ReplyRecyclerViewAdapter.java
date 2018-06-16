package com.ux7.fullhyve.ui.adapters;

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
import com.ux7.fullhyve.services.Models.Identity;
import com.ux7.fullhyve.services.Storage.AppData;
import com.ux7.fullhyve.ui.data.ListReply;
import com.ux7.fullhyve.ui.data.ListTeam;
import com.ux7.fullhyve.ui.util.CircleTransform;
import com.ux7.fullhyve.ui.util.Images;
import com.ux7.fullhyve.ui.util.U;

import java.sql.Time;
import java.util.List;

/**
 * Created by hp on 4/17/2018.
 */

public class ReplyRecyclerViewAdapter extends RecyclerView.Adapter<ReplyRecyclerViewAdapter.ViewHolder> {

    public ListTeam team;
    public List<ListReply> mReplys;
    public OnReplyInteractionListener mListener;

    public ReplyRecyclerViewAdapter(List<ListReply> messageList, OnReplyInteractionListener listener, ListTeam team) {
        mReplys = messageList;
        this.team = team;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_reply, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.mReply = mReplys.get(position);
        holder.mReplyContent.setText(holder.mReply.reply);
        holder.mReplyTime.setText(holder.mReply.time);
        holder.mSenderName.setText(holder.mReply.senderName);
        holder.mSenderImage.setBackgroundResource(Images.USER);

        if (holder.mReply.senderImage != null) {

            Picasso.with(holder.mView.getContext())
                    .load(U.getImageUrl(holder.mReply.senderImage))
                    .transform(new CircleTransform())
                    .into(holder.mSenderImage);

        } else {

            holder.mSenderImage.setImageResource(Images.USER);

        }

        LinearLayout body = (LinearLayout)holder.mView.findViewById(R.id.reply_body);
        int normalPadding = body.getPaddingBottom();

        if (holder.mReply.pAnnouncement) {

            body.setBackgroundColor(holder.mView.getContext().getResources().getColor(R.color.messageReceived));
            if (body.getPaddingTop() == body.getPaddingBottom())
                body.setPadding(body.getPaddingLeft(), body.getPaddingTop(), body.getPaddingRight(), normalPadding * 2);
            holder.mReplyContent.setTextColor(holder.mView.getContext().getResources().getColor(R.color.textdark));
            holder.mReplyTime.setTextColor(holder.mView.getContext().getResources().getColor(R.color.textdark));

        } else {
            body.setBackgroundResource(R.drawable.ripple_effect);
            if (body.getPaddingTop() != body.getPaddingBottom())
                body.setPadding(body.getPaddingLeft(), body.getPaddingTop(), body.getPaddingRight(), normalPadding / 2);
            holder.mReplyContent.setTextColor(holder.mView.getContext().getResources().getColor(R.color.textLight));
            body.setPadding(body.getPaddingLeft(), body.getPaddingTop(), body.getPaddingRight(), normalPadding );
            holder.mReplyTime.setTextColor(holder.mView.getContext().getResources().getColor(R.color.textLight));
        }


        body.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                PopupMenu popup = new PopupMenu(view.getContext(), view);
                MenuInflater inflater = popup.getMenuInflater();

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.reply_option_edit:
                                mListener.onEditReply(holder.mReply);
                                break;

                            case R.id.reply_option_delete:
                                mListener.onDeleteReply(holder.mReply);
                                break;

                        }

                        return false;
                    }
                });

                Identity identity = AppData.getCache().getIdentity();

                if (holder.mReply.senderId == identity.getId()) {
                    inflater.inflate(R.menu.menu_reply_self, popup.getMenu());
                } else {
                    if (team.detail.leaderId == identity.getId()) {
                        inflater.inflate(R.menu.menu_reply_admin_others, popup.getMenu());
                    }
                }
                popup.show();

                return false;
            }
        });

    }


    public void update() {

        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return mReplys.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final ImageView mSenderImage;
        public final TextView mReplyContent;
        public final TextView mReplyTime;
        public final TextView mSenderName;
        public final LinearLayout mReplyTrigger;

        public ListReply mReply;


        public ViewHolder(View view) {

            super(view);
            mView = view;
            mReplyContent = (TextView)view.findViewById(R.id.reply_content);
            mReplyTime = (TextView)view.findViewById(R.id.reply_time);
            mSenderImage = (ImageView)view.findViewById(R.id.reply_sender_image);
            mSenderName = (TextView)view.findViewById(R.id.reply_sender);
            mReplyTrigger = (LinearLayout)view.findViewById(R.id.reply_trigger);


        }



    }

    public interface OnReplyInteractionListener {

        public void onEditReply(ListReply reply);

        public void onDeleteReply(ListReply reply);

    }
}
