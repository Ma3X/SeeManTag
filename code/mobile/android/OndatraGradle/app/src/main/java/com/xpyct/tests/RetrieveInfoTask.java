package com.xpyct.tests;

import android.os.AsyncTask;

import com.xpyct.ondatra.MuteActivity;

public class RetrieveInfoTask extends AsyncTask<String, Void, String> {

    MuteActivity activity;
    private Exception exception;
    String sendedText;

    public RetrieveInfoTask(MuteActivity activity)
    {
        this.activity = activity;
    }

    protected String doInBackground(String... sendText) {
        try {
            //return new HWClient().getInfo(sendText);
            this.sendedText = sendText[1];
            return HWClient.main(sendText);
        } catch (Exception e) {
            this.exception = e;
            return null;
        }
    }

    protected void onPostExecute(String feed) {
        // TODO: check this.exception
        // TODO: do something with the feed
        if(this.sendedText != "get_image") {
            activity.PostRetrieveInfoTask(feed);
        } else {
            activity.PostRetrieveImage(feed);
        }
    }
}
