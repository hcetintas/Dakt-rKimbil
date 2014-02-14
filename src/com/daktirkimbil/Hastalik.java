package com.daktirkimbil;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Hastalik extends Activity implements OnClickListener{
String bilgi="";
String bolum="";
TextView tvBolum ;
TextView tvBilgi;
Button BtnHastane;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.kimbil);
		bolum="";
		bilgi="";
		tvBilgi = (TextView)findViewById(R.id.tvHastane);
		tvBolum = (TextView)findViewById(R.id.textView2);
        BtnHastane = (Button)findViewById(R.id.btnHastane);
        BtnHastane.setOnClickListener(this);
            String url ="http://example.com/Kimbil/HastalikBilgi.php?Aranan="+getIntent().getExtras().getString("HastalikId");
            Log.w("url", url);
            try
            {
            	String result = new Data().execute(url).get();
            JSONArray jArray = new JSONArray(result);
            for(int i=0;i<jArray.length();i++){
            	JSONObject json_data = jArray.getJSONObject(i);
              	bolum = json_data.getString("Bolum");
              	bilgi = json_data.getString("Bilgi");
            }
            }
            catch(Exception ex)
            {
            	Log.v("hata",ex.toString());
            }
            tvBilgi.setText(bilgi);
            tvBolum.setText(tvBolum.getText()+bolum);
           
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(v.getId() ==R.id.btnHastane){
			Intent i = new Intent("android.intent.action.MAP");
			startActivity(i);
		}
		
	}

}
