package com.ux7.fullhyve.services.Utility;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.ux7.fullhyve.R;

public class TesterUpload extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {

    super.onCreate(savedInstanceState);
    setContentView(R.layout.testupload);
    Button button =(Button) findViewById(R.id.button4);
    button.setOnClickListener(new View.OnClickListener(){
      @Override
      public void onClick(View view) {
        //Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.putExtra("return-data", true);
        startActivityForResult(intent , 55);


      }
    });

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




        //Uploader.uploadImage(getBaseContext(),picturePath);
      }
    }
  }

}
