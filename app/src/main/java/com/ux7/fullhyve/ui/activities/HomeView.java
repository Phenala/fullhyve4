package com.ux7.fullhyve.ui.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ux7.fullhyve.R;
import com.ux7.fullhyve.services.Handlers.AppHandler;
import com.ux7.fullhyve.services.Models.Identity;
import com.ux7.fullhyve.services.Storage.AppData;
import com.ux7.fullhyve.services.Utility.Realtime;
import com.ux7.fullhyve.ui.data.ListContact;
import com.ux7.fullhyve.ui.data.ListProject;
import com.ux7.fullhyve.ui.data.ListTeam;
import com.ux7.fullhyve.ui.fragments.ContactsListFragment;
import com.ux7.fullhyve.ui.fragments.NotificationFragment;
import com.ux7.fullhyve.ui.fragments.ProjectsListFragment;
import com.ux7.fullhyve.ui.fragments.TeamsListFragment;
import com.ux7.fullhyve.ui.interfaces.OnHomeInteractionListener;
import com.ux7.fullhyve.ui.util.CircleTransform;
import com.ux7.fullhyve.ui.util.U;

public class HomeView extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnHomeInteractionListener {

    ContactsListFragment contactsListFragment = new ContactsListFragment();
    TeamsListFragment teamsListFragment = new TeamsListFragment();
    ProjectsListFragment projectsListFragment = new ProjectsListFragment();
    NotificationFragment notificationFragment = new NotificationFragment();

    SearchView searchView;
    NavigationView navigationView;
    FloatingActionButton fab;
    View.OnClickListener addTeam;
    View.OnClickListener addProject;
    /*Counting Idling Resource*/
     CountingIdlingResource idlingResource=new CountingIdlingResource("home");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initApp();

        checkRedirect();

        setContentView(R.layout.activity_home_view);

        buildNavigation();

        initializeFloatingActionButton();
        initializeAdders();
        idlingResource.increment();
    }

    public void initApp() {

        Realtime.getSocket();

    }


    public void buildNavigation() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        ImageView userPicture = (ImageView)(((NavigationView)findViewById(R.id.nav_view)).getHeaderView(0).findViewById(R.id.userPicture));
        Picasso.with(this).load(R.mipmap.user_picture_round).into(userPicture);

        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contactsListFragment).commit();


        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        updateUserImage();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {

            drawer.closeDrawer(GravityCompat.START);

        } else {

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_view, menu);

        searchView = (SearchView) ((MenuItem) menu.findItem(R.id.app_bar_search)).getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {

                searchUsers();

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });


        return true;
    }

    public void searchUsers() {



    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void initializeAdders() {
        addTeam = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), NewTeam.class);
                startActivity(intent);
            }
        };
        addProject = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), NewProject.class);
                startActivity(intent);
            }
        };
    }

    public void updateUserImage() {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                Identity identity = AppData.getCache().getIdentity();
                ((TextView)navigationView.findViewById(R.id.profile_identity_name)).setText(identity.getFirstName() + " " + identity.getLastName());

                Log.e("Picture", identity.getImage());

                Picasso.with(getBaseContext())
                        .load(U.getImageUrl(identity.getImage()))
                        .transform(new CircleTransform())
                        .into((ImageView)navigationView.findViewById(R.id.userPicture));

            }
        };

        AppHandler.getInstance().loginHandler.getProfile(this, runnable);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_notifications) {
            setTitle("Notifications");
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, notificationFragment).commit();
            fab.hide();
        } else if (id == R.id.nav_chat) {
            setTitle("Chat");
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contactsListFragment).commit();
            fab.hide();
        } else if (id == R.id.nav_teams) {
            setTitle("Teams");
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, teamsListFragment).commit();
            fab.show();
            fab.setOnClickListener(addTeam);
        } else if (id == R.id.nav_projects) {
            setTitle("Projects");
            getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, projectsListFragment).commit();
            fab.show();
            fab.setOnClickListener(addProject);
        } else if (id == R.id.nav_edit_profile) {

            editProfile();

        } else if (id == R.id.nav_log_out) {

            logoutConfirmation();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void initializeFloatingActionButton() {

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fab.hide();
    }

    public void checkRedirect() {

        if (!isLoggedIn()) {

            Intent intent = new Intent(this, LoginView.class);
            startActivity(intent);

        }

    }

    public boolean isLoggedIn() {

        return AppData.getCache().getToken() != null;

    }

    public void editProfile() {

        Intent intent = new Intent(this, EditProfileView.class);
        startActivity(intent);

    }


    public void logoutConfirmation() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Are you sure you want to log out?")
                .setPositiveButton("Log Out", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        logout();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();

    }

    @Override
    public void onContactListFragmentInteraction(ListContact item) {

    }

    @Override
    public void onTeamListFragmentInteraction(ListTeam team) {

    }

    @Override
    public void onProjectListFragmentInteraction(ListProject project) {

    }

    @Override
    public void onNotificationFragmentInteraction(ListProject project) {

    }

    @Override
    public void onStartNewActivity(Intent intent) {
        startActivity(intent);
    }

    @Override
    public Context getHomeContext() {
        return this;
    }


    public void logout() {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                checkRedirect();

            }
        };

        AppHandler.getInstance().loginHandler.signout(this, runnable);

    }


}
