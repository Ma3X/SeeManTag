// http://davanum.wordpress.com/2007/12/15/android-listen-for-incoming-sms-messages/
// http://www.androidcompetencycenter.com/2008/12/android-api-sms-handling/
// http://appinventor.googlelabs.com/learn/tutorials/notext/notext.html
// http://appinventor.googlelabs.com/learn/setup/hellopurr/hellopurrphonepart1.html
// http://appinventor.googlelabs.com/ode/Ya.html#1154679
// http://www.anddev.org/talking_sms_app_tutorial-t9624.html
// http://groups.google.com/group/android-beginners/browse_thread/thread/caa0b335531ffa9c?pli=1

package com.xpyct.ondatra;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.BroadcastReceiver; //android.content.IntentReceiver;
import android.os.Bundle;
import android.telephony.*; //android.provider.Telephony;
//import android.telephony.gsm.SmsMessage;
import android.util.Log;
 
public class SMSApp extends BroadcastReceiver/*IntentReceiver*/ {
    private static final String LOG_TAG = "SMSApp";
 
    /* package */ static final String ACTION =
            "android.provider.Telephony.SMS_RECEIVED";
 
    private void appendData(StringBuilder buf, String key, String value) {
        buf.append(", ");
        buf.append(key);
        buf.append('=');
        buf.append(value);
    }

    //public void onReceiveIntent(Context context, Intent intent) {
	@Override
	public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ACTION)) {
            StringBuilder buf = new StringBuilder();
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                //SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
                //SmsMessage[] messages = Telephony.Sms.Intents.getMessagesFromIntent(intent);
                //for (int i = 0; i < messages.length; i++) {
                //    SmsMessage message = messages[i];
                //    buf.append("Received SMS from  ");
                //    buf.append(message.getDisplayOriginatingAddress());
                //    buf.append(" - ");
                //    buf.append(message.getDisplayMessageBody());
                ;//}
            }
            Log.i(LOG_TAG, "[SMSApp] onReceiveIntent: " + buf);
            NotificationManager nm = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
 
            //nm.notifyWithText(123, buf.toString(),
                //NotificationManager.LENGTH_LONG, null);
                ;//NotificationManager.LENGTH_LONG, null);
        }
	}
}
