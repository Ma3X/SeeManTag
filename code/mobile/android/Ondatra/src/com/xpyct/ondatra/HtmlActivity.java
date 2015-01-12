package com.xpyct.ondatra;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.Context;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.*;
import org.htmlcleaner.TagNode;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class HtmlActivity  extends Activity {
    static {
        System.loadLibrary("test");
    }
    private static native String stringFromJNI();

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		setTheme(android.R.style.Theme_Holo); // (for Android Built In Theme)

        setContentView(R.layout.htmlcleaner);

		// Create the array of numbers that will populate the numberpicker
		//final String[] nums = new String[21];
		//for(int i=0; i<nums.length; i++) {
		//	nums[i] = Integer.toString(i*5);
		//}
		final String[] nums = new String[150];
		for(int i=nums.length; i>0; i--) {
			nums[i-1] = Integer.toString(i);
		}

		// Set the max and min values of the numberpicker, and give it the
		// array of numbers created above to be the displayed numbers
		final NumberPicker np = (NumberPicker) findViewById(R.id.np);
		np.setMaxValue(149);
		np.setMinValue(0);
		np.setValue(81);
		np.setWrapSelectorWheel(false);
		np.setDisplayedValues(nums);

		final String[] nums2 = new String[10];
		for(int i=nums2.length-1; i>-1; i--) {
			nums2[i] = Integer.toString(i);
		}
		final NumberPicker np2 = (NumberPicker) findViewById(R.id.np2);
		np2.setMaxValue(9);
		np2.setMinValue(0);
		np2.setValue(2);
		np2.setWrapSelectorWheel(false);
		np2.setDisplayedValues(nums2);

		Button button = (Button) findViewById(R.id.button);
		button.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// Show the selected value of the numberpicker when the button is clicked
				Toast.makeText(HtmlActivity.this, "Selected value: " + nums[np.getValue()], Toast.LENGTH_SHORT).show();
			}
		});

        Button halter = (Button) findViewById(R.id.halter);
        halter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HtmlActivity.this, "System halting...", Toast.LENGTH_SHORT).show();
                //Sleep
                //IPowerManager powerManager = IPowerManager.Stub.asInterface(
                //        ServiceManager.getService(Context.POWER_SERVICE));
                //try {
                //    powerManager.shutdown(false, false);
                //} catch (RemoteException e) {
                //}

                final String message = stringFromJNI();
                final Button button = (Button)findViewById(R.id.halter);
                final String actualText = button.getText().toString();
                if(message.equals(actualText)) {
                    button.setText("Dummy");
                }
                else {
                    button.setText(message);
                }

                //HtmlActivity.LoadIPowerClass();
            }
        });

        //Находим кнопку
        //Button button = (Button)findViewById(R.id.parse);
        //Регистрируем onClick слушателя
        //button.setOnClickListener(myListener);
	}

    // http://stackoverflow.com/questions/6927444/why-do-not-work-in-android-setbacklightbrightnessint
    public static void LoadIPowerClass(/*Context context*/) {
        try {
            //Load classes and objects

            Object power = null;
            //Context fContext = context;
            Class<?> ServiceManager = null;
            try {
                ServiceManager = Class.forName("android.os.ServiceManager");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            Class<?> Stub = null;
            try {
                Stub = Class.forName("android.os.IPowerManager$Stub");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


            Method getService = null;
            try {
                getService = ServiceManager.getMethod("getService", new Class[]{String.class});
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            //Method asInterface = GetStub.getMethod("asInterface", new Class[] {IBinder.class});//of this class?
            Method asInterface = null;    //of this class?
            try {
                asInterface = Stub.getMethod("asInterface", new Class[]{IBinder.class});
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
            IBinder iBinder = null;//
            try {
                iBinder = (IBinder) getService.invoke(null, new Object[]{Context.POWER_SERVICE});
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            try {
                power = asInterface.invoke(null, iBinder); //or call constructor Stub?//
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            Method setBacklightBrightness = null;
            try {
                //setBacklightBrightness = power.getClass().getMethod("setBacklightBrightness", new Class[]{int.class});
                setBacklightBrightness = power.getClass().getMethod("shutdown", new Class[]{boolean.class, boolean.class});
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }

            //int Brightness = 5;
            boolean p1 = false;
            boolean p2 = false;

            try {
                //setBacklightBrightness.invoke(power, new Object[]{Brightness}); //HERE Failen
                setBacklightBrightness.invoke(power, new Object[]{p1, p2}); //HERE Failen
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

            //Log.i(TAG, "Load internal IPower classes Ok");
        } catch (InvocationTargetException e) {                     //HERE catch!!!!
            ;
        }
    }

	  //Диалог ожидания
	   private ProgressDialog pd;
	   //Слушатель OnClickListener для нашей кнопки
	   private OnClickListener myListener = new OnClickListener() {
	     public void onClick(View v) {
	       //Показываем диалог ожидания
	       pd = ProgressDialog.show(/*StackParser.this*/ HtmlActivity.this, 
	    		   "Working...", "request to server", true, false);
	       //Запускаем парсинг
	       new ParseSite().execute("http://www.stackoverflow.com");
	     }
	   };
	   
	   private class ParseSite extends AsyncTask<String, Void, List<String>> {
	     //Фоновая операция
	     protected List<String> doInBackground(String... arg) {
	       List<String> output = new ArrayList<String>();
	       try
	       {
	         HtmlHelper hh = new HtmlHelper(new URL(arg[0]));
	         List<TagNode> links = hh.getLinksByClass("question-hyperlink");

	         for (Iterator<TagNode> iterator = links.iterator(); iterator.hasNext();)
	         {
	           TagNode divElement = (TagNode) iterator.next();
	           output.add(divElement.getText().toString());
	         }
	       }
	       catch(Exception e)
	       {
	         e.printStackTrace();
	       }
	       return output;
	     }

	     //Событие по окончанию парсинга
	     protected void onPostExecute(List<String> output) {
	       //Убираем диалог загрузки
	       pd.dismiss();
	       //Находим ListView
	       ListView listview = (ListView) findViewById(R.id.listViewData);
	       //Загружаем в него результат работы doInBackground
	       listview.setAdapter(new ArrayAdapter<String>(/*StackParser.this*/ HtmlActivity.this,
	           android.R.layout.simple_list_item_1 , output));
	     }
	   }
}
