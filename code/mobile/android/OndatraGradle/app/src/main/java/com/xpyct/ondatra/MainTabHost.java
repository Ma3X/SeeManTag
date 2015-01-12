// http://code.google.com/p/mobyfactory-uiwidgets-android/
// http://www.android-x.ru/content/development/android-sdk/scrollable-tabhost

package com.xpyct.ondatra;

import com.mobyfactory.uiwidgets.RadioStateDrawable;
import com.mobyfactory.uiwidgets.ScrollableTabActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;

/*
 * This activity demonstrates the use of ScrollableTabActivity by extending the class
 * 
 * Required files:
 * ScrollableTabActivity.java
 * RadioStateDrawable.java
 * TabBarButton.java
 * res/drawable/bottom_bar_highlight.9.png
 * res/drawable/bottom_bar.9.png
 * res/drawable/scrollbar_horizontal_thumb.xml
 * res/drawable/scrollbar_horizontal_track.xml
 * res/layout/customslidingtabhost.xml
 * res/layout/scrollgroupradiobuttonview.xml
 */
public class MainTabHost  extends ScrollableTabActivity {

	protected PowerManager.WakeLock mWakeLock;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        final PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
        this.mWakeLock = pm.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "My Tag");
        this.mWakeLock.acquire();
        
        /*
         * set this activity as the tab bar delegate
         * so that onTabChanged is called when users tap on the bar
         */
        setDelegate(new SliderBarActivityDelegateImpl());
        
        for (int i=0; i<8; i++)
        {
        	Intent intent;
        	String thname;
        	switch (i%5) {
                case  0:
                    intent = new Intent(this, OndatraActivity.class);
                    thname = "Base";
                    this.addTab(thname, R.drawable.tab_1, RadioStateDrawable.SHADE_GREEN, RadioStateDrawable.SHADE_RED, intent);
                    break;
                case  1:
                    intent = new Intent(this, SoapMADActivity.class);
                    thname = "Soap";
                    this.addTab(thname, R.drawable.tab_2, RadioStateDrawable.SHADE_GREEN, RadioStateDrawable.SHADE_RED, intent);
                    break;
                case  2:
                    intent = new Intent(this, HtmlActivity.class);
                    thname = "HClean";
                    this.addTab(thname, R.drawable.tab_3, RadioStateDrawable.SHADE_GREEN, RadioStateDrawable.SHADE_RED, intent);
                    break;
                case  3:
                    intent = new Intent(this, XSaneActivity.class);
                    thname = "Xsane";
                    this.addTab(thname, R.drawable.tab_4, RadioStateDrawable.SHADE_GREEN, RadioStateDrawable.SHADE_RED, intent);
                    break;
                case  4:
                    intent = new Intent(this, MuteActivity.class);
                    thname = "Mute";
                    this.addTab(thname, R.drawable.tab_5, RadioStateDrawable.SHADE_GREEN, RadioStateDrawable.SHADE_RED, intent);
                    break;
                default:
                    intent = new Intent(this, OndatraActivity.class);
                    thname = "";
                    this.addTab(thname, R.drawable.star, RadioStateDrawable.SHADE_GREEN, RadioStateDrawable.SHADE_RED, intent);
        	}
        	//if (i%2==0) intent = new Intent(this, OndatraActivity.class);
        	//else intent = new Intent(this, HtmlActivity.class);
        	
        	/*
        	 * This adds a title and an image to the tab bar button
        	 * Image should be a PNG file with transparent background.
        	 * Shades are opaque areas in on and off state are specific as parameters
        	 */
            //this.addTab(thname, R.drawable.star, RadioStateDrawable.SHADE_MAGENTA, RadioStateDrawable.SHADE_RED, intent);

        	/*
        	 * This adds a title, an off-state image, and an on-state image to the tab bar button
        	 * Image is displayed as it is without shading
        	 */
        	//this.addTab("title"+i, R.drawable.star_orange_frame, R.drawable.star_orange_filled, intent);
        }
        
        /*
         * commit is required to redraw the bar after add tabs are added
         * if you know of a better way, drop me your suggestion please.
         */
        commit();
    }

	@Override
    public void onDestroy() {
            this.mWakeLock.release();
            super.onDestroy();
    }

    private class SliderBarActivityDelegateImpl extends SliderBarActivityDelegate
    {
    	/*
    	 * Optional callback method
    	 * called when users tap on the tab bar button
    	 */
    	protected void onTabChanged(int tabIndex) 
    	{
    		Log.d("onTabChanged",""+tabIndex);
    	}
    }
}
