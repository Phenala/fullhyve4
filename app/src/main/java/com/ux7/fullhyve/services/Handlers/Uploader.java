package com.ux7.fullhyve.services.Handlers;


import android.app.Activity;
import android.content.Context;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;


import com.ux7.fullhyve.services.Models.Enclosure;
import com.ux7.fullhyve.services.Utility.Realtime;


import java.io.File;

public class Uploader {

  public static void uploadImage(Context context, String imagePath, final Enclosure<String> path, final Activity activity, final Runnable runnable){

      Ion.with(context)
         .load("POST", Realtime.URL + "file/upload")
         .setMultipartFile("image","image/jpg",new File(imagePath))
         .asJsonObject().setCallback(new FutureCallback<JsonObject>() {
                @Override
                public void onCompleted(Exception e, JsonObject result) {

                  path.value = result.get("path").getAsString();

                  activity.runOnUiThread(runnable);

                }
              });

  }
}
