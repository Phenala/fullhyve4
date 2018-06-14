package com.ux7.fullhyve.ui.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.squareup.picasso.Picasso;
import com.ux7.fullhyve.R;
import com.ux7.fullhyve.ui.adapters.ReplyRecyclerViewAdapter;
import com.ux7.fullhyve.ui.data.ListAnnouncement;
import com.ux7.fullhyve.ui.data.ListReply;
import com.ux7.fullhyve.ui.util.ActionBarTarget;
import com.ux7.fullhyve.ui.util.CircleTransform;

import java.util.ArrayList;

public class AnnouncementView extends AppCompatActivity {

    ListAnnouncement announcement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcement_view);

        buildActionBar();
        buildReplies();

    }

    public void buildAnnouncement() {

    }

    public void buildReplies() {

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.reply_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        ArrayList<ListReply> nlist = new ArrayList<>();
        nlist.add((new ListAnnouncement()).getAnnouncementInReplyForm());
        //nlist.add(ListMessage.getSpinnerValue());
        for (int i = 0; i < 20; i++) {
            ListReply l = new ListReply();
            nlist.add(l);
        }

        recyclerView.setAdapter(new ReplyRecyclerViewAdapter(nlist));
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

    public void buildActionBar() {
        ActionBar actionBar = getSupportActionBar();
        setTitle("   Announcement");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        Picasso.with(this)
                .load(getIntent().getStringExtra("image"))
                .transform(new CircleTransform())
                .resize( CircleTransform.dimen, CircleTransform.dimen)
                .into(new ActionBarTarget(this, actionBar));
    }


}
