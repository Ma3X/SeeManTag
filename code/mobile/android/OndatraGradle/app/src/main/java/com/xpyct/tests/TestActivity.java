package com.xpyct.tests;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.xpyct.ondatra.DbLite;
import com.xpyct.ondatra.R;

import java.io.IOException;
import java.util.ArrayList;

// http://developer.android.com/training/sharing/receive.html

public class TestActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        // Get intent, action and MIME type
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                handleSendText(intent); // Handle text being sent
            } else if (type.startsWith("image/")) {
                handleSendImage(intent); // Handle single image being sent
            }
        } else if (Intent.ACTION_SEND_MULTIPLE.equals(action) && type != null) {
            if (type.startsWith("image/")) {
                handleSendMultipleImages(intent); // Handle multiple images being sent
            }
        } else {
            // Handle other intents, such as being started from the home screen
        }
    }

    void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            // Update UI to reflect text being shared
            TextView tvL = (TextView) findViewById(R.id.tvLarge);
            tvL.setText(sharedText);

            final Button btnPost = (Button) findViewById(R.id.btPost);
            btnPost.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    TextView tvL = (TextView) findViewById(R.id.tvLarge);
                    String tvS = tvL.getText().toString();
                    tvS = tvS.replace("\"", "\"\"");
                    tvS = tvS.replace("\'", "\'\'");
                    Context ctx = getApplicationContext();

                    try {
                        DbLite dbs = new DbLite(ctx);
                        dbs.create();
                        dbs.insert(tvS);
                        String info = tvS + "\nsaved in: " + dbs._dbPath;

                        Toast toast = Toast.makeText(ctx, info, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

            final Button btnSync = (Button) findViewById(R.id.btSync);
            btnSync.setOnClickListener(new Button.OnClickListener() {
                public void onClick(View v) {
                    TextView tvL = (TextView) findViewById(R.id.tvLarge);
                    String tvS = tvL.getText().toString();
                    tvS = tvS.replace("\"", "\"\"");
                    tvS = tvS.replace("\'", "\'\'");

                    Context ctx = getApplicationContext();
                    try {
                        DbLite dbs = new DbLite(ctx);

                        ////HWClient.main(["test"]);
                        ////new HWClient().getInfo("test");
                        //new RetrieveInfoTask(this).execute(dbs.getText(), tvS);
                        String info = "Test sync.";
                        Toast toast = Toast.makeText(ctx, info, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    void handleSendImage(Intent intent) {
        Uri imageUri = (Uri) intent.getParcelableExtra(Intent.EXTRA_STREAM);
        if (imageUri != null) {
            // Update UI to reflect image being shared
        }
    }

    void handleSendMultipleImages(Intent intent) {
        ArrayList<Uri> imageUris = intent.getParcelableArrayListExtra(Intent.EXTRA_STREAM);
        if (imageUris != null) {
            // Update UI to reflect multiple images being shared
        }
    }
}
