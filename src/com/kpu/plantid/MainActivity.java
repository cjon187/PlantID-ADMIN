package com.kpu.plantid;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.kpu.plantidAdmin.R;

public class MainActivity extends Activity {
	String fam,species,common,zone,pest,month,flowerc,leaff,leafa,type,leafs,fruitc;
	String zz = "0";
	String yy = "11";
	private List<car3> myCars = new ArrayList<car3>();
	private static String url_all_products = "http://107.170.241.190/plantid/a-listall.php";
	 
	int count=0;
	String line;
	private AQuery aq;
	 JSONParser jParser = new JSONParser();
	 JSONArray products = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(android.R.style.Theme_Light);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTitleColor(Color.GREEN);
		
			
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			try{
				
				species = extras.getString("species");
				zone = extras.getString("zone");
				pest = extras.getString("pest");
				month = extras.getString("month");
				flowerc = extras.getString("flowerc");
				leaff = extras.getString("leaff");
				leafa = extras.getString("leafa");
				type=extras.getString("type");
				leafs=extras.getString("leafs");
				fruitc=extras.getString("fruitc");
				
			}

			catch (Exception e)
			{
				Toast.makeText(getApplicationContext(), "ERROR on TExt" , Toast.LENGTH_LONG).show();
			}


		}

		new task().execute();

		registerClickCallback();

	}
	@Override
	protected void onPause()
	{
	    super.onPause();
	    System.gc();
	}
	class task extends AsyncTask<String, String, Void>
	{
		ProgressDialog progressDialog = new ProgressDialog(MainActivity.this);
		InputStream is = null ;
		String result = "";
		@Override
		protected void onPreExecute() {
			progressDialog.setMessage("Connecting, Please Wait..");
			progressDialog.setTitle("Plant Database...");
			progressDialog.show();





		}
		@Override
		protected Void doInBackground(String... params)
		{

			try {
				populateCarList();   
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}


		@Override
		protected void onPostExecute(Void v)
		{
			populateListView();
			
			setTitle(count+ " Results Found");
			if (count == 0)
			{
				TextView t = (TextView)findViewById(R.id.textView1);
				t.setVisibility(View.VISIBLE);
			}
			
			this.progressDialog.dismiss();
		}
	}//task

	private void populateCarList() throws JSONException
	{	
		 
			List<NameValuePair> params = new ArrayList<NameValuePair>();
		    params.add(new BasicNameValuePair("scientific", species));
            params.add(new BasicNameValuePair("type", type));
            params.add(new BasicNameValuePair("month", month));
            params.add(new BasicNameValuePair("zone", zone));
            params.add(new BasicNameValuePair("leafform", leaff));
            params.add(new BasicNameValuePair("leafarrangement", leafa));
            params.add(new BasicNameValuePair("leafshape", leafs));
            params.add(new BasicNameValuePair("flowercolour", flowerc));
            params.add(new BasicNameValuePair("fruitcolour", fruitc));
            params.add(new BasicNameValuePair("pest", pest));
            
            
			// getting JSON string from URL
			JSONObject json = jParser.makeHttpRequest(url_all_products, "GET", params);
			products = json.getJSONArray("products");
		
			count=0;
			 for (int i = 0; i < products.length(); i++) {
                 JSONObject c = products.getJSONObject(i);
				String temp=c.getString("plant_code");
				String imageurl = "http://107.170.241.190/plantdb/l/"+temp+"h1.jpg";
				myCars.add(new car3(c.getString("family_name"),c.getString("scientific_name"),c.getString("common_name"),c.getString("id"),imageurl,c.getString("type"),c.getString("hardiness_zones"),c.getString("leaf_form"),c.getString("leaf_arrangement"),c.getString("leaf_shape"),c.getString("flowering_time"),c.getString("flower_color"),c.getString("fruit_color"),c.getString("pest"),c.getString("plant_code"),c.getString("lat"),c.getString("lon"),c.getString("species2")));
				count=count+1;
				
			}

	}
	
	





	

	private void populateListView() {
		ArrayAdapter<car3> adapter = new MyListAdapter();
		ListView list = (ListView) findViewById(R.id.carsListView);
		list.setAdapter(adapter);
	}

	private void registerClickCallback() {
		ListView list = (ListView) findViewById(R.id.carsListView);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked,
					int position, long id) {

				car3 clickedCar = myCars.get(position);
				
				//Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
				Intent i = new Intent(getApplicationContext(), Info.class);
	     		i.putExtra("id",clickedCar.getID());
	     		i.putExtra("common", clickedCar.getCommon());
	     		i.putExtra("family", clickedCar.getFamily());
	     		i.putExtra("species", clickedCar.getSpecies());
	     		i.putExtra("type",clickedCar.getType());
	     		i.putExtra("zones", clickedCar.getZones());
	     		i.putExtra("leafform", clickedCar.getLeafform());
	     		i.putExtra("leafarrangement", clickedCar.getLeafarrangement());
	     		i.putExtra("leafshape", clickedCar.getLeafshape());
	     		i.putExtra("floweringtime", clickedCar.getFloweringtime());
	     		i.putExtra("flowercolour", clickedCar.getFlowercolour());
	     		i.putExtra("fruitcolour", clickedCar.getFruitColour());
	     		i.putExtra("pest", clickedCar.getPest());
	     		i.putExtra("code",clickedCar.getCode());
	     		i.putExtra("lat",clickedCar.getLat());
	     		i.putExtra("lon",clickedCar.getLon());
	     		startActivity(i);
			}
		});
	}

	private class MyListAdapter extends ArrayAdapter<car3> {
		public MyListAdapter() {
			super(MainActivity.this, R.layout.item_view, myCars);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// Make sure we have a view to work with (may have been given null)
			View itemView = convertView;
			if (itemView == null) {
				itemView = getLayoutInflater().inflate(R.layout.item_view, parent, false);
			}

			// Find the car to work with.
			car3 currentCar = myCars.get(position);

			// Fill the view


			// family
			TextView makeText = (TextView) itemView.findViewById(R.id.textView2);
			makeText.setText(currentCar.getSpecies()+" "+currentCar.getSpecies2());
			makeText.setText(setTextStyleItalic(makeText.getText()));
			
			// species
			TextView yearText = (TextView) itemView.findViewById(R.id.textView4);
			yearText.setText((Html.fromHtml(currentCar.getFamily())));
			// Common name:
			TextView condionText = (TextView) itemView.findViewById(R.id.textView6);
			condionText.setText((Html.fromHtml(currentCar.getCommon())));
			
			aq = new AQuery(itemView);
			
            aq.id(R.id.imageView1).image(currentCar.getIconID(), true, true, 90, 90).visible();

						
			return itemView;
		}
	}
	public void back(View view)
	{
		finish();
	}
	//for samsung italics
	public static CharSequence setTextStyleItalic(CharSequence text) {
	    final StyleSpan style = new StyleSpan(Typeface.ITALIC);
	    final SpannableString str = new SpannableString(text);
	    str.setSpan(style, 0, text.length(), 0);
	    return str;
	}
	
}
