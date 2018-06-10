package com.ux7.fullhyve.ui.activities;

import android.annotation.SuppressLint;
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
    String messageEditingId = "";
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_view);

        buildContact();
        buildActionBar();
        buildMessages();

    }

    public void buildContact() {
        contact.name = getIntent().getStringExtra("name");
        contact.image = getIntent().getStringExtra("image");
    }

    public void buildMessages() {

        recyclerView = (RecyclerView) findViewById(R.id.messages_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        (new GetMessagesTask()).execute();

        recyclerView.setAdapter(new MessagesRecyclerViewAdapter(messages, this));
        recyclerView.getLayoutManager().scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);
    }


    public void getMessages() {

        final MessagesRecyclerViewAdapter messagesRecyclerViewAdapter = (MessagesRecyclerViewAdapter)recyclerView.getAdapter();

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



    class GetMessagesTask extends AsyncTask<String, Integer, List<ListMessage>>
    {

        @Override
        protected List<ListMessage> doInBackground(String... strings) {

            //getMessageHandler
            Semaphore semaphore = new Semaphore(0);

//            ArrayList<ListMessage> nlist = new ArrayList<>();
//            //nlist.add(ListMessage.getSpinnerValue());
//            for (int i = 0; i < 2; i++) {
//                ListMessage l = new ListMessage();
//                l.sent = Math.random() > 0.5;
//                nlist.add(l);
//            }

            Socket socket = Realtime.getSocket();
            socket.connect();

            AppHandler appHandler = AppHandler.getInstance();

            appHandler.contactHandler.userConnected(1, new com.ux7.fullhyve.services.Utility.ResponseListener() {
                @Override
                public void call(Object... data) {
                    Log.e("userConnected", "sucss");
                }
            });

            appHandler.contactHandler.getMessages(2,0,50,messages, semaphore);

            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

//            messages.addAll(nlist);

            return messages;

        }

        @Override
        protected void onPostExecute(List<ListMessage> result) {

            ((MessagesRecyclerViewAdapter)recyclerView.getAdapter()).update();

        }
    };



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
        String message = ((EditText)findViewById(R.id.messageToSend)).getText().toString();

        //messageSendLogic
    }

    public void deleteMessage(String messageId) {

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
