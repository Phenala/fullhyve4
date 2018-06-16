package com.ux7.fullhyve.ui.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
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
import com.ux7.fullhyve.ui.util.Images;
import com.ux7.fullhyve.ui.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public class ContactView extends AppCompatActivity implements MessagesRecyclerViewAdapter.OnMessageRecyclerInteractionListener {

    ListContact contact = new ListContact();
    List<ListMessage> messages = new ArrayList<>();

    boolean editing = false;
    int messageEditingId;
    String messageToSend = "";
    int messageForwardingId;
    int retrieveLimit = 10;
    int size = retrieveLimit;

    Activity activity = this;
    RecyclerView recyclerView;
    MessagesRecyclerViewAdapter adapter;
    LinearLayoutManager layoutManager;

    int j = 0;

    boolean fetchingMessages = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_view);

        buildContact();
        buildActionBar();
        buildMessages();

    }

    //Builder functions

    public void buildContact() {
        contact = (ListContact) getIntent().getSerializableExtra("contact");
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

                if (layoutManager.findLastVisibleItemPosition() == messages.size() - 1 && dy != 0) {

                    size += retrieveLimit;

                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            getMessages();
                        }
                    });

                }
            }
        });

        //recyclerView.getLayoutManager().scrollToPosition(recyclerView.getAdapter().getItemCount() - 1);

    }


    public void getMessages() {

        if (!fetchingMessages) {

            fetchingMessages = true;

            ListMessage spinnerMessage = new ListMessage();
            spinnerMessage.spinner = true;
            messages.add(spinnerMessage);

            final Runnable runnable = new Runnable() {
                @Override
                public void run() {

//                    for (int i = 0; i < 5; i++) {
//
//                        ListMessage x = new ListMessage();
//                        x.sent = (Math.random() > 0.5d);
//                        x.message = j + " coconuts";
//                        j++;
//                        messages.add(x);
//
//                    }
//
//                    for (int i = 0; i < messages.size(); i++) {
//
//                        if (messages.get(i).spinner == true) {
//
//                            messages.remove(messages.get(i));
//
//                        }
//
//                    }

                    ((MessagesRecyclerViewAdapter)recyclerView.getAdapter()).update();

                    updateSeen();

                    fetchingMessages = false;
                }
            };

            adapter.update();

            AppHandler.getInstance().contactHandler.getMessages(contact.id, 0, size, messages, activity, runnable);
//            activity.runOnUiThread(runnable);

        }


    }

    public void buildActionBar() {
        final ActionBar actionBar = getSupportActionBar();
        setTitle("   " + contact.name);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        if (contact.image != null)

            Picasso.with(this)
                    .load(Util.getImageUrl(contact.image))
                    .transform(new CircleTransform())
                    .resize(96,96)
                    .into(new ActionBarTarget(this, actionBar));

        else

            actionBar.setIcon(Images.USER);
            actionBar.setDisplayShowHomeEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);


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

        messageForwardingId = message.id;

        Intent intent = new Intent(this, AddMember.class);
        intent.putExtra("type", AddMember.AddUserType.FORWARD);
        startActivityForResult(intent, 11);

    }

    @Override
    public void onEditMessage(View view, ListMessage message) {

        EditText messageEditor = ((EditText)findViewById(R.id.messageToSend));

        messageEditor.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
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

           forwardMessage(messageForwardingId, data.getIntArrayExtra("users"));

        }

    }

    public void updateSeen() {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {



            }
        };

        if (messages.size() > 0)

            AppHandler.getInstance().contactHandler.updateMessageSeen(messages.get(0).id, this, runnable);

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


