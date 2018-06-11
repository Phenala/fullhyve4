package com.ux7.fullhyve.ui.adapters;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ux7.fullhyve.R;
import com.ux7.fullhyve.ui.data.ListMessage;

import java.util.List;

/**
 * Created by hp on 4/17/2018.
 */

public class MessagesRecyclerViewAdapter extends RecyclerView.Adapter<MessagesRecyclerViewAdapter.ViewHolder> {

    public List<ListMessage> mMessages;
    public final OnMessageRecyclerInteractionListener mListener;

    public MessagesRecyclerViewAdapter(List<ListMessage> messageList, OnMessageRecyclerInteractionListener listener) {

        mMessages = messageList;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Context context = holder.mView.getContext();
        View body = (View)holder.mView.findViewById(R.id.callout_body);

        holder.mMessage = mMessages.get(position);
        holder.mMessageContent.setText(holder.mMessage.message);
        holder.mMessageTime.setText(holder.mMessage.time);


        if (holder.mMessage.sent) {
            ((ImageView)holder.mView.findViewById(R.id.callout_spike_receive)).setVisibility(View.GONE);
            ((ImageView)holder.mView.findViewById(R.id.callout_spike_send)).setVisibility(View.VISIBLE);
            body.setBackground(context.getResources().getDrawable(R.drawable.ripple_effect_sent));
            holder.mMessageContent.setTextColor(holder.mView.getResources().getColor(R.color.textLight));
            holder.mView.findViewById(R.id.messages_loading_spinner).setVisibility(View.GONE);
        } else {
            ((ImageView)holder.mView.findViewById(R.id.callout_spike_receive)).setVisibility(View.VISIBLE);
            ((ImageView)holder.mView.findViewById(R.id.callout_spike_send)).setVisibility(View.GONE);
            body.setBackground(context.getResources().getDrawable(R.drawable.ripple_effect_received));
            holder.mMessageContent.setTextColor(holder.mView.getResources().getColor(R.color.textdark));
            holder.mView.findViewById(R.id.messages_loading_spinner).setVisibility(View.VISIBLE);
        }

        if (mMessages.get(position).spinner) {
            holder.mView.findViewById(R.id.message_layout).setVisibility(View.GONE);
            holder.mView.findViewById(R.id.messages_loading_spinner).setVisibility(View.VISIBLE);
        } else {
            holder.mView.findViewById(R.id.message_layout).setVisibility(View.VISIBLE);
            holder.mView.findViewById(R.id.messages_loading_spinner).setVisibility(View.GONE);
        }

        final ListMessage message = holder.mMessage;

        body.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                PopupMenu popup = new PopupMenu(view.getContext(), view);
                MenuInflater inflater = popup.getMenuInflater();

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        switch (item.getItemId()) {

                            case R.id.message_option_forward:
                                mListener.onForwardMessage(holder.mView, message);
                                break;

                            case R.id.message_option_edit:
                                mListener.onEditMessage(holder.mView, message);
                                break;

                            case R.id.message_option_delete:
                                mListener.onDeleteMessage(holder.mView, message);
                                break;

                        }

                        return false;
                    }
                });

                if (message.sent) {
                    inflater.inflate(R.menu.menu_message_sent, popup.getMenu());
                } else {
                    inflater.inflate(R.menu.menu_message_recieved, popup.getMenu());
                }
                popup.show();

                return false;
            }
        });

        holder.mView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                return false;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mMessages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View mView;
        public final TextView mMessageContent;
        public final TextView mMessageTime;

        public ListMessage mMessage;


        public ViewHolder(View view) {

            super(view);
            mView = view;
            mMessageContent = (TextView)view.findViewById(R.id.message_content);
            mMessageTime = (TextView)view.findViewById(R.id.message_time);


        }

    }

    public void update() {

        notifyDataSetChanged();

    }

    public interface OnMessageRecyclerInteractionListener {

        void onForwardMessage(View view, ListMessage message);

        void onEditMessage(View view, ListMessage message);

        void onDeleteMessage(View view, ListMessage message);

    }

}
