package com.kpu.plantid;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

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
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.kpu.plantidAdmin.R;


public class MainActivityPest extends Activity {
	String fam,species,common,zone,pest;
	private AQuery aq;
	private List<Car2> myCars = new ArrayList<Car2>();
	private static final String url = "jdbc:mysql://107.170.241.190:3306/plantid";
	private static final String user = "aaron";
	private static final String pass = "kpuaaron";
	int count=0;
	String line;
	public static Activity fa;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(android.R.style.Theme_Light);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main_activity_pest);
		setTitleColor(Color.RED);
		fa = this;
		setTitle("Search");
		
			
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			try{
				
				//species = extras.getString("species");
				
				//pest = extras.getString("pest");
				
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
		ProgressDialog progressDialog = new ProgressDialog(MainActivityPest.this);
		InputStream is = null ;
		String result = "";
		@Override
		protected void onPreExecute() {
			progressDialog.setMessage("Connecting, Please Wait..");
			progressDialog.setTitle("PEST Database...");
			progressDialog.show();





		}
		@Override
		protected Void doInBackground(String... params)
		{

			try {
				populateCarList();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			return null;
		}


		@Override
		protected void onPostExecute(Void v)
		{
			populateListView();
			if (global.a==1)
			{
			setTitle(count+ " PEST Results Found");
			}
			if (global.a==2)
			{
			setTitle(count+ " Locations Found");
			}
			if (count ==0)
			{
				TextView t1 = (TextView)findViewById(R.id.textView1);
				t1.setVisibility(View.VISIBLE);
			}
			this.progressDialog.dismiss();
		}
	}//task

	private void populateCarList() throws IOException {
		String query = null;
		try {
			
			//check to see if value from serach page is null
            
           
           
            if(global.a==1)
            {
            	query = "select dd,des,pest,scientific_name,id,filename from pest";
            }
            if(global.a==2)
            {
            	query = "select dd,des,pest,scientific_name,id,filename from plantlocation";
            }

            
            //query ="select dd,des,pest,scientific_name,id,filename from plantlocation where (family_name like '%"+species+"%' or scientific_name like '%"+species+"%' or common_name like '%"+species+"%' or des like '%"+species+"%' ) and pest like '%"+pest+"%' ";
            
            Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(query);

			count=0;
			while (rs.next())
			{
				
				String d = null;
				String temp=rs.getString("filename");
				String result = temp.substring(temp.lastIndexOf('/')+1).trim();
				 if(global.a==1)
		            {
					d = "http://107.170.241.190/uploads/uploads/"+result;
		            }
		            if(global.a==2)
		            {
		            d = "http://107.170.241.190/uploads/plantlocations/"+result;
		            }
				
				myCars.add(new Car2(rs.getString("des"),rs.getString("scientific_name"),rs.getString("pest"),rs.getString("id"),d,rs.getString("dd")));
				count=count+1;
				
			}


			




		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void populateListView() {
		ArrayAdapter<Car2> adapter = new MyListAdapter();
		ListView list = (ListView) findViewById(R.id.carsListView);
		list.setAdapter(adapter);
	}

	private void registerClickCallback() {
		ListView list = (ListView) findViewById(R.id.carsListView);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked,
					int position, long id) {

				Car2 clickedCar = myCars.get(position);
				
				//Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
				Intent i = new Intent(getApplicationContext(), Infopest.class);
	     		i.putExtra("id",clickedCar.getId());
	     		i.putExtra("common", clickedCar.getCommon());
	     		i.putExtra("family", clickedCar.getFamily());
	     		i.putExtra("species", clickedCar.getSpecies());
	     		startActivity(i);
			}
		});
	}

	private class MyListAdapter extends ArrayAdapter<Car2> {
		
		public MyListAdapter() {
			super(MainActivityPest.this, R.layout.item_view2, myCars);
		}

		@SuppressWarnings("deprecation")
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// Make sure we have a view to work with (may have been given null)
			View itemView = convertView;
			if (itemView == null) {
				itemView = getLayoutInflater().inflate(R.layout.item_view2, parent, false);
			}

			// Find the car to work with.
			Car2 currentCar = myCars.get(position);

			// Fill the view


			// family
			TextView makeText = (TextView) itemView.findViewById(R.id.textView2);
			makeText.setText(currentCar.getSpecies());
			makeText.setText(setTextStyleItalic(makeText.getText()));

			// species
			TextView yearText = (TextView) itemView.findViewById(R.id.textView4);
			yearText.setText((Html.fromHtml("<i>Pest:  </i></font>" + currentCar.getCommon())));
			
			TextView r1 = (TextView) itemView.findViewById(R.id.textView1);
			r1.setText(Html.fromHtml("<i>Description:  </i></font>" + currentCar.getFamily()+"<br><i>Date Taken:  </i></font>"+currentCar.getDate()));
			
			aq = new AQuery(itemView);
            
            aq.id(R.id.listimage).progress(R.id.progressBar1).image(currentCar.getIconID(), true, true, 100, 100).visible();
			
						
						
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