package com.kpu.PlantidAdmin;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.text.Html;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.kpu.PlantidAdmin.R;

public class Favlist extends Activity {
	private List<Car> myCars = new ArrayList<Car>();
	int count = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(android.R.style.Theme_Black);
		setContentView(R.layout.activity_favlist);
		setTitleColor(Color.GREEN);
		new task().execute();
		registerClickCallback();
		
	}
	@Override
	protected void onRestart()
	{   super.onResume();
		Intent a = new Intent(getApplicationContext(),Favlist.class);
		startActivity(a);
		finish();
	}
	@Override
	protected void onPause()
	{
	    super.onPause();
	    System.gc();
	}
	
	
	private void populateCarList() {
		DbHelper dbHelper = new DbHelper(Favlist.this);
		 SQLiteDatabase db = dbHelper.getWritableDatabase();
		 Cursor c = db.rawQuery("SELECT rowid,* FROM myfav;",null);
		 
		
		
		while (c.moveToNext())
		{
						
			myCars.add(new Car(c.getString(c.getColumnIndex("family")),c.getString(c.getColumnIndex("species")),c.getString(c.getColumnIndex("common")),c.getString(c.getColumnIndex("pid")),null));
			count = count+1;
		}

	}//populate car
	
	private void populateListView() {
		ArrayAdapter<Car> adapter = new MyListAdapter();
		ListView list = (ListView) findViewById(R.id.listView1);
		list.setAdapter(adapter);
	}

	class task extends AsyncTask<String, String, Void>
	{
		ProgressDialog progressDialog = new ProgressDialog(Favlist.this);
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

			populateCarList();

			return null;
		}


		@Override
		protected void onPostExecute(Void v)
		{
			populateListView();
			setTitle(count+" Plants In Your Favorites List");
			this.progressDialog.dismiss();
		}
	}//task
	
	private class MyListAdapter extends ArrayAdapter<Car> {
		public MyListAdapter() {
			super(Favlist.this, R.layout.item_view2, myCars);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// Make sure we have a view to work with (may have been given null)
			View itemView = convertView;
			if (itemView == null) {
				itemView = getLayoutInflater().inflate(R.layout.item_view2, parent, false);
			}

			// Find the car to work with.
			Car currentCar = myCars.get(position);

			// Fill the view


			// family
						TextView makeText = (TextView) itemView.findViewById(R.id.textView2);
						makeText.setText(currentCar.getSpecies());

						// species
						TextView yearText = (TextView) itemView.findViewById(R.id.textView1);
						
						yearText.setText((Html.fromHtml("<i>Description:  </i></font>" + currentCar.getFamily())));
						// Common name:
						TextView condionText = (TextView) itemView.findViewById(R.id.textView4);
						condionText.setText((Html.fromHtml("<i>Pest:  </i></font>" + currentCar.getCommon())));

			return itemView;
		}
	}//adapter
	
	private void registerClickCallback() {
		ListView list = (ListView) findViewById(R.id.listView1);
		list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View viewClicked,
					int position, long id) {

				Car clickedCar = myCars.get(position);
				
				//Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
				Intent i = new Intent(getApplicationContext(), Infofav.class);
	     		i.putExtra("id",clickedCar.getId());
	     		i.putExtra("common", clickedCar.getCommon());
	     		i.putExtra("family", clickedCar.getFamily());
	     		i.putExtra("species", clickedCar.getSpecies());
	     		startActivity(i);
	     		
	     		
			}
		});
	}//register callback
	
	public void refresh(View view)
	{
		Intent a = new Intent(getApplicationContext(),Favlist.class);
		startActivity(a);
		finish();
	}
}
