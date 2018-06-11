package com.ux7.fullhyve.ui.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.github.nkzawa.socketio.client.Socket;
import com.squareup.picasso.Picasso;
import com.ux7.fullhyve.R;
import com.ux7.fullhyve.services.Handlers.AppHandler;
import com.ux7.fullhyve.services.Utility.Realtime;
import com.ux7.fullhyve.ui.adapters.MessagesRecyclerViewAdapter;
import com.ux7.fullhyve.ui.data.ListContact;
import com.ux7.fullhyve.ui.data.ListMessage;
import com.ux7.fullhyve.ui.interfaces.ResponseListener;
import com.ux7.fullhyve.ui.util.ActionBarTarget;
import com.ux7.fullhyve.ui.util.CircleTransform;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class ContactView extends AppCompatActivity implements MessagesRecyclerViewAdapter.OnMessageRecyclerInteractionListener {

    ListContact contact = new ListContact();
    List<ListMessage> messages = new ArrayList<>();
    boolean editing = false;
    int messageEditingId;
    String messageToSend = "";
    AppHandler appHandler;

    RecyclerView recyclerView;
    MessagesRecyclerViewAdapter adapter;
    LinearLayoutManager layoutManager;

    int j = 0;

    boolean fetchingMessages = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_view);

        appHandler =  AppHandler.getInstance();
        Socket socket = Realtime.getSocket();
        socket.connect();


        appHandler.contactHandler.userConnected(1, new com.ux7.fullhyve.services.Utility.ResponseListener() {
            @Override
            public void call(Object... data) {
                Log.e("userConnected", "sucss");
            }
        });

        buildContact();
        buildActionBar();
        buildMessages();

    }

    //Builder functions

    public void buildContact() {
        contact.name = getIntent().getStringExtra("name");
        contact.image = getIntent().getStringExtra("image");
    }

    public void buildMessages() {

        recyclerView = (RecyclerView) findViewById(R.id.messages_view);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MessagesRecyclerViewAdapter(messages, this);
        recyclerView.setAdapter(adapter);

        getMessages();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                Log.e("Yoo", layoutManager.findFirstVisibleItemPosition() + "");


                RecyclerView.State x = new RecyclerView.State();
                layoutManager.computeVerticalScrollOffset(x);
                Log.e("Yaka", "" + x.getRemainingScrollVertical());

                if (layoutManager.findLastVisibleItemPosition() == messages.size() - 1) {

                    getMessages();

                }
            }
        });


        recyclerView.getLayoutManager().scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);

    }


    public void getMessages() {

        if (!fetchingMessages)
            (new GetMessagesTask()).execute();

//        ResponseListener listener = new ResponseListener() {
//            @Override
//            public void call(final Object... args) {
//                Handler handler = new Handler(Looper.getMainLooper());
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        handler.
//
//                    }
//                });
//
//
//
//
//            }
//        };


    }

    public void buildActionBar() {
        final ActionBar actionBar = getSupportActionBar();
        setTitle("   " + contact.name);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);
        Picasso.with(this)
                .load(contact.image)
                .transform(new CircleTransform())
                .into(new ActionBarTarget(getResources(), actionBar));
    }


    //Activity functions

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {

            case android.R.id.home:

                goBack();
                return false;

            default:
                return false;

        }
    }

    public void goBack() {
        finish();
    }

    @Override
    public void onForwardMessage(View view, ListMessage message) {

        Intent intent = new Intent(this, AddMember.class);
        intent.putExtra("type", AddMember.AddUserType.FORWARD);
        startActivityForResult(intent, 11);

    }

    @Override
    public void onEditMessage(View view, ListMessage message) {

        EditText messageEditor = ((EditText)findViewById(R.id.messageToSend));

        messageEditor.setText(message.message);
        messageEditingId = message.id;
        setMessageEditMode(true);

    }

    public void setMessageEditMode(boolean state) {

        editing = state;
        ImageButton button = ((ImageButton)findViewById(R.id.messageSendButton));

        if (state) {
            button.setImageResource(R.drawable.ic_tick_icon);
        } else {
            button.setImageResource(R.drawable.ic_send_icon);
        }

    }


    public void onActivityResult(int requestCode, int resultCode, Intent data)  {

        if (resultCode == RESULT_OK && requestCode == 11) {

            Toast.makeText(this, data.getStringArrayExtra("users").length + "", Toast.LENGTH_LONG).show();

        }

    }

    @Override
    public void onDeleteMessage(View view, final ListMessage message) {

        AlertDialog.Builder confirmation = new AlertDialog.Builder(this);
        confirmation.setMessage("Are you sure you want to delete this message?")
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        deleteMessage(message.id);

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();

    }


    //Async Tasks


    class GetMessagesTask extends AsyncTask<String, Integer, List<ListMessage>>
    {

        @Override
        protected void onPreExecute() {

            fetchingMessages = true;

            int offset = layoutManager.findFirstVisibleItemPosition();

            ListMessage spinnerMessage = new ListMessage();
            spinnerMessage.spinner = true;
            messages.add(spinnerMessage);
            adapter.update();

        }

        @Override
        protected List<ListMessage> doInBackground(String... strings) {

            //getMessageHandler
            Semaphore semaphore = new Semaphore(0);



//            appHandler.contactHandler.getMessages(2,0,50, messages, semaphore);
//
//            try {
//                semaphore.acquire();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }

            for (int i = 0; i < 5; i++) {

                ListMessage x = new ListMessage();
                x.sent = (Math.random() > 0.5d);
                x.message = j + " coconuts";
                j++;
                messages.add(x);

            }

//            messages.addAll(nlist);

            return messages;

        }

        @Override
        protected void onPostExecute(List<ListMessage> result) {

            for (int i = 0; i < messages.size(); i++) {

                if (messages.get(i).spinner == true) {

                    messages.remove(messages.get(i));

                }

            }

            ((MessagesRecyclerViewAdapter)recyclerView.getAdapter()).update();

            fetchingMessages = false;


        }
    };


    class SendMessageTask extends AsyncTask<String, Integer, String> {

        @Override
        protected void onPreExecute() {

            ListMessage newMessage = new ListMessage();

            newMessage.id = -1;
            newMessage.message = messageToSend;

            messages.add(0, newMessage);

            adapter.update();

        }

        @Override
        protected String doInBackground(String... strings) {

            Semaphore semaphore = new Semaphore(0);

            appHandler.contactHandler.sendMessage(contact.id, messageToSend, semaphore);

            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }
    }



    public void enterMessage(View view) {

        if (editing) {
            editMessage();
            setMessageEditMode(false);
        } else {
            sendMessage();
        }

        ((EditText)findViewById(R.id.messageToSend)).setText("");

    }


    public void sendMessage() {
        messageToSend = ((EditText)findViewById(R.id.messageToSend)).getText().toString();

        (new SendMessageTask()).execute();

        //messageSendLogic
    }

    public void deleteMessage(int messageId) {

        //messageDeleteLogic

    }

    public void editMessage() {
        String message = ((EditText)findViewById(R.id.messageToSend)).getText().toString();

        //messageEditLogic

    }

    public void forwardMessage(String message, String[] receiverIds) {

        //messageForwardLogic

    }
}