//    class GetMessagesTask extends AsyncTask<String, Integer, List<ListMessage>>
//    {
//
//        @Override
//        protected void onPreExecute() {
//
//            fetchingMessages = true;
//
//            int offset = layoutManager.findFirstVisibleItemPosition();
//
//            ListMessage spinnerMessage = new ListMessage();
//            spinnerMessage.spinner = true;
//            messages.add(spinnerMessage);
//            adapter.update();
//
//            Runnable runnable = new Runnable() {
//                @Override
//                public void run() {
//
//                    for (int i = 0; i < messages.size(); i++) {
//
//                        if (messages.get(i).spinner == true) {
//
//                            messages.remove(messages.get(i));
//
//                        }
//
//                    }
//
//                    ((MessagesRecyclerViewAdapter)recyclerView.getAdapter()).update();
//
//                    fetchingMessages = false;
//                }
//            };
//
//
//
//        }

//        @Override
//        protected List<ListMessage> doInBackground(String... strings) {
//
//            //getMessageHandler
//            Semaphore semaphore = new Semaphore(0);
//
//
//
////            appHandler.contactHandler.getMessages(2,0,50, messages, semaphore);
////
////            try {
////                semaphore.acquire();
////            } catch (InterruptedException e) {
////                e.printStackTrace();
////            }
//
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            for (int i = 0; i < 5; i++) {
//
//                ListMessage x = new ListMessage();
//                x.sent = (Math.random() > 0.5d);
//                x.message = j + " coconuts";
//                j++;
//                messages.add(x);
//
//            }
//
////            messages.addAll(nlist);
//
//            return messages;
//
//        }
//
//        @Override
//        protected void onPostExecute(List<ListMessage> result) {
//
//
//
//
//        }
//    };


//    class SendMessageTask extends AsyncTask<String, Integer, String> {
//
//        @Override
//        protected void onPreExecute() {
//
//            ListMessage newMessage = new ListMessage();
//
//            newMessage.id = -1;
//            newMessage.message = messageToSend;
//
//            messages.add(0, newMessage);
//
//            adapter.update();
//
//        }
//
//        @Override
//        protected String doInBackground(String... strings) {
//
//            Semaphore semaphore = new Semaphore(0);
//
//            appHandler.contactHandler.sendMessage(contact.id, messageToSend, semaphore);
//
//            try {
//                Thread.sleep(10000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//            return null;
//        }
//    }



    public void enterMessage(View view) {

        if (editing) {
            editMessage();
        } else {
            sendMessage();
        }

        ((EditText)findViewById(R.id.messageToSend)).setText("");

    }


    public void sendMessage() {
        messageToSend = ((EditText)findViewById(R.id.messageToSend)).getText().toString();

        ListMessage newMessage = new ListMessage();
        newMessage.id = -1;
        newMessage.message = messageToSend;
        messages.add(0, newMessage);

        adapter.update();

        Runnable runnable = new Runnable() {

            @Override
            public void run() {

                getMessages();

            }
        };

        AppHandler.getInstance().contactHandler.sendMessage(contact.id, messageToSend, activity, runnable);

    }

    public void deleteMessage(final int messageId) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                for (int i = 0; i < messages.size(); i++) {

                    if (messages.get(i).id == messageId)

                        messages.remove(i);

                }

                adapter.update();

            }
        };

        AppHandler.getInstance().contactHandler.deleteMessage(messageId, this, runnable);

        //messageDeleteLogic

    }

    public void editMessage() {

        messageToSend = ((EditText)findViewById(R.id.messageToSend)).getText().toString();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                for (ListMessage message : messages) {
                    if (message.id == messageEditingId) {

                        message.message = messageToSend;

                    }
                }

                messageEditingId = -1;

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(((EditText)findViewById(R.id.messageToSend)).getWindowToken(), 0);

                adapter.update();
            }
        };

        AppHandler.getInstance().contactHandler.editMessage(messageEditingId, messageToSend, this, runnable);

        setMessageEditMode(false);

        //messageEditLogic

    }

    public void forwardMessage(int messageId, int[] receiverIds) {

        //messageForwardLogic

        Runnable runnable = new Runnable() {
            @Override
            public void run() {



            }
        };

        AppHandler.getInstance().contactHandler.forwardMessage(receiverIds, messageId, this, runnable);


    }
}
