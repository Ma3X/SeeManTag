// http://www.anddev.org/talking_sms_app_tutorial-t9624.html

package com.xpyct.ondatra;

import android.speech.tts.*;
import android.app.Activity;
import android.os.Bundle;
 
public class Speaker extends Activity {
    /** Called when the activity is first created. */
        private static TextToSpeech myTts;
                        
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        myTts = new TextToSpeech(this, ttsInitListener);
    }
    private TextToSpeech.OnInitListener ttsInitListener = new TextToSpeech.OnInitListener() {
        public void onInit(int version) {
          //myTts.speak(""+o, 0, null);
        }
      };
    
   public static void speakSMS(String sms)
   {
           myTts.speak(sms, 0, null);
           myTts.synthesizeToFile(sms,null, "/sdcard/download/mysms.wav");
   }
}
