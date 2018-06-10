package com.ux7.fullhyve.ui.activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.ux7.fullhyve.R;
import com.ux7.fullhyve.ui.data.UserDetail;
import com.ux7.fullhyve.ui.util.ActionBarTarget;
import com.ux7.fullhyve.ui.util.CircleTransform;

public class UserView extends AppCompatActivity {

    UserDetail userDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view);

        buildUserDetail();
        buildActionBar();
    }

    public void buildUserDetail() {
        userDetail = (UserDetail)(getIntent().getSerializableExtra("user"));
        ((TextView)findViewById(R.id.user_name)).setText(userDetail.name);
        ((TextView)findViewById(R.id.user_title)).setText(userDetail.title);
        ((TextView)findViewById(R.id.user_bio)).setText(userDetail.bio);

        String skillString = "";
        for (int i = 0; i < userDetail.skills.length; i++) {
            skillString += userDetail.skills[i] + ((i == userDetail.skills.length - 1)? "" : ", ");
        }
        ((TextView)findViewById(R.id.user_skills)).setText(skillString);

        Picasso.with(this)
                .load(userDetail.image)
                .transform(new CircleTransform())
                .into((ImageView)findViewById(R.id.user_picture));

        Button button = ((Button)findViewById(R.id.user_button));

        if (userDetail.friends) {
            button.setText("Send Message");
        } else {
            button.setText("Request Friendship");
        }

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
        setTitle("   " + userDetail.name);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        Picasso.with(this)
                .load(userDetail.image)
                .transform(new CircleTransform())
                .resize(64, 64)
                .into(new ActionBarTarget(getResources(), actionBar));
    }
}
