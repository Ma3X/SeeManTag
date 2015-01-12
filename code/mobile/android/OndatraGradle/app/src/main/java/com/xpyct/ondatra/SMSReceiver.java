// http://www.anddev.org/talking_sms_app_tutorial-t9624.html

package com.xpyct.ondatra;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;
 
public class SMSReceiver extends BroadcastReceiver {

	@Override
    public void onReceive(Context context, Intent intent) {
        int n; 
        Bundle bundle = intent.getExtras();
        Object pdus[] = (Object[]) bundle.get("pdus");
        SmsMessage smsMessage[] = new SmsMessage[pdus.length];
        String[] messages = new String[pdus.length];
        for (n = 0; n<pdus.length; n++) {
        	smsMessage[n] = SmsMessage.createFromPdu((byte[])pdus[n]);
        	//smsMessage[n] = SmsMessage.createFromPdu((byte[]) messages[n].getBytes());
        }
        // show first message
        String sms1 = smsMessage[0].getMessageBody();
        String from = smsMessage[0].getOriginatingAddress();
        Toast toast = Toast.makeText(context,"SMS Received from "+from+":" + sms1, Toast.LENGTH_LONG);
        toast.show();
        //Speaker.speakSMS(sms1);                         
    }
}
