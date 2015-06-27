package com.xpyct.ondatra;

import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.xpyct.tests.RetrieveInfoTask;

public class MuteActivity extends Activity {

    // example XPATH queries in the form of strings - will be used later
    private static final String NAME_XPATH = "//div[@class='yfi_quote']/div[@class='hd']/h2";
 
    private static final String TIME_XPATH = "//table[@id='time_table']/tbody/tr/td[@class='yfnc_tabledata1']";
 
    private static final String PRICE_XPATH = "//table[@id='price_table']//tr//span";

    private static final String MUTE_XPATH = "//h2[@class='StatOnSite ']//span";

    // TagNode object, its use will come in later
    private static TagNode node;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.mute);

		final Button btnP = (Button) findViewById(R.id.bMute);
        btnP.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				RefreshMute();
			}
        });
        RefreshMute();
	}

	@Override
	protected void onStart() {
		super.onStart();

		Context ctx = getApplicationContext();
		Toast toast = Toast.makeText(ctx, "onCreate", Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
	
	@Override
	protected void onDestroy() {
		Context ctx = getApplicationContext();
		Toast toast = Toast.makeText(ctx, "onDestroy", Toast.LENGTH_SHORT);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();

		super.onDestroy();
	}

    public String GetMute(String urlsite)
    {
        String matchmute = "";
        try
        {
            // the URL whose HTML I want to retrieve and parse
            //String option_url = "http://finance.yahoo.com/q?s=" + name.toUpperCase();
            String option_url = urlsite;
     
            // this is where the HtmlCleaner comes in, I initialize it here
            HtmlCleaner cleaner = new HtmlCleaner();
            CleanerProperties props = cleaner.getProperties();
            props.setAllowHtmlInsideAttributes(true);
            props.setAllowMultiWordAttributes(true);
            props.setRecognizeUnicodeChars(true);
            props.setOmitComments(true);
     
            // open a connection to the desired URL
            URL url = new URL(option_url);
            URLConnection conn = url.openConnection();
     
            //use the cleaner to "clean" the HTML and return it as a TagNode object
            node = cleaner.clean(new InputStreamReader(conn.getInputStream()));
     
            // once the HTML is cleaned, then you can run your XPATH expressions on the node, which will then return an array of TagNode objects (these are returned as Objects but get casted below)
            Object[] info_nodes = node.evaluateXPath(NAME_XPATH);
            Object[] time_nodes = node.evaluateXPath(TIME_XPATH);
            Object[] price_nodes = node.evaluateXPath(PRICE_XPATH);

            Object[] mute_nodes = node.evaluateXPath(MUTE_XPATH);
     
            // here I just do a simple check to make sure that my XPATH was correct and that an actual node(s) was returned
            if (info_nodes.length > 0) {
                // casted to a TagNode
                TagNode info_node = (TagNode) info_nodes[0];
                // how to retrieve the contents as a string
                String info = info_node.getChildren().iterator().next().toString().trim();
     
                // some method that processes the string of information (in my case, this was the stock quote, etc)
                //processInfoNode(o, info);
            }
     
            if (time_nodes.length > 0) {
                TagNode time_node = (TagNode) time_nodes[0];
                String date = time_node.getChildren().iterator().next().toString().trim();
     
                // date returned in 15-Jan-10 format, so this is some method I wrote to just parse that string into the format that I use
                //processDateNode(o, date);
            }
     
            if (price_nodes.length > 0) {
                TagNode price_node = (TagNode) price_nodes[0];
                double price = Double.parseDouble(price_node.getChildren().iterator().next().toString().trim());
                //o.setPremium(price);
            }

            if (mute_nodes.length > 0) {
                TagNode mute_node = (TagNode) mute_nodes[0];
                //String mute = mute_node.getChildren().iterator().next().toString().trim();
                String mute = mute_node.getChildren().iterator().next().toString().replaceFirst("&nbsp", "");
                matchmute = mute;
            }
            
            return matchmute;
        }
        catch (Exception e)
        {
            
        }
        return matchmute;  
    };

    public void RefreshMute()
    {
        Context ctx1 = getApplicationContext();
        new RetrieveInfoTask().execute("192.168.1.50", "hello");
        String info = "Test sync.";
        Toast toast1 = Toast.makeText(ctx1, info, Toast.LENGTH_SHORT);
        toast1.setGravity(Gravity.CENTER, 0, 0);
        toast1.show();

        final TextView tMute = (TextView) findViewById(R.id.tvMute);
        CharSequence bashmute = "";
		if(this.isOnline()) {
	        String bm = GetMute("http://ya.ru/_mute_");
	        // http://androidforums.com/application-development/151384-string-charsequence.html
	        bashmute = bm.subSequence(0, bm.length());
	        tMute.setText(bashmute);
		} else {
		    tMute.setText(bashmute);
			Context ctx = getApplicationContext();
			Toast toast = Toast.makeText(ctx, "internet not found", Toast.LENGTH_SHORT);
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
}
