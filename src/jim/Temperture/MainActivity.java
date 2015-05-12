package jim.Temperture;

import android.os.AsyncTask;
//import android.os.Build;
import android.os.Bundle;
//import android.os.StrictMode;
import android.annotation.SuppressLint;
import android.app.Activity;
//import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;
//import java.net.URL;
import java.io.*;

//import org.xml.sax.InputSource;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;

public class MainActivity extends Activity {
	//Id_CityName
	final String[] Id_CityName={"2306179",//台北市
								"20070569",//新北市
								"2298866",//桃園
								"2306185",//新竹市
								"2306185",//新竹
								"2301128",//苗栗
								"2306181",//台中市
								"2306183",//彰化
								"2306204",//南投
								"2296315",//嘉義市
								"2296315",//嘉義
								"2306182",//台南市
								"2306180",//高雄市
								"2306189",//屏東
								"2306188",//基隆市
								"2306198",//宜蘭
								"2306187",//花蓮
								"2306190",//台東
								"2347346",//雲林
								"22695856",//澎湖
								"28760735",//金門
								"12470575"};//馬祖
								
	private final String Yahoo_Weather_Url="http://weather.yahooapis.com/forecastrss?u=c&w=";
	private int City_Id;
	private Spinner s;
	
    @SuppressLint("NewApi") @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        s = (Spinner)findViewById(R.id.spinner1);
        //ArrayAdapter
        ArrayController(s);
        SetListener();
        Button go =(Button) findViewById(R.id.button1);
        go.setOnClickListener(getDataFromNet);
        
        
       
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    private void ArrayController(Spinner spinner){
    	//new ArrayAdapter();
    	ArrayAdapter< CharSequence > SourceAdapter = ArrayAdapter.createFromResource(this, R.array.id_city, android.R.layout.simple_spinner_item);
    	SourceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	spinner.setAdapter(SourceAdapter);
    }
    private void SetListener(){
    	s.setOnItemSelectedListener(getId);
    }
    
    private Spinner.OnItemSelectedListener getId = new Spinner.OnItemSelectedListener(){
    	
    	
		public void onItemSelected(AdapterView parent, View v, int position, long id) {
			// TODO Auto-generated method stub
			City_Id = parent.getSelectedItemPosition();
			//Log.d("Jim",Integer.toString(City_Id));
			
			
		}
		
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}
    	
    };
    
    private OnClickListener getDataFromNet = new OnClickListener()
    {
    	public void onClick(View v){
    		
    		try {
				
    			//String value = new GetDataFromYahoo().doInBackground(Yahoo_Weather_Url+Id_CityName[City_Id]);
				 new GetDataFromYahoo().execute(Yahoo_Weather_Url+Id_CityName[City_Id]);
    			
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		
    	}
    	
    };
    /*
    private String parseData(String u) throws Exception{
    	DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    	DocumentBuilder bd= factory.newDocumentBuilder();
    	Document temperature_xml = bd.parse(u);
    	NodeList nlist = temperature_xml.getElementsByTagName("yweather:condition");
    	String temp=null;
    	for(int i=0;i<nlist.getLength();i++){
    		Element e =(Element) nlist.item(i);	
			temp=e.getAttribute("temp");
    	}
    	return temp;
    }*/
    private class GetDataFromYahoo  extends AsyncTask<String, Void, String> {
    	String temp=null;
    	TextView tview, tview_WaitMessage;
    	Toast waitMessage = Toast.makeText(MainActivity.this, R.string.network_notconnetced, Toast.LENGTH_SHORT);
		
    	@Override
    	protected void onPreExecute(){
    		super.onPreExecute();
    		tview = (TextView) findViewById(R.id.textView3);
    		tview_WaitMessage =(TextView) findViewById(R.id.textView6);
    		
    		tview_WaitMessage.setText(R.string.wait_message);
    		
    	}
    	@Override
    	protected String doInBackground(String... arg0) {
    		// TODO Auto-generated method stub
    		
        	
    		try {
    			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    	    	DocumentBuilder bd= factory.newDocumentBuilder();
    	    	Document temperature_xml;
    			temperature_xml = bd.parse(arg0[0]);
    			NodeList nlist = temperature_xml.getElementsByTagName("yweather:condition");
    	    	
    	    	for(int i=0;i<nlist.getLength();i++){
    	    		Element e =(Element) nlist.item(i);	
    				temp=e.getAttribute("temp");
    	    	}
    		} catch (SAXException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		} catch (IOException e1) {
    			// TODO Auto-generated catch block
    			waitMessage.show();
    			e1.printStackTrace();
    		} catch (ParserConfigurationException e1) {
    			// TODO Auto-generated catch block
    			e1.printStackTrace();
    		}
        	
        	return temp;
    		
    		
    	}
    	@Override
    	protected void onPostExecute(String unused){
    		tview_WaitMessage.setText("");
    		if(temp != null)
    			tview.setText(temp+"℃");
    		//tview.setText("12345");
    	}
    	
    	
    	
    }
}
