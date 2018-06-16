package com.ux7.fullhyve.ui.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.squareup.picasso.Picasso;
import com.ux7.fullhyve.R;
import com.ux7.fullhyve.services.Handlers.AppHandler;
import com.ux7.fullhyve.services.Models.Identity;
import com.ux7.fullhyve.services.Storage.AppData;
import com.ux7.fullhyve.ui.adapters.ReplyRecyclerViewAdapter;
import com.ux7.fullhyve.ui.data.ListAnnouncement;
import com.ux7.fullhyve.ui.data.ListReply;
import com.ux7.fullhyve.ui.data.ListTeam;
import com.ux7.fullhyve.ui.util.ActionBarTarget;
import com.ux7.fullhyve.ui.util.CircleTransform;
import com.ux7.fullhyve.ui.util.Images;
import com.ux7.fullhyve.ui.util.U;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class AnnouncementView extends AppCompatActivity implements ReplyRecyclerViewAdapter.OnReplyInteractionListener {

    ListTeam team;
    ListAnnouncement announcement;
    List<ListReply> replies = new ArrayList<>();
    int replyEditingId = -1;

    LinearLayoutManager layoutManager;
    ReplyRecyclerViewAdapter adapter;
    EditText replyToSend;
    ImageButton replyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_view);

        buildAnnouncement();
        buildViews();
        buildActionBar();
        buildReplies();

    }

    public void buildAnnouncement() {

        announcement = (ListAnnouncement) getIntent().getSerializableExtra("announcement");
        team = (ListTeam) getIntent().getSerializableExtra("team");

    }

    public void buildViews() {

        replyToSend = findViewById(R.id.reply_to_send);
        replyButton = findViewById(R.id.reply_send_button);

    }

    public void buildReplies() {

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.reply_list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        adapter = new ReplyRecyclerViewAdapter(replies, this, team);
        recyclerView.setAdapter(adapter);

        getReplies();
//        recyclerView.getLayoutManager().scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id) {

            case android.R.id.home:
                finish();
                return false;

        }

        return super.onOptionsItemSelected(item);
    }

    public void getReplies() {

        replies.clear();
        replies.addAll(announcement.listReplies);
        replies.add(0, announcement.getAnnouncementInReplyForm());

        adapter.update();

    }

    public void buildActionBar() {
        ActionBar actionBar = getSupportActionBar();
        setTitle("   Announcement");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        String image = team.image;

        if (image != null)

            Picasso.with(this)
                    .load(U.getImageUrl(image))
                    .transform(new CircleTransform())
                    .resize( CircleTransform.dimen, CircleTransform.dimen)
                    .into(new ActionBarTarget(this, actionBar));

        else

            actionBar.setIcon(Images.TEAM);
    }


    public void enterReply(View view) {

        if (replyEditingId == -1) {
            sendReply();
        } else {
            editReply();
        }

    }

    @Override
    public void onEditReply(ListReply reply) {

        replyToSend.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        replyEditingId = reply.id;
        replyToSend.setText(reply.reply);
        replyButton.setImageResource(R.drawable.ic_tick_icon);

    }

    @Override
    public void onDeleteReply(final ListReply reply) {

        AlertDialog.Builder confirmation = new AlertDialog.Builder(this);
        confirmation.setMessage("Are you sure you want to delete this reply?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        deleteReply(reply);

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();

    }

    public void sendReply() {

        Identity identity = AppData.getCache().getIdentity();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                adapter.update();

            }
        };

        ListReply newReply = new ListReply();
        String replyMessage = replyToSend.getText().toString();
        newReply.reply = replyMessage;
        newReply.senderId = identity.getId();
        newReply.senderName = identity.getName();
        newReply.senderImage = identity.getImage();
        newReply.time = U.makeTime();

        replies.add(newReply);


        AppHandler.getInstance().teamHandler.reply(team.id, replyMessage, announcement.id, newReply, this, runnable);

        replyToSend.setText("");
    }

    public void editReply() {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                adapter.update();

            }
        };

        String newReplyMessage = replyToSend.getText().toString();

        for (ListReply reply : replies) {
            if (reply.id == replyEditingId)
                reply.reply = newReplyMessage;
        }

        AppHandler.getInstance().teamHandler.editAnnouncementReply(replyEditingId, newReplyMessage, this, runnable);

        replyEditingId = -1;
        replyButton.setImageResource(R.drawable.ic_send_icon);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(replyToSend.getWindowToken(), 0);
        replyToSend.setText("");

    }

    public void deleteReply(ListReply reply) {

        replies.remove(reply);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                adapter.update();

            }
        };

        AppHandler.getInstance().teamHandler.deleteReply(reply.id, this, runnable);

    }
}
