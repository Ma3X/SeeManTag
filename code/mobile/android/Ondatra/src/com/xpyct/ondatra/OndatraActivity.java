package com.xpyct.ondatra;

import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.res.Resources;
import com.xpyct.ondatra.R;
import org.htmlcleaner.TagNode;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class OndatraActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	//TODO: Поиграться с вхождениями =)
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        SharedPreferences prefs = getSharedPreferences("goodnight", MODE_PRIVATE); 
        prefs.getBoolean("isEnabled", false);
        prefs.getInt("startHour", 23);
        Editor prefEditor = prefs.edit();
        //prefEditor.putBoolean("isEnabled",isEnabledCheckBox.isChecked());
        //prefEditor.putInt("startHour", startTimePicker.getCurrentHour());
        //prefEditor.putInt("startMinute", startTimePicker.getCurrentMinute());
        //prefEditor.putInt("endHour", endTimePicker.getCurrentHour());
        //prefEditor.putInt("endMinute", endTimePicker.getCurrentMinute());
        prefEditor.commit();
        
        final Button btnP = (Button) findViewById(R.id.bPush);
        btnP.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				Context ctx = getApplicationContext();
                Resources res = getResources();
                String vInete = this.isOnline() ? res.getString(R.string.msg_online) : res.getString(R.string.msg_offline);
				Toast toast = Toast.makeText(ctx, vInete, Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0, 0);
				toast.show();
				
				//NotificationManager notifyMgr =
				//	(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
				//int icon = R.drawable.icon;
				//CharSequence tickerText = "Warning!";
				//long when = System.currentTimeMillis();
				//Notification noti = new Notification(icon, tickerText, when);
				//
				//CharSequence contentTitle = "My notification";
				//CharSequence contentText = "Hellows!";
				//Context context = getApplicationContext();
				//Intent intent = new Intent(context /*this*/, 
				//		                   HelloAndroidActivity.class);
				//PendingIntent cntIntent = PendingIntent.getActivity(context /*this*/,  
				//		                                            0, intent, 0);
				//
				//RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.custom_layout);
		        //contentView.setImageViewResource(R.id.image, R.drawable.android_happy);
		        //contentView.setTextViewText(R.id.bPush, "GM...");
		        //
		        //noti.contentIntent = cntIntent;
		        //noti.contentView = contentView;
		        //notifyMgr.notify(NOTIFY_ID, noti);
			}

			// Как проверить связь с Интернетом в Android?
			// http://androidengineer.ru/2011/01/kak-proverit-svyaz-s-internetom-v-android/
			public boolean isOnline() {
			    ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
			    NetworkInfo nInfo = cm.getActiveNetworkInfo();
			    if (nInfo != null && nInfo.isConnected()) {
			        Log.v("status", "ONLINE");
			        return true;
			    }
			    else {
			        Log.v("status", "OFFLINE");
			        return false;
			    }
			}
        });
        
        final Button btTemper = (Button) findViewById(R.id.bTemper);
        btTemper.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) // клик на кнопку
            {
                RefreshTemper();
            }
        });
        RefreshTemper(); // при запуске грузим температуру сразу
        
        final Button btMusic = (Button) findViewById(R.id.bMusic);
        btMusic.setOnClickListener(new Button.OnClickListener() {
            MediaPlayer mPlayer;
            public void onClick(View v)
            {
            	if(mPlayer==null) {
    				Context ctx = getApplicationContext();
                	mPlayer = MediaPlayer.create(ctx, R.raw.snd101);
            	}
            	if(mPlayer.isPlaying()) {
                	mPlayer.stop();
            	} else {
                	mPlayer.start();
            	}
            }
        });
    }

    public String GetTemper(String urlsite) // фукция загрузки температуры
    {
        String matchtemper = "";
        try
        {
            // загрузка страницы
            URL url = new URL(urlsite);
            URLConnection conn = url.openConnection();
            InputStreamReader rd = new InputStreamReader(conn.getInputStream());
            StringBuilder allpage = new StringBuilder();
            int n = 0;
            char[] buffer = new char[100000];
            while (n >= 0)
            {
                n = rd.read(buffer, 0, buffer.length);
                if (n > 0)
                {
                    allpage.append(buffer, 0, n);                    
                }
            }
            // работаем с регулярками
            //final Pattern pattern = Pattern.compile
            //        ("<span style=\"color:#[a-zA-Z0-9]+\">[^-+0]+([-+0-9]+)[^<]+</span>[^(а-яА-ЯёЁa-zA-Z0-9)]+([а-яА-ЯёЁa-zA-Z ]+)");
            final Pattern pattern = Pattern.compile
                    ("<dd class='value m_temp c'>([-+0-9]+)<span");
            Matcher matcher = pattern.matcher(allpage.toString());
            if (matcher.find())
            {    
                matchtemper = matcher.group(1);            
            }        
            return matchtemper;
        }
        catch (Exception e)
        {
            matchtemper = e.getMessage();
        }
        return matchtemper;  
    };

    public void RefreshTemper()
    { 
        final TextView tTemper = (TextView) findViewById(R.id.tvTemper);
        String bashtemp = "";
		if(this.isOnline()) {
	        //bashtemp = GetTemper("http://be.bashkirenergo.ru/weather/ufa/");
            bashtemp = GetTemper("http://www.gismeteo.ru/city/daily/4429/");
            if(bashtemp!=null) {
                tTemper.setText(bashtemp.concat("°")); // отображение температуры
            } else {
                tTemper.setText("temperature not found");
            }
		} else {
		    tTemper.setText(bashtemp);
			Context ctx = getApplicationContext();
			Toast toast = Toast.makeText(ctx, "весь интернет закончился", Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, 0);
			toast.show();
		}
    };

	public boolean isOnline() {
	    ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo nInfo = cm.getActiveNetworkInfo();
	    if (nInfo != null && nInfo.isConnected()) {
	        Log.v("status", "ONLINE");
	        return true;
	    }
	    else {
	        Log.v("status", "OFFLINE");
	        return false;
	    }
	}
    
	public void onClick(View v) {
		;
	}
}
