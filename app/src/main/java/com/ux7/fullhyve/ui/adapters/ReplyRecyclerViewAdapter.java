package com.ux7.fullhyve.ui.adapters;

import android.provider.ContactsContract;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ux7.fullhyve.R;
import com.ux7.fullhyve.ui.data.ListReply;
import com.ux7.fullhyve.ui.util.CircleTransform;
import com.ux7.fullhyve.ui.util.Images;
import com.ux7.fullhyve.ui.util.Util;

import java.util.List;

/**
 * Created by hp on 4/17/2018.
 */

public class ReplyRecyclerViewAdapter extends RecyclerView.Adapter<ReplyRecyclerViewAdapter.ViewHolder> {

    public List<ListReply> mReplys;

    public ReplyRecyclerViewAdapter(List<ListReply> messageList) {
        mReplys = messageList;
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
                    .load(Util.getImageUrl(holder.mReply.senderImage))
                    .transform(new CircleTransform())
                    .into(holder.mSenderImage);

        } else {

            holder.mSenderImage.setImageResource(Images.USER);

        }

        LinearLayout body = (LinearLayout)holder.mView.findViewById(R.id.reply_body);
        int normalPadding = body.getPaddingBottom();

        if (holder.mReply.pAnnouncement) {

            body.setBackgroundColor(holder.mView.getContext().getResources().getColor(R.color.messageReceived));
            body.setPadding(body.getPaddingLeft(), body.getPaddingTop(), body.getPaddingRight(), normalPadding * 2);
            holder.mReplyContent.setTextColor(holder.mView.getContext().getResources().getColor(R.color.textdark));

        } else {
            body.setBackgroundColor(holder.mView.getContext().getResources().getColor(R.color.colorBackground));
            holder.mReplyContent.setTextColor(holder.mView.getContext().getResources().getColor(R.color.textLight));
            body.setPadding(body.getPaddingLeft(), body.getPaddingTop(), body.getPaddingRight(), normalPadding );
        }


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

}
