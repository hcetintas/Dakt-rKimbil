package com.daktirkimbil;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Kimbil extends ListActivity{
String id="";
String isim="";
String[]  id1;
String[] HastalikId;
ProgressDialog dialog;
	  @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        requestWindowFeature(Window.FEATURE_NO_TITLE);
	        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
	            WindowManager.LayoutParams.FLAG_FULLSCREEN);
	            HastalikId = getIntent().getExtras().getString("hasta").split(",");  
	            String [] Adi = null;
	            if(isOnline()){
	            String url ="http://pauyemeklistesi.com/Kimbil/HastalikAdi.php?Aranan="+getIntent().getExtras().getString("hasta");
	            Log.w("url", url);
	            try
	            {
	            	String result = new Data().execute(url).get();
	            JSONArray jArray = new JSONArray(result);
	            Adi = new String[jArray.length()];
	            for(int i=0;i<jArray.length();i++){
	            	JSONObject json_data = jArray.getJSONObject(i);
	              	Adi[i] = json_data.getString("Adi");
	            }
	            }
	            catch(Exception ex)
	            {
	            	Log.v("hata",ex.toString());
	            }
	            Log.w("hastalikadet", getIntent().getExtras().getString("HastaAdet"));
	          String[] HastalikAdet = getIntent().getExtras().getString("HastaAdet").split(",");
	            Siralama(HastalikAdet,Adi,HastalikId);
		        setListAdapter(new ArrayAdapter<String>(Kimbil.this, android.R.layout.simple_list_item_checked,Adi));
		        } 
	           
	            else if(!isOnline()){connectionMessage("Please check your internet connection.");}
	          

	        }
	    
public void Siralama(String [] HastalikAdet,String[] Hastalik,String [] HastalikId){
	Log.w("boyut", String.valueOf(HastalikAdet.length)+"deðer");
	for(int i=0;i<HastalikAdet.length;i++){
		for(int a=0;a<HastalikAdet.length;a++){
			Log.w("a", String.valueOf(a));
			Log.w("a", String.valueOf(i));
			if(Integer.valueOf(HastalikAdet[i])>Integer.valueOf( HastalikAdet[a])){
				String araci = HastalikAdet[i];
				HastalikAdet[i]=HastalikAdet[a];
				HastalikAdet[a] = araci;
				
				araci = Hastalik[i];
				Hastalik[i]=Hastalik[a];
				Hastalik[a] = araci;
				

				araci = HastalikId[i];
				HastalikId[i]=HastalikId[a];
				HastalikId[a] = araci;
			}
		}
	}
	int toplam=0;
	for(int i=0;i<HastalikAdet.length;i++){
		toplam = toplam+Integer.valueOf(HastalikAdet[i]);
	}
	Log.w("Toplam", String.valueOf(toplam));
	for(int i=0;i<Hastalik.length;i++){
		float oran = 100*Float.valueOf(HastalikAdet[i])/toplam;
		Log.w("Toplam", String.valueOf(oran));
		Hastalik[i] = Hastalik[i] +"- %"+String.valueOf(oran);
	}
	for(int c=0;c<HastalikAdet.length;c++){
		Log.w("HastalikAdet", HastalikAdet[c]);
		Log.w("Hastalik", Hastalik[c]);
	}
	
	//return null;
}

protected void onListItemClick(ListView l, View v, int position, long id) {
	// TODO Auto-generated method stub
	super.onListItemClick(l, v, position, id);
	Intent i = new Intent("android.intent.action.HASTALIK");
	i.putExtra("HastalikId",HastalikId[position] );
	Log.w("HastalikId", HastalikId[position]);
	startActivity(i);
	
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
		


}
