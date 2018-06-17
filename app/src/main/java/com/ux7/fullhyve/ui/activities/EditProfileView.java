package com.ux7.fullhyve.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;
import com.ux7.fullhyve.R;
import com.ux7.fullhyve.services.Handlers.AppHandler;
import com.ux7.fullhyve.services.Handlers.Uploader;
import com.ux7.fullhyve.services.Models.Enclosure;
import com.ux7.fullhyve.services.Models.Identity;
import com.ux7.fullhyve.services.Storage.AppData;
import com.ux7.fullhyve.ui.util.CircleTransform;
import com.ux7.fullhyve.ui.util.U;

public class EditProfileView extends AppCompatActivity {

    EditText firstName, lastName, title, bio;
    MultiAutoCompleteTextView skills;
    ImageView imagePreview;
    ProgressBar progressBar;
    String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile_view);

        buildActionBar();
        getIdentity();
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
        actionBar.setTitle("Edit Profile");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

    }

    public void updateProfile(View view) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                HomeView.updateUserNavigation = true;
                finish();

            }
        };

        Identity identity = AppData.getCache().getIdentity();

        identity.setFirstName(firstName.getText().toString());
        identity.setLastName(lastName.getText().toString());
        identity.setTitle(title.getText().toString());
        identity.setDescription(bio.getText().toString());
        identity.setSkills(skills.getText().toString().split(", "));
        identity.setImage(imageUrl);

        AppHandler.getInstance().loginHandler.editProfile(identity, this, runnable);

    }

    public void getIdentity() {

        Identity identity = AppData.getCache().getIdentity();

        firstName = findViewById(R.id.edit_profile_first_name);
        lastName = findViewById(R.id.edit_profile_last_name);
        title = findViewById(R.id.edit_profile_title);
        bio = findViewById(R.id.edit_profile_bio);
        skills = findViewById(R.id.edit_profile_skills);
        imagePreview = findViewById(R.id.edit_profile_upload_image);
        progressBar = findViewById(R.id.new_image_upload_progress);

        firstName.setText(identity.getFirstName());
        lastName.setText(identity.getLastName());
        title.setText(identity.getTitle());
        bio.setText(identity.getDescription());
        String skillString = "";
        for (int i = 0; i < identity.getSkills().length - 1; i++) {
            skillString += ", " + identity.getSkills()[i];
        }
        if (skillString.length() > 2) {
            skillString = skillString.substring(2);
        }
        imageUrl = identity.getImage();
        Picasso.with(this)
                .load(U.getImageUrl(identity.getImage()))
                .transform(new CircleTransform())
                .into(imagePreview);
        skills.setText(skillString);

    }

    public void getImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.putExtra("return-data", true);
        startActivityForResult(intent , 55);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent resultData) {
        if (requestCode ==55 && resultCode == Activity.RESULT_OK) {
            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();

                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getContentResolver().query(uri,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();
                Log.e("FilePicker",picturePath);

                final Enclosure<String> path = new Enclosure<>("");

                progressBar.setVisibility(View.VISIBLE);

                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {

                        progressBar.setVisibility(View.INVISIBLE);
                        imageUrl = path.value;
                        Picasso.with(getBaseContext())
                                .load(U.getImageUrl(path.value))
                                .transform(new CircleTransform())
                                .into(imagePreview);

                    }
                };

                Uploader.uploadImage(getBaseContext(),picturePath, path, this, runnable);
            }
        }
    }

}
