package com.xpyct.ondatra;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.xpyct.ondatra.R;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import java.util.Hashtable;

public class SoapMADActivity extends Activity {

	public class Category implements KvmSerializable
	{
	    public int CategoryId;
	    public String Name;
	    public String Description;
	    
	    public Category(){}
	    

	    public Category(int categoryId, String name, String description) {
	        
	        CategoryId = categoryId;
	        Name = name;
	        Description = description;
	    }


	    public Object getProperty(int arg0) {
	        
	        switch(arg0)
	        {
	        case 0:
	            return CategoryId;
	        case 1:
	            return Name;
	        case 2:
	            return Description;
	        }
	        
	        return null;
	    }

	    public int getPropertyCount() {
	        return 3;
	    }

	    public void getPropertyInfo(int index, Hashtable arg1, PropertyInfo info) {
	        switch(index)
	        {
	        case 0:
	            info.type = PropertyInfo.INTEGER_CLASS;
	            info.name = "CategoryId";
	            break;
	        case 1:
	            info.type = PropertyInfo.STRING_CLASS;
	            info.name = "Name";
	            break;
	        case 2:
	            info.type = PropertyInfo.STRING_CLASS;
	            info.name = "Description";
	            break;
	        default:break;
	        }
	    }

	    public void setProperty(int index, Object value) {
	        switch(index)
	        {
	        case 0:
	            CategoryId = Integer.parseInt(value.toString());
	            break;
	        case 1:
	            Name = value.toString();
	            break;
	        case 2:
	            Description = value.toString();
	            break;
	        default:
	            break;
	        }
	    }
	}
	
	final String NAMESPACE = "urn:examples";
    final String URL = "http://no-ip.org:12345/csoapserver";

	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);
        
        final Button btnP = (Button) findViewById(R.id.button1);
        btnP.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String METHOD_NAME = "sayHello";
				String SOAP_ACTION = "urn:examples#sayHello";

				SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
		        Request.addProperty("name", "Bobbi");
		        
		        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		        envelope.bodyOut = Request;

		        HttpTransportSE htse = new HttpTransportSE(URL);

		        try
		        {
		        	htse.call(SOAP_ACTION, envelope);
		            TextView tv = (TextView)findViewById(R.id.tekstik);
		            tv.setText("" + envelope.getResponse());
		        }
		        catch(Exception e)
		        {
		            e.printStackTrace();
		        }
			}
        });

        final Button bPlay = (Button) findViewById(R.id.btnPlay);
        bPlay.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String METHOD_NAME = "doPlay";
				String SOAP_ACTION = "urn:examples#doPlay";
		        SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
		        Request.addProperty("name", "Bobbi");
		        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		        envelope.bodyOut = Request;
		        try {
			        HttpTransportSE htse = new HttpTransportSE(URL);
		        	htse.call(SOAP_ACTION, envelope);
		            TextView tv = (TextView)findViewById(R.id.tekstik);
                    tv.setText("" + envelope.getResponse());
	            } catch(Exception e) {
	                e.printStackTrace();
	            }
			}
        });

        final Button bStop = (Button) findViewById(R.id.btnStop);
        bStop.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String METHOD_NAME = "doStop";
				String SOAP_ACTION = "urn:examples#doStop";
		        SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
		        Request.addProperty("name", "Bobbi");
		        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		        envelope.bodyOut = Request;
		        try {
			        HttpTransportSE htse = new HttpTransportSE(URL);
		        	htse.call(SOAP_ACTION, envelope);
		            TextView tv = (TextView)findViewById(R.id.tekstik);
                    tv.setText("" + envelope.getResponse());
	            } catch(Exception e) {
	                e.printStackTrace();
	            }
			}
        });

        final Button bPrev = (Button) findViewById(R.id.btnPrev);
        bPrev.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String METHOD_NAME = "doPrev";
				String SOAP_ACTION = "urn:examples#doPrev";
		        SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
		        Request.addProperty("name", "Bobbi");
		        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		        envelope.bodyOut = Request;
		        try {
			        HttpTransportSE htse = new HttpTransportSE(URL);
		        	htse.call(SOAP_ACTION, envelope);
		            TextView tv = (TextView)findViewById(R.id.tekstik);
                    tv.setText("" + envelope.getResponse());
	            } catch(Exception e) {
	                e.printStackTrace();
	            }
			}
        });

        final Button bNext = (Button) findViewById(R.id.btnNext);
        bNext.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String METHOD_NAME = "doNext";
				String SOAP_ACTION = "urn:examples#doNext";
		        SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
		        Request.addProperty("name", "Bobbi");
		        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		        envelope.bodyOut = Request;
		        try {
			        HttpTransportSE htse = new HttpTransportSE(URL);
		        	htse.call(SOAP_ACTION, envelope);
		            TextView tv = (TextView)findViewById(R.id.tekstik);
                    tv.setText("" + envelope.getResponse());
	            } catch(Exception e) {
	                e.printStackTrace();
	            }
			}
        });

        final Button bExit = (Button) findViewById(R.id.btnExit);
        bExit.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String METHOD_NAME = "doExit";
				String SOAP_ACTION = "urn:examples#doExit";
		        SoapObject Request = new SoapObject(NAMESPACE, METHOD_NAME);
		        Request.addProperty("name", "Bobbi");
		        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		        envelope.bodyOut = Request;
		        try {
			        HttpTransportSE htse = new HttpTransportSE(URL);
		        	htse.call(SOAP_ACTION, envelope);
		            TextView tv = (TextView)findViewById(R.id.tekstik);
                    tv.setText("" + envelope.getResponse());
	            } catch(Exception e) {
	                e.printStackTrace();
	            }
			}
        });
    }
}