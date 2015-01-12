package com.xpyct.tests;

import android.os.AsyncTask;

public class RetrieveInfoTask extends AsyncTask<String, Void, String> {

    private Exception exception;

    protected String doInBackground(String... sendText) {
        try {
            //return new HWClient().getInfo(sendText);
            return HWClient.main(sendText);
        } catch (Exception e) {
            this.exception = e;
            return null;
        }
    }

    protected void onPostExecute(String feed) {
        // TODO: check this.exception
        // TODO: do something with the feed
    }
}
