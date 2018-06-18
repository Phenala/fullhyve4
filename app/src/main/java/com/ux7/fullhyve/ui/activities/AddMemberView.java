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

public class AddMemberView extends AppCompatActivity implements AddMemberRecyclerViewAdapter.OnAddMemberInteractionListener{

    List<ListMember> users = new ArrayList<>();



    LinearLayoutManager layoutManager;
    AddMemberRecyclerViewAdapter adapter;
    RecyclerView recyclerView;
    SearchView searchView;
    Button sendButton;

    @Override
    public void onUnselectOthers(Runnable runnable) {

        recyclerView.post(runnable);

    }

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

        adapter = new AddMemberRecyclerViewAdapter(users, addUserType, this);
        recyclerView.setAdapter(adapter);

    }

    public void buildButton() {

        sendButton = (Button) findViewById(R.id.add_user_button);

        switch (addUserType) {

            case FORWARD:
                sendButton.setText("Forward");
                break;

            case ASSIGN_TASK_TEAM:
                sendButton.setText("Assign");
                break;

            case ASSIGN_TASK_USER:
                sendButton.setText("Assign");
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


                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                searchUsers(s);
                return false;
            }
        });

        return true;
    }

    public void addUsers(View view) {

        Intent intent = new Intent();
        intent.putExtra("users", adapter.getSelectedUsers().toArray());
        intent.putExtra("teams", adapter.getSelectedTeams().toArray());
        intent.putExtra("user", adapter.getSelectedUser());
        intent.putExtra("team", adapter.getSelectedTeam());
        setResult(RESULT_OK, intent);
        finish();

    }

    public void buildActionBar() {
        ActionBar actionBar = getSupportActionBar();

        switch (addUserType) {

            case FORWARD:
                setTitle("Forward to");
                break;

            case ASSIGN_TASK_TEAM:
                setTitle("Select team");
                break;

            case ASSIGN_TASK_USER:
                setTitle("Select User");
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

        if (addUserType == AddUserType.ASSIGN_TASK_USER) {

            AppHandler.getInstance().contactHandler.searchAddUsers(searchTerm, 0, 500, users, this, runnable);

        } else if (addUserType == AddUserType.ASSIGN_TASK_TEAM) {

            AppHandler.getInstance().teamHandler.searchAddTeams(0,500, searchTerm, users, this, runnable);

        } else {
            AppHandler.getInstance().contactHandler.searchAddUsers(searchTerm, 0, 500, users, this, runnable);
        }

    }


}
