package com.ux7.fullhyve.services;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.ux7.fullhyve.R;
import com.ux7.fullhyve.services.Handlers.AppHandler;
import com.ux7.fullhyve.services.Handlers.LoginHandler;
import com.ux7.fullhyve.services.Models.Identity;
import com.ux7.fullhyve.services.Models.Notification;
import com.ux7.fullhyve.services.Storage.AppData;
import com.ux7.fullhyve.services.Utility.Realtime;
import com.ux7.fullhyve.services.Utility.ResponseListener;
import com.github.nkzawa.socketio.client.Socket;
import com.ux7.fullhyve.ui.activities.LoginView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static AppData.Cache cache;

    public static Socket socket = Realtime.getSocket();

    public static LoginHandler loginHandler;
    public static AppHandler appHandler;
    //private final android.os.Handler mainThreadHandler = new android.os.Handler(Looper.getMainLooper());

    private static EditText arg1, arg2, arg3, arg4, userNameTxt, passwordTxt;
    private static Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cache = AppData.getCache();
        loginHandler = new LoginHandler();
        appHandler = AppHandler.getInstance();

        Log.e("On view page",cache.getToken()==null?"No token":cache.getToken());
        // set the list of actions to the spinner
        spinner = findViewById(R.id.spinner);
        String[] acts = {"Show token","Show identity","Show notifications",
                "User connected","Signup","Add friend","Reply friend request",
                "Unfriend","Get notifications", "Get profile","Sign-out", "Edit profile",
                "Get messages", "Get friends"};

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(this, android.R.layout.simple_spinner_item,acts);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);
        //--------------------------------------------

        userNameTxt = findViewById(R.id.userName);
        passwordTxt = findViewById(R.id.password);


        // login button
        final Button login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(view);

            }
        });

        arg1 = findViewById(R.id.arg_1);
        arg2 = findViewById(R.id.arg_2);
        arg3 = findViewById(R.id.arg_3);
        arg4 = findViewById(R.id.arg_4);


        // execute button
        Button execute = findViewById(R.id.execute);

        execute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testAccount(view);

            }
        });



//        Button registerBtn = findViewById(R.id.button4);
//        registerBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                EditText userId = findViewById(R.id.editText5);
//                JSONObject jsonObject = new JSONObject();
//                try{
//                    jsonObject.put("id",userId.getText().toString());
//                } catch (Exception e){
//
//                }
//                Integer usrId = Integer.parseInt(userId.getText().toString());
//
//                appHandler.contactHandler.userConnected(usrId, new ResponseListener() {
//                    @Override
//                    public void call(Object... data) {
//                        mainThreadHandler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(getApplicationContext(),"Connected",Toast.LENGTH_LONG).show();
//                            }
//                        });
//
//                    }
//                });
//            }
//        });

    }

    public void testAccount(View view){
        Log.e("Selected item",spinner.getSelectedItem().toString());
        switch (spinner.getSelectedItem().toString()){
            case "Show token":
                Log.e("Token",cache.getToken()==null?"No token":cache.getToken());

                break;

            case "Show identity":
                Log.e("Identity",cache.getIdentity().toString());

                break;

            case "Show notifications":
                ArrayList<Notification> nots = cache.getNotifications().notifications;
                if(nots.size() > 0) {
                    for (Notification not : nots) {
                        Log.e("Notification", not.toString());
                    }
                }

                break;

            case "User connected":
                loginHandler.userConnected(this, new Runnable() {
                    @Override
                    public void run() {
                        Log.e("status", "Connected now online!!");
                    }
                });
                break;

            case "Sign-out":
                loginHandler.signout(this, new Runnable() {
                    @Override
                    public void run() {
                        Log.e("status", "Signed out!!");
                    }
                });
                break;

            case "Signup":
                String fName = arg1.getText().toString();
                String lName = arg2.getText().toString();
                String userName = arg3.getText().toString();
                String password = arg4.getText().toString();
                String email = "samuel@yahoo.com";

                loginHandler.signup(fName, lName, email, userName, password, this, new Runnable() {
                    @Override
                    public void run() {
                        Log.e("Status","New account created");
                    }
                });

                break;

            case "Add friend":
                Integer friendId = Integer.parseInt(arg1.getText().toString());
                loginHandler.addFriend(friendId, this, new Runnable() {
                    @Override
                    public void run() {
                        Log.e("Status","Friend request sent");
                    }
                });
                break;

            case "Reply friend request":
                Integer requestId = Integer.parseInt(arg1.getText().toString());

                loginHandler.replyFriendRequest(requestId, true, this, new Runnable() {
                    @Override
                    public void run() {
                        Log.e("Status","Friend request accepted");
                    }
                });
                break;



            case "Unfriend":
                Integer friendId1 = Integer.parseInt(arg1.getText().toString());

                loginHandler.unfriend(friendId1, this, new Runnable() {
                    @Override
                    public void run() {
                        Log.e("Status","Friend removed");
                    }
                });
                break;

            case "Edit profile":
                Identity identity = new Identity();
                identity.setFirstName(arg1.getText().toString());
                identity.setLastName(arg2.getText().toString());
                identity.setEmail(arg3.getText().toString());
                identity.setDescription(arg4.getText().toString());
                identity.setUserName(cache.getIdentity().getUserName());
                identity.setSkills(cache.getIdentity().getSkills());
                identity.setImage("new image");
                identity.setTitle("Software engineer");

                loginHandler.editProfile(identity, this, new Runnable() {
                    @Override
                    public void run() {
                        Log.e("Status","Profile modified");
                    }
                });
                break;

            case "Get notifications":
                loginHandler.getNotifications("", 0, 10, this, new Runnable() {
                    @Override
                    public void run() {
                        Log.e("Status","Notification retrieved");
                    }
                });
                break;

            case "Get profile":
                loginHandler.getProfile(this, new Runnable() {
                    @Override
                    public void run() {
                        Log.e("Profile",cache.getIdentity().toString());
                    }
                });
                break;


            case "Get messages":
                Integer friendId2 = Integer.parseInt(arg1.getText().toString());
                appHandler.contactHandler.getMessages(friendId2,0,10, this, new Runnable() {
                    @Override
                    public void run() {
                        Log.e("Profile",cache.getIdentity().toString());
                    }
                });
                break;

            case "Get friends":
                appHandler.contactHandler.getFriendsFromServer(0,10, this, new Runnable() {
                    @Override
                    public void run() {
                        Log.e("Friends","Successfully fetched");
                    }
                });
                break;

            default:
                Log.e("Action", "Unknown");
        }
    }

    public void login(View view){
        String userName = userNameTxt.getText().toString();
        String password = passwordTxt.getText().toString();


        appHandler.loginHandler.signin(userName, password, this, new Runnable() {
            @Override
            public void run() {
                Log.e("Status","Successfully loggedin");
            }
        });
    }



//    public void update(){
//        EditText editText2 = findViewById(R.id.editText2);
//
//        editText2.setText(cache.getToken());
//        //socket.emit("socketDetail");
//    }
//
//    public void r(){
//        Activity activity = this;
//        activity.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
////                update();
//                EditText editText2 = findViewById(R.id.editText2);
//
//                editText2.setText(AppData.userToken);
//                //Toast.makeText(this,"",Toast.LENGTH_LONG).show();
//            }
//        });
//    }
}

