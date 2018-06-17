package com.ux7.fullhyve.ui.activities;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.ux7.fullhyve.R;
import com.ux7.fullhyve.services.Handlers.AppHandler;
import com.ux7.fullhyve.ui.adapters.AddMemberRecyclerViewAdapter;
import com.ux7.fullhyve.ui.data.ListContact;
import com.ux7.fullhyve.ui.data.ListMember;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AddMemberView extends AppCompatActivity {

    List<ListMember> users;


    LinearLayoutManager layoutManager;
    AddMemberRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    SearchView searchView;
    Button sendButton;

    public enum AddUserType implements Serializable {

        FORWARD,
        INVITE_TO_TEAM,
        INVITE_TO_PROJECT,
        ASSIGN_TASK_TEAM,
        ASSIGN_TASK_USER

    }

    AddUserType addUserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        buildAddUserType();
        buildActionBar();
        buildRecyclerView();
        buildButton();

    }

    public void buildRecyclerView() {

        recyclerView = (RecyclerView)findViewById(R.id.add_member_list);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new AddMemberRecyclerViewAdapter(users);
        recyclerView.setAdapter(adapter);

    }

    public void buildButton() {

        sendButton = (Button) findViewById(R.id.add_user_button);

        switch (addUserType) {

            case FORWARD:
                sendButton.setText("Forward");
                break;

            default:
                sendButton.setText("Invite");
                break;

        }

    }

    public void buildAddUserType() {

        addUserType = (AddUserType)getIntent().getSerializableExtra("type");

    }

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_user_view, menu);

        searchView = (SearchView) ((MenuItem) menu.findItem(R.id.add_user_search)).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                searchUsers(s);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return true;
    }

    public void addUsers(View view) {

        Intent intent = new Intent();
        intent.putExtra("users", getSelectedUserIds());
        setResult(RESULT_OK, intent);
        finish();

    }

    public int[] getSelectedUserIds() {

        return ((AddMemberRecyclerViewAdapter)recyclerView.getAdapter()).getSelectedUserIds();

    }

    public void buildActionBar() {
        ActionBar actionBar = getSupportActionBar();

        switch (addUserType) {

            case FORWARD:
                setTitle("Forward to");
                break;

            default:
                setTitle("Add User");
                break;
        }
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }


    public void searchUsers(String searchTerm) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                adapter.update();

            }
        };

        AppHandler.getInstance().contactHandler.searchUsers(searchTerm, 0, 500, new ArrayList<ListContact>(), this, runnable);

    }


}
