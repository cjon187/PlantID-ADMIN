package com.kpu.PlantidAdmin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.kpu.PlantidAdmin.R;
import com.mysql.jdbc.Statement;

public class Infofav extends Activity {
	String common,species,family,id,webimage;
	private static final String url = "jdbc:mysql://107.170.241.190:3306/plantid";
	private static final String user = "aaron";
	private static final String pass = "kpuaaron";
	String file1;
	Bitmap mIcon_val,mIcon_val1,mIcon_val2;
	ImageView imageView,imageView1,imageView2;
	
	String jon,latitude,longitude;
	@SuppressLint("InlinedApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(android.R.style.Theme_Holo_Light);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_infofav);
		setTitleColor(Color.GREEN);		
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		
		new task3().execute();
		
			
	}
	@Override
	protected void onPause()
	{
	    super.onPause();
	    System.gc();
	}
	public void image(View view)
	{
		Intent i = new Intent(getApplicationContext(), Webimage.class);
 		i.putExtra("image",webimage);
 		startActivity(i);
		
	}
	private Bitmap downloadImage(String url) {
        Bitmap bitmap = null;
        InputStream stream = null;
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inSampleSize = 1;

        try {
            stream = getHttpConnection(url);
            bitmap = BitmapFactory.
                    decodeStream(stream, null, bmOptions);
            stream.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return bitmap;
    }
	 
	private InputStream getHttpConnection(String urlString)
            throws IOException {
        InputStream stream = null;
        URL url = new URL(urlString);
        URLConnection connection = url.openConnection();

        try {
            HttpURLConnection httpConnection = (HttpURLConnection) connection;
            httpConnection.setRequestMethod("GET");
            httpConnection.connect();

            if (httpConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                stream = httpConnection.getInputStream();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return stream;
    }
	public void menu(View view)
	{
		Infofav.this.openOptionsMenu();
	}
	
	public void google()
	{
		
		String url = "http://www.google.ca/search?hl=en&imgtbs=zt&q=`"+common+" "+species;
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		startActivity(browserIntent);
	}
	
	public void map(View view)
	{
		
		Intent a = new Intent(getApplicationContext(),Kwantlen.class);
		startActivity(a);
	}
	
	public void show()
	{
		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			try{
				
				common = extras.getString("common");
				species = extras.getString("species");
				family = extras.getString("family");
				id = extras.getString("id");
				
				runOnUiThread(new Runnable()
				{   @Override
					public void run()
				{
					setTitle(species+" In Favorites List");
					TextView text1 = (TextView)findViewById(R.id.textView2);
					text1.setText(Html.fromHtml("<b>Family Name: </font></b>"+family));
					
					TextView text3 = (TextView)findViewById(R.id.textView6);
					text3.setText(Html.fromHtml("<b>Common Name: </font></b>"+common));
					
					String temp = common.replace(" ","");
					temp = temp.replace(",", "");
					temp = temp.replace("'", "");
					webimage = "http://"+temp+".jpg.to";				
					
					
				    
			      				}
				});//runonui
			}

			catch (Exception e)
			{
				//Toast.makeText(getApplicationContext(), "ERROR on TExt" , Toast.LENGTH_LONG).show();
			}


		}//if
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			/* System.out.println("Database connection success"); */

			
			Statement st1 = (Statement) con.createStatement();
			//ResultSet rs1 = st1.executeQuery("select * from plant where plant.id = '"+id+"'");
			ResultSet rs1 = st1.executeQuery("select plant_code,fruit_color,flowering_time,flower_color,leaf_form,leaf_arrangement,flower_corolla_shape,leaf_shape,hardiness_zones,"+
					"lat,lon,type,pest from plant where plant.id = '"+id+"'");

						
			while (rs1.next())
			{
final String code = rs1.getString("plant_code");
				
				
				final String barkshowy = rs1.getString("fruit_color");
				final String budsmell = rs1.getString("flowering_time");
				final String outer = rs1.getString("flower_color");
				
				final String addinfo = rs1.getString("leaf_form");
				final String fct = rs1.getString("leaf_arrangement");
			
				final String fcin = rs1.getString("leaf_shape");
				final String hardiness = rs1.getString("hardiness_zones");
				final String lat = rs1.getString("lat");
				final String lon = rs1.getString("lon");
				final String type = rs1.getString("type");
				final String pest = rs1.getString("pest");
				//zone=hardiness;
				latitude = lat;
				longitude = lon;
				file1 =code;
				
				
				
					try {
						imageView = (ImageView)findViewById(R.id.jon12);
						URL newurl = new URL("http://107.170.241.190/plantdb/l/"+code+"h2"); 
						 mIcon_val = BitmapFactory.decodeStream(newurl.openConnection() .getInputStream());
					} catch (Exception e) {
						imageView.getLayoutParams().width =0;
						e.printStackTrace();
					}
					
					
					try {
						imageView1 = (ImageView)findViewById(R.id.imageView2);
						URL newurl1 = new URL("http://107.170.241.190/plantdb/l/"+code+"l1"); 
						 mIcon_val1 = BitmapFactory.decodeStream(newurl1.openConnection() .getInputStream());
					} catch (Exception e) {
						imageView1.getLayoutParams().width =0;
						e.printStackTrace();
					}
					
					
					try {
						imageView2 = (ImageView)findViewById(R.id.imageView3);
						URL newurl2 = new URL("http://107.170.241.190/plantdb/l/"+code+"f1"); 
						mIcon_val2 = BitmapFactory.decodeStream(newurl2.openConnection() .getInputStream());
					} catch (Exception e) {
						imageView2.getLayoutParams().width =0;
						e.printStackTrace();
					}
					
					
					
				
				
				
				
				runOnUiThread(new Runnable()
				{   @Override
					public void run()
				{
					
					
					TextView text5= (TextView)findViewById(R.id.textView10);
					text5.setText(Html.fromHtml("<b>Flower Colour: </font></b>"+outer));
					TextView text6= (TextView)findViewById(R.id.textView12);
					text6.setText(Html.fromHtml("<b>Leaf Arrangement: </font></b>"+fct));
					TextView text7= (TextView)findViewById(R.id.textView13);
					text7.setText(Html.fromHtml("<b>Leaf Shape: </font></b>"+fcin));
					TextView text9= (TextView)findViewById(R.id.textView15);
					text9.setText(Html.fromHtml("<b>Leaf Form: </font></b>"+addinfo));
					TextView text10= (TextView)findViewById(R.id.textView16);
					text10.setText(Html.fromHtml("<b>Flowering Time: </font></b>"+budsmell));
					TextView text12= (TextView)findViewById(R.id.textView18);
					text12.setText(Html.fromHtml("<b>Fruit Colour: </font></b>"+barkshowy));
					
					TextView text14= (TextView)findViewById(R.id.textView20);
					text14.setText(Html.fromHtml("<b>Hardiness Zones: </font></b>"+hardiness));
					TextView text15= (TextView)findViewById(R.id.textView1);
					text15.setText(Html.fromHtml("<b>Plant Type: </font></b>"+type));
					TextView pest1= (TextView)findViewById(R.id.textView22);
					pest1.setText(Html.fromHtml("<b>Pest Susceptibility:  </font></b>"+pest));
					
					//set images in hor sv
					imageView.setScaleType(ScaleType.FIT_XY);
					imageView.setImageBitmap(mIcon_val);
					imageView1.setScaleType(ScaleType.FIT_XY);
					imageView1.setImageBitmap(mIcon_val1);
					imageView2.setScaleType(ScaleType.FIT_XY);
					imageView2.setImageBitmap(mIcon_val2);
					
				}
				});//runonui


			}



		} catch (Exception e) {
			Toast.makeText(this, "No Image", 3).show();
			e.printStackTrace();
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.infofav, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.google:
	            google();
	            return true;
	        case R.id.viewpest:
	            viewpest();
	            return true;	       
	        case R.id.share:
	            share();
	            return true;
	        case R.id.main:
	            gotomain();
	            return true;
	        case R.id.remove:
	            delete();
	            return true;
	            
	            
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	
	
	class task3 extends AsyncTask<String, String, Void>
	{
  		ProgressDialog progressDialog = new ProgressDialog(Infofav.this);
  		
	      protected void onPreExecute() {
	    	  progressDialog.setMessage("Connecting, Please Wait..");
		       progressDialog.setTitle("Plant Database");
		       progressDialog.show();
		}
		@Override
		protected Void doInBackground(String... params) {
			
			show();
			return null;
			 
	     }
			
	     
		protected void onPostExecute(Void v) {
			
			
		this.progressDialog.dismiss();					
		}
  }//task
		
	public void viewpest()
	{
		Intent i = new Intent(getApplicationContext(), MainActivityPest.class);
 		i.putExtra("species", species);
 		i.putExtra("zone", "All");
 		i.putExtra("pest", "All");
 		startActivity(i);
	}
	public void slideshow(View view)
	{
		Intent i = new Intent(getApplicationContext(), Slide.class);
 		i.putExtra("file1",file1);
 		i.putExtra("species", species);
 		i.putExtra("pick", "1");
 		startActivity(i);
	}
	public void slideshow1(View view)
	{
		Intent i = new Intent(getApplicationContext(), Slide.class);
 		i.putExtra("file1",file1);
 		i.putExtra("species", species);
 		i.putExtra("pick", "2");
 		startActivity(i);
	}
	public void slideshow2(View view)
	{
		Intent i = new Intent(getApplicationContext(), Slide.class);
 		i.putExtra("file1",file1);
 		i.putExtra("species", species);
 		i.putExtra("pick", "3");
 		startActivity(i);
	}
	
	public void share()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(Infofav.this);
		builder.setMessage("Share this screenshot? An image of this screenshot will be saved in your gallery\n and an option to share it now will be avaliable.").setPositiveButton("Yes", dialogClickListener1)
		.setNegativeButton("No", dialogClickListener1).show();
	}
	
	DialogInterface.OnClickListener dialogClickListener1 = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which){
			case DialogInterface.BUTTON_POSITIVE:
				email();

				break;

			case DialogInterface.BUTTON_NEGATIVE:
				//No button clicked
				break;
			}
		}
	};
	
	
	public void email()
	 {
		 View u = findViewById(R.id.ScrollView1);
         u.setDrawingCacheEnabled(true);                                                
         ScrollView z = (ScrollView) findViewById(R.id.ScrollView1);
         int totalHeight = z.getChildAt(0).getHeight();
         int totalWidth = z.getChildAt(0).getWidth();
         u.layout(0, 0, totalWidth, totalHeight);    
         u.buildDrawingCache(true);
         Bitmap b = Bitmap.createBitmap(u.getDrawingCache());             
         u.setDrawingCacheEnabled(false);

         //Save bitmap
         String extr = Environment.getExternalStorageDirectory().toString();
         String fileName = "test.jpg";
         File myPath = new File(extr, fileName);
         FileOutputStream fos = null;
         try {
             fos = new FileOutputStream(myPath);
             b.compress(Bitmap.CompressFormat.JPEG, 100, fos);
             fos.flush();
             fos.close();
             MediaStore.Images.Media.insertImage(getContentResolver(), b, "Screen", "screen");
         }catch (FileNotFoundException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         } catch (Exception e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         }
		
         Intent i = new Intent(getApplicationContext(), Infofav.class);
   		i.putExtra("id",id);
   		i.putExtra("common", common);
   		i.putExtra("family", family);
   		i.putExtra("species", species);
   		startActivity(i);
   		finish();
      // build a email sending intent 
         Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
         emailIntent.setType("image/png");
         // set the email subject 
         emailIntent.putExtra(Intent.EXTRA_SUBJECT, species+" ");
         // set the email image path for the attachment 
         emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://" + extr+"/test.jpg"));
         // set the body of the email 
         emailIntent.putExtra(Intent.EXTRA_TEXT, "From Kwantlen Plant Database App ");
        
         startActivity(Intent.createChooser(emailIntent, "Send Email"));
         
               
	 }
	public void delete()
	{	
		AlertDialog.Builder builder = new AlertDialog.Builder(Infofav.this);
		builder.setMessage("Delete "+species+ " From your favorites list?").setPositiveButton("Yes", dialogClickListener)
		.setNegativeButton("No", dialogClickListener).show();
		
		
	}
	
	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which){
			case DialogInterface.BUTTON_POSITIVE:
				DbHelper dbHelper = new DbHelper(Infofav.this);
			    SQLiteDatabase db = dbHelper.getWritableDatabase();
			    db.execSQL("DELETE FROM myfav where pid = '"+id+"'");
			    
			    finish();
				break;

			case DialogInterface.BUTTON_NEGATIVE:
				//No button clicked
				break;
			}
		}
	};//dialog
	
	public void gotomain()
	{
		Intent intent = new Intent(this, Start.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
	}
}
