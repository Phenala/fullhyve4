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
import android.widget.ProgressBar;

import com.squareup.picasso.Picasso;
import com.ux7.fullhyve.R;
import com.ux7.fullhyve.services.Handlers.AppHandler;
import com.ux7.fullhyve.services.Handlers.Uploader;
import com.ux7.fullhyve.services.Models.Enclosure;
import com.ux7.fullhyve.ui.fragments.TeamsListFragment;
import com.ux7.fullhyve.ui.util.CircleTransform;
import com.ux7.fullhyve.ui.util.U;

public class NewTeam extends AppCompatActivity {

    EditText name, focus, description;
    ProgressBar progressBar;
    ImageView imagePreview;

    String imageUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_team);

        buildViews();
        buildActionBar();
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

    public void buildViews() {

        name = findViewById(R.id.new_team_name);
        focus = findViewById(R.id.new_team_focus);
        description = findViewById(R.id.new_team_description);
        imagePreview = findViewById(R.id.new_team_image);
        progressBar = findViewById(R.id.new_image_upload_progress);

    }

    public void buildActionBar() {
        ActionBar actionBar = getSupportActionBar();
        setTitle("New Team");
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
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

    public void createTeam(View view) {

        Runnable runnable = new Runnable() {
            @Override
            public void run() {

                TeamsListFragment.get = true;
                finish();

            }
        };

        AppHandler.getInstance().teamHandler.newTeam(name.getText().toString(), imageUrl, focus.getText().toString(), description.getText().toString(), this, runnable);

    }

}
