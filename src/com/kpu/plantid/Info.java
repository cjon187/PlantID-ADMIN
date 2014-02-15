package com.kpu.plantid;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.SQLException;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.kpu.plantidAdmin.R;


public class Info extends Activity {
	String common,species,family,id,webimage,zones;
	String type,code,leafarrangement,leafshape,leafform,floweringtime,flowercolour,fruitcolour,pest;
	
	
	Bitmap mIcon_val,mIcon_val1,mIcon_val2;
	ImageView imageView,imageView1,imageView2;
	private AQuery aq;
	
	String jon,latitude,longitude;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(android.R.style.Theme_Holo_Light);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_info);
		setTitleColor(Color.GREEN);
		
		 
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			try{
				
				common = extras.getString("common");
				species = extras.getString("species");
				family = extras.getString("family");
				id = extras.getString("id");
				type = extras.getString("type");
				zones = extras.getString("zones");
				leafform = extras.getString("leafform");
				leafarrangement=extras.getString("leafarrangement");
				leafshape = extras.getString("leafshape");
				floweringtime = extras.getString("floweringtime");
				flowercolour = extras.getString("flowercolour");
				fruitcolour = extras.getString("fruitcolour");
				pest = extras.getString("pest");
				code = extras.getString("code");
				latitude=extras.getString("lat");
				longitude=extras.getString("lon");
				TextView text5= (TextView)findViewById(R.id.textView10);
				text5.setText(Html.fromHtml("<b>Flower Colour: </font></b>"+flowercolour));
				TextView text6= (TextView)findViewById(R.id.textView12);
				text6.setText(Html.fromHtml("<b>Leaf Arrangement: </font></b>"+leafarrangement));
				TextView text7= (TextView)findViewById(R.id.textView13);
				text7.setText(Html.fromHtml("<b>Leaf Shape: </font></b>"+leafshape));
				TextView text9= (TextView)findViewById(R.id.textView15);
				text9.setText(Html.fromHtml("<b>Leaf Form: </font></b>"+leafform));
				TextView text10= (TextView)findViewById(R.id.textView16);
				text10.setText(Html.fromHtml("<b>Flowering Time: </font></b>"+floweringtime));
				TextView text12= (TextView)findViewById(R.id.textView18);
				text12.setText(Html.fromHtml("<b>Fruit Colour: </font></b>"+fruitcolour));
				
				TextView text14= (TextView)findViewById(R.id.textView20);
				text14.setText(Html.fromHtml("<b>Hardiness Zones: </font></b>"+zones));
				TextView text15= (TextView)findViewById(R.id.textView1);
				text15.setText(Html.fromHtml("<b>Plant Type: </font></b>"+type));
				TextView pest1= (TextView)findViewById(R.id.textView22);
				pest1.setText(Html.fromHtml("<b>Pest Susceptibility:  </font></b>"+pest));
				
				/*aq = new AQuery(Info.this);	
				String url1 = "http://107.170.241.190/plantdb/l/"+code+"h1";  
				aq.id(R.id.imageView1).image(url1).visible();
				String url2 = "http://107.170.241.190/plantdb/l/"+code+"l1";  
				aq.id(R.id.imageView2).image(url2).visible();
				String url3 = "http://107.170.241.190/plantdb/l/"+code+"f1";  
				aq.id(R.id.imageView3).image(url3).visible();*/
			}
			catch(Exception e)
			{
			}
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
	
	public void menu(View view)
	{
		Info.this.openOptionsMenu();
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
	
	public void google()
	{
		
		String url = "http://www.google.ca/search?hl=en&imgtbs=zt&q=`"+common+" "+species;
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		startActivity(browserIntent);
	}
	
	public void map()
	{
		Intent a = new Intent(getApplicationContext(),Kwantlen.class);
		a.putExtra("latitude",latitude);
		a.putExtra("longitude",longitude);
		a.putExtra("species", species);
		a.putExtra("common",common);
		startActivity(a);
		
		
	}
	
	public void show()
	{
		
		
				
				runOnUiThread(new Runnable()
				{   @Override
					public void run()
				{
					setTitle(Html.fromHtml("<b><i>"+species+"</b></i>"));
					TextView text1 = (TextView)findViewById(R.id.textView2);
					text1.setText(Html.fromHtml("<b>Family Name: </font></b>"+family));
					TextView text3 = (TextView)findViewById(R.id.textView6);
					text3.setText(Html.fromHtml("<b>Common Name: </font></b>"+common));
		
				}
				});//runonui
			

			


		
		try {
			
				
				
				
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
					
					
					
				
				
				
				
				


			



		} catch (Exception e) {
			Toast.makeText(this, "No Image", 3).show();
			e.printStackTrace();
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.info, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.google:
	            google();
	            return true;
	        case R.id.help:
	            add();
	            return true;
	        case R.id.fav:
	            fav();
	            return true;
	        case R.id.share:
	            email();
	            return true;
	        case R.id.main:
	            gotomain();
	            return true;
	        case R.id.viewpest:
	            viewpest();
	            return true;
	        case R.id.map:
	            map();
	            return true;
	        case R.id.location:
	            location();
	            return true;
	       
	            
	            
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	
	
	
	class task3 extends AsyncTask<String, String, Void>
	{
  		ProgressDialog progressDialog = new ProgressDialog(Info.this);
  		
	      protected void onPreExecute() {
	    	
		}
		@Override
		protected Void doInBackground(String... params) {
			
			show();
			return null;
			 
	     }
			
	     
		protected void onPostExecute(Void v) {
			imageView.setScaleType(ScaleType.FIT_XY);
			imageView.setImageBitmap(mIcon_val);
			imageView1.setScaleType(ScaleType.FIT_XY);
			imageView1.setImageBitmap(mIcon_val1);
			imageView2.setScaleType(ScaleType.FIT_XY);
			imageView2.setImageBitmap(mIcon_val2);	
			TextView t1 = (TextView)findViewById(R.id.textView3);
			t1.setVisibility(View.GONE);
			ProgressBar p1 = (ProgressBar)findViewById(R.id.progressBar1);
			p1.setVisibility(View.GONE);
		}
  }//task
	public void gotomain()
	{	
		Intent intent = new Intent(this, Start.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
	}
	
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
 		i.putExtra("file1",code);
 		i.putExtra("species", species);
 		i.putExtra("pick", "1");
 		startActivity(i);
	}
	public void slideshow1(View view)
	{
		Intent i = new Intent(getApplicationContext(), Slide.class);
 		i.putExtra("file1",code);
 		i.putExtra("species", species);
 		i.putExtra("pick", "2");
 		startActivity(i);
	}
	public void slideshow2(View view)
	{
		Intent i = new Intent(getApplicationContext(), Slide.class);
 		i.putExtra("file1",code);
 		i.putExtra("species", species);
 		i.putExtra("pick", "3");
 		startActivity(i);
	}
	public void email()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(Info.this);
		builder.setMessage("Share this screenshot? An image of this screenshot will be saved in your gallery\n and an option to share it now will be avaliable.").setPositiveButton("Yes", dialogClickListener1)
		.setNegativeButton("No", dialogClickListener1).show();
	}
	DialogInterface.OnClickListener dialogClickListener1 = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which){
			case DialogInterface.BUTTON_POSITIVE:
				share();

				break;

			case DialogInterface.BUTTON_NEGATIVE:
				//No button clicked
				break;
			}
		}
	};
	
	public void share()
	 {
		View view= findViewById(R.id.scrollView1);
		 View u = view.getRootView();
         u.setDrawingCacheEnabled(true);                                                
       
         Bitmap b = Bitmap.createBitmap(u.getDrawingCache());             
       
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
		
       /*  Intent i = new Intent(getApplicationContext(), Info.class);
   		i.putExtra("id",id);
   		i.putExtra("common", common);
   		i.putExtra("family", family);
   		i.putExtra("species", species);
   		startActivity(i);
   		finish();*/
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
	
	public void add()
	{
		
		   Intent i = new Intent(getApplicationContext(), Add.class);
	   		i.putExtra("common", common);
	   		i.putExtra("family", family);
	   		i.putExtra("species", species);
	   		i.putExtra("zone", zones);
	   		startActivity(i);
	}
	
	public void location()
	{
		 Intent i = new Intent(getApplicationContext(), Addloc.class);
	   		i.putExtra("common", common);
	   		i.putExtra("family", family);
	   		i.putExtra("species", species);
	   		i.putExtra("zone", zones);
	   		startActivity(i);
	}
	
	public void fav()
	{	
		AlertDialog.Builder builder = new AlertDialog.Builder(Info.this);
		builder.setMessage("Add "+species+ " to your favorites list?").setPositiveButton("Yes", dialogClickListener)
		.setNegativeButton("No", dialogClickListener).show();
		
		
	}
	
	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which){
			case DialogInterface.BUTTON_POSITIVE:
				try {
					DbHelper dbHelper = new DbHelper(Info.this);
					SQLiteDatabase db = dbHelper.getWritableDatabase();
					db.execSQL("INSERT INTO myfav (species,family,common,pid) VALUES ('"+species+"','"+family+"','"+common+"','"+id+"');");
					Toast.makeText(getApplicationContext(), "Plant Added", Toast.LENGTH_SHORT).show();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					Toast.makeText(getApplicationContext(), "Plant Already Added", Toast.LENGTH_SHORT).show();
				}

				break;

			case DialogInterface.BUTTON_NEGATIVE:
				//No button clicked
				break;
			}
		}
	};
}
