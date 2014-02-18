package com.kpu.plantid;

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
import java.sql.SQLException;
import java.util.regex.Pattern;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Patterns;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.kpu.plantidAdmin.R;
import com.mysql.jdbc.Statement;

public class Infopest extends Activity {
	String common,species,family,webimage,id,email1,emailcheck,description,filename;
	ResultSet rs1;
	String url1;
	private AQuery aq;
	private static final String url = "jdbc:mysql://107.170.241.190:3306/plantid";
	private static final String user = "aaron";
	private static final String pass = "kpuaaron";
	String file1;
	int mState=0;
	Drawable d;
	int size =2;
	String jon,latitude,longitude;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		
		setTheme(android.R.style.Theme_Holo_Light);
		System.gc();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_infopest);
		setTitleColor(Color.RED);
		//get the users android account
		Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
		Account[] accounts = AccountManager.get(getBaseContext()).getAccounts();
		for (Account account : accounts) {
		    if (emailPattern.matcher(account.name).matches()) {
		        emailcheck = account.name;
		       
		    }
		}
		
		
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}

		//scale image
		
		
		
		new task3().execute();
		
				
			
	}
	@Override
	protected void onPause()
	{
	    super.onPause();
	    System.gc();
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	 /*   switch (item.getItemId()) {
	        case R.id.new_game:
	            map();
	            return true;
	        case R.id.main:
		            gotomain();
		            return true;
	        case R.id.share:
	            email();
	            return true;
	        case R.id.fav:
	            fav();
	            return true;
	        case R.id.report:
	            report();
	            return true;
	            
	        default:
	            return super.onOptionsItemSelected(item);*/
		return false;
	    }
	
	
	public void email()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(Infopest.this);
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
	
	public void report()
	{
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("message/rfc822");
		i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"cjon187@gmail.com"});
		i.putExtra(Intent.EXTRA_SUBJECT, "Error in Pest for id= "+id);
		i.putExtra(Intent.EXTRA_TEXT   , "");
		try {
		    startActivity(Intent.createChooser(i, "Send mail..."));
		} catch (android.content.ActivityNotFoundException ex) {
		    Toast.makeText(Infopest.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
		}
	}
	public void share()
	 {
		View view= findViewById(R.id.scrollView1);
		 View u = view.getRootView();
         u.setDrawingCacheEnabled(true);                                                
       
         Bitmap b = Bitmap.createBitmap(u.getDrawingCache());             
       

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
		
         Intent i = new Intent(getApplicationContext(), Infopest.class);
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
	
	public void gotomain()
	{
		Intent intent = new Intent(this, Start.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
	}
	public void add()
	{
		Intent a = new Intent(getApplicationContext(),Add.class);
		startActivity(a);
		finish();
	}
	public void image(View view)
	{
		Intent i = new Intent(getApplicationContext(), Webimage.class);
 		i.putExtra("image",webimage);
 		startActivity(i);
		
	}
	
	public void remove(View view)
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(Infopest.this);
		builder.setMessage("Delete "+species+ " From your entry from database?").setPositiveButton("Yes", dialogClickListener)
		.setNegativeButton("No", dialogClickListener).show();
	}
		
	DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which){
			case DialogInterface.BUTTON_POSITIVE:
				try {
					Class.forName("com.mysql.jdbc.Driver");
					Connection con = DriverManager.getConnection(url, user, pass);
					Statement st1 = (Statement) con.createStatement();
					if (global.a==1)
					{
					st1.executeUpdate("DELETE from pest where id = '"+id+"'");
					}
					if (global.a==2)
					{
						st1.executeUpdate("DELETE from plantlocation where id = '"+id+"'");

					}
					
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				MainActivityPest.fa.finish();
				finish();
				break;

			case DialogInterface.BUTTON_NEGATIVE:
				//No button clicked
				break;
			}
		}
	};//dialog
	
	
		
	
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
	
	public void update (View view)
	{
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pass);
			Statement st1 = (Statement) con.createStatement();
			EditText e1=(EditText)findViewById(R.id.editText1);
			String update1=e1.getText().toString();
			if (global.a==1)
			{
			st1.executeUpdate("UPDATE pest SET des='"+update1+"' WHERE id="+id+"");
			}
			if (global.a==2)
			{
				st1.executeUpdate("UPDATE plantlocation SET des='"+update1+"' WHERE id="+id+"");
			}
			MainActivityPest.fa.finish();
			finish();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void google(View view)
	{
		
		String url = "http://www.google.ca/search?hl=en&imgtbs=zt&q=`"+common+" "+species;
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		startActivity(browserIntent);
	}
	
	public void map()
	
	{
	
		if (!latitude.equals("none"))
	{
		
		Intent a = new Intent(getApplicationContext(),PestMap.class);
		a.putExtra("species",species);
		a.putExtra("latitude",latitude);
		a.putExtra("longitude",longitude);
		
		startActivity(a);
		//finish();
	}
	else{
		
		Toast toast = Toast.makeText(Infopest.this,"There is no location for this image.", Toast.LENGTH_LONG);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.show();
	}
		
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
					setTitle(Html.fromHtml("<b><i>"+species+"</b></i>"));
				
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
			
			//strip common
			common = common.trim();
			common = common.replace("'","''");
			Statement st1 = (Statement) con.createStatement();
			
			if (global.a==1)
			{
				rs1 = st1.executeQuery("select dd,lat,lon,des,zone,user,pest,family_name,common_name,filename from pest where id = '"+id+"'");

			}
			if (global.a==2)
			{
			rs1 = st1.executeQuery("select dd,lat,lon,des,zone,user,pest,family_name,common_name,filename from plantlocation where id = '"+id+"'");
			}
						
			while (rs1.next())
			{
				
				final String lat = rs1.getString("lat");
				final String lon = rs1.getString("lon");
				final String des = rs1.getString("des");
				final String zone = rs1.getString("zone");
				final String email = rs1.getString("user");
				final String pesttype=rs1.getString("pest");
				final String family1=rs1.getString("family_name");
				final String common1=rs1.getString("common_name");
				final String fn = rs1.getString("filename");
				final String date = rs1.getString("dd");
				filename = fn;
				latitude = lat;
				longitude = lon;
				description = des;
				email1=email;
				
				//resize image to fit memory
				
				
				runOnUiThread(new Runnable()
				{   

				@Override
					public void run()
				{
					
					TextView text1 = (TextView)findViewById(R.id.textView2);
					text1.setText(Html.fromHtml("<b>Family Name: </font></b>"+family1));
					TextView text2= (TextView)findViewById(R.id.textView4);
					text2.setText(Html.fromHtml("<b>Scientific Name: </font></b>"+species));
					TextView text3 = (TextView)findViewById(R.id.textView6);
					text3.setText(Html.fromHtml("<b>Common Name: </font></b>"+common1));
					
					
					
					TextView text= (TextView)findViewById(R.id.textView8);
					text.setText(Html.fromHtml("<b>Description: </font></b>"+des));
					TextView text11= (TextView)findViewById(R.id.textView10);
					text11.setText(Html.fromHtml("<b>Hardiness Zone: </font></b>"+zone));
					TextView user2= (TextView)findViewById(R.id.user);
					user2.setText(Html.fromHtml("<b>Contributed By: </font></b>"+email));
					TextView pest= (TextView)findViewById(R.id.textView1);
					pest.setText(Html.fromHtml("<b>Pest Susceptability: </font></b>"+pesttype));
					TextView date1= (TextView)findViewById(R.id.date);
					date1.setText(Html.fromHtml("<b>Date Submitted: </font></b>"+date));
					
					EditText e1 = (EditText)findViewById(R.id.editText1);
					e1.setText(des);
					
					aq = new AQuery(Infopest.this);
					filename = (filename.substring(filename.lastIndexOf("/") + 1));
					if(global.a==1)
					{
						url1 = "http://107.170.241.190/uploads/uploads/"+filename;
					}
					if (global.a==2)
					{
					url1 = "http://107.170.241.190/uploads/plantlocations/"+filename;
					}
					aq.id(R.id.jon12).image(url1).visible();
				
					
					
					
					
						//admin can see
						Button b = (Button)findViewById(R.id.button2);
						b.setVisibility(View.VISIBLE);
						
					
			
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
		
		getMenuInflater().inflate(R.menu.infopest, menu);
		// inflate menu from xml
	   
		return true;
	}
	class task3 extends AsyncTask<String, String, Void>
	{
  		ProgressDialog progressDialog = new ProgressDialog(Infopest.this);
  		
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
	public void back(View view)
	{
		finish();
	}
	
	public void menu(View view)
	{
		
		Infopest.this.openOptionsMenu();
	}
	
	public void slideshow(View view)
	{
		Intent i = new Intent(this, Fullimage.class);
		i.putExtra("filename", filename);
       
		startActivity(i);
	}
	
	public void fav()
	{	
		AlertDialog.Builder builder = new AlertDialog.Builder(Infopest.this);
		builder.setMessage("Add "+species+ " to your favorites list?").setPositiveButton("Yes", dialogClickListener2)
		.setNegativeButton("No", dialogClickListener2).show();
		
		
	}
	
	DialogInterface.OnClickListener dialogClickListener2 = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			switch (which){
			case DialogInterface.BUTTON_POSITIVE:
				try {
					DbHelper dbHelper1 = new DbHelper(Infopest.this);
					SQLiteDatabase db1 = dbHelper1.getWritableDatabase();
					db1.execSQL("INSERT INTO mypest (species,family,common,pid) VALUES ('"+species+"','"+description+"','"+common+"','"+id+"');");
					Toast.makeText(getApplicationContext(), "Plant Added", Toast.LENGTH_SHORT).show();
				} catch (Exception e) {
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
