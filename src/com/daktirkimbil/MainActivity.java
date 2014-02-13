package com.daktirkimbil;

import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ExecutionException;

import org.json.JSONArray;
import org.json.JSONObject;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	MultiAutoCompleteTextView multitv;
	ListView listView;
    Button Ara;
    Button Yolla;
	String isim;
	String id;
	String [] id1;
	String Aranan;
	Button button1;
	String [] bel=null;
	 ArrayAdapter<String> adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Ara = (Button)findViewById(R.id.btnAra);
		Yolla = (Button)findViewById(R.id.btnSorgula);
		button1 = (Button)findViewById(R.id.button1);
		multitv =(MultiAutoCompleteTextView) findViewById(R.id.multiAutoCompleteTextView1);
		if(Neresi()!=null){
		adapter =new ArrayAdapter<String>(this,android.R.layout.select_dialog_singlechoice, Neresi());}
		else{
			Toast.makeText(getApplicationContext(), "Nette sýkýntý var", Toast.LENGTH_LONG).show();
		}
        multitv.setAdapter(adapter);

        listView = (ListView) findViewById(R.id.list);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        multitv.setThreshold(1);
       multitv.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        
       
       button1.setOnClickListener(this);
       Ara.setOnClickListener(this) ;
       Yolla.setOnClickListener(this);
        
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
	
		if(v.getId() == R.id.btnAra)
		{
			Aranan= multitv.getText().toString();
			multitv.setText("");
			Aranan = Aranan.substring(0, (Aranan.length()-2));
			bel = Belirtiler(Aranan);
	        listView.setAdapter(null);
	        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice,bel);
	        listView.setAdapter(adapter);
	     }
		
		else if(v.getId()==R.id.btnSorgula){
		   String name="";
		   String [] belirtilerId = BelirtilerId(Aranan);	      
	       for(int i=0; i<bel.length;i++){
	    	   if(listView.isItemChecked(i)){
	    		   name =name+belirtilerId[i+1]+",";
	    		   Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();
	    	   }
	       }
	       String hasta = "";
	       String hastaAdet = "";
	       name = name.substring(0, name.length()-1);
	       String [] HastalikAdet = HastalikAdet(name);
	       String [] Hastalik =Hastalik(name);
	       Log.w("name", name);
	       for(int i=0;i<Hastalik.length;i++){
	    	   hasta = hasta + Hastalik[i]+",";
	    	   hastaAdet = hastaAdet + HastalikAdet[i]+",";
	       }
	       hastaAdet = hastaAdet.substring(0, hastaAdet.length()-1);
	       hasta = hasta.substring(0,hasta.length()-1);
	       Intent i = new Intent("android.intent.action.KIMBIL");
			i.putExtra("hasta", hasta);
			i.putExtra("HastaAdet",hastaAdet);
			startActivity(i);
		}
	       
	        
		}
	
	public String [] Hastalik(String Aranan){

		isim = "";
		
		String url="http://pauyemeklistesi.com/Kimbil/Hastalik.php?Aranan="+Aranan;
		
if(isOnline()){
	String[] dizi22 = null;
            try
            {
            	String result = new Data().execute(url).get();

            JSONArray jArray = new JSONArray(result);
            dizi22 =new String[jArray.length()];
            for(int i=0;i<jArray.length();i++){
            	JSONObject json_data = jArray.getJSONObject(i);
            	dizi22[i]=json_data.getString("HastalikId");}
            }
            catch(Exception ex)
            {
            	Log.v("isOnline()",ex.toString());
            }
           return dizi22;
	        

        }
            else if(!isOnline()){
            	connectionMessage("Please check your internet connection.");
            }
return null;
	}
		public boolean isOnline()
	{
	    ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
	 
	    NetworkInfo netInfo = cm.getActiveNetworkInfo();
	 
	    if (netInfo != null && netInfo.isConnectedOrConnecting())
	    {
	        return true;
	    }
	    return false;
	}
	public void connectionMessage(String hata)
	{
	      AlertDialog.Builder builder = new AlertDialog.Builder(this);
	 
	      builder.setMessage(hata).setPositiveButton("OK", new DialogInterface.OnClickListener()
	      {
	            public void onClick(DialogInterface dialog, int which)
	            {
	 
	                finish();
	 
	            }
	      });
	 
	      final AlertDialog alert = builder.create();
	      alert.show();
	}
	public String [] HastalikAdet(String Aranan){

		isim = "";
		String[] dizi22 =null;
		String url="http://pauyemeklistesi.com/Kimbil/Hastalik.php?Aranan="+Aranan;
		
if(isOnline()){
            
            try
            {
            	String result = new Data().execute(url).get();
            JSONArray jArray = new JSONArray(result);
            dizi22 = new String[jArray.length()];
            for(int i=0;i<jArray.length();i++){
            	JSONObject json_data = jArray.getJSONObject(i);
              	dizi22[i]=json_data.getString("adet");}
            }
            catch(Exception ex)
            {
            	Log.v("HastalikAdet(String Aranan)",ex.toString());
            }

           return dizi22;
	        

        }
            else if(!isOnline()){
            	connectionMessage("Please check your internet connection.");
            }
return null;
	}
	
	public String[] Neresi(){

		String[] dizi2=null;
		String url="http://pauyemeklistesi.com/Kimbil/Neresi.php";
		
if(isOnline()){
            try
            {
            	String result = new Data().execute(url).get();
            JSONArray jArray = new JSONArray(result);
            dizi2 = new String[jArray.length()];

            for(int i=0;i<jArray.length();i++){
            	JSONObject json_data = jArray.getJSONObject(i);
              	dizi2[i]=json_data.getString("Adi");}
            }
            
            catch(Exception ex)
            {
            	Log.v("Neresi()",ex.toString());
            }
	        return dizi2;

        }
            else if(!isOnline()){
            	connectionMessage("Please check your internet connection.");
            }

return null;
	}


	public String [] Belirtiler(String Aranan){

		String[] belirtiler =null;
		String url="http://pauyemeklistesi.com/Kimbil/Aranan.php?Aranan="+Aranan;
		
if(isOnline()){
            try
            {
            String result = new Data().execute(url).get();
            JSONArray jArray = new JSONArray(result);
            belirtiler = new String[jArray.length()];
            
            for(int i=0;i<jArray.length();i++){
            	JSONObject json_data = jArray.getJSONObject(i);
              	belirtiler[i] = json_data.getString("Adi");
              	}
            }
            catch(Exception ex)
            {
            	Log.v("Belirtiler(String Aranan)",ex.toString());
            }

           return belirtiler;
	        

        }
            else if(!isOnline()){
            	connectionMessage("Please check your internet connection.");
            }
return null;
	}
	
	public String [] BelirtilerId(String Aranan){

		id1=null;
		id=null;
		String[] dizi22 =null;
		String url="http://pauyemeklistesi.com/Kimbil/Aranan.php?Aranan="+Aranan;
		
if(isOnline()){
            try
            {
            	String result = new Data().execute(url).get();
           
            JSONArray jArray = new JSONArray(result);
            dizi22 = new String[jArray.length()];
            for(int i=0;i<jArray.length();i++){
            	JSONObject json_data = jArray.getJSONObject(i);
            	dizi22[i] = json_data.getString("id");}
            }
            catch(Exception ex)
            {
            	Log.v("BelirtilerId(String Aranan)",ex.toString());
            }
           return dizi22;
        }
            else if(!isOnline()){
            	connectionMessage("Please check your internet connection.");
            }
return null;
	}
	

}
