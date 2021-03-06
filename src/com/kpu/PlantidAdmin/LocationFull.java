package com.kpu.PlantidAdmin;

import java.io.InputStream;
import java.sql.ResultSet;

import com.androidquery.AQuery;
import com.imagezoom.ImageAttacher;
import com.imagezoom.ImageAttacher.OnMatrixChangedListener;
import com.imagezoom.ImageAttacher.OnPhotoTapListener;

import com.kpu.PlantidAdmin.R;
import com.kpu.PlantidAdmin.Fullimage.task;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.RectF;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

public class LocationFull extends Activity {

String filename;
private static final String url = "jdbc:mysql://107.170.241.190:3306/plantid";
private static final String user = "aaron";
private static final String pass = "kpuaaron";
ResultSet rs1;
private AQuery aq;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.gc();
		setTheme(android.R.style.Theme_Light);
		setContentView(R.layout.activity_location_full);
		setTitleColor(Color.RED);
		aq = new AQuery(LocationFull.this);
		new task().execute();
	}
	
	@Override
	protected void onPause()
	{
	    super.onPause();
	    System.gc();
	}
	
	public void showimage()
	{
		if(getIntent().hasExtra("filename")) {
			try {
				
				Bundle extras = getIntent().getExtras();
				filename = extras.getString("filename");
			
				ImageView imageView = (ImageView)findViewById(R.id.jon12);
							
							filename = (filename.substring(filename.lastIndexOf("/") + 1));
							String url1 = "http://107.170.241.190/uploads/plantlocations/"+filename;
							 aq.id(R.id.webView1).progress(R.id.progressBar1).webImage(url1);
							usingSimpleImage(imageView);
	
			}
		catch (Exception e)
		{
			
		}
		}
	}
	
	

	class task extends AsyncTask<String, String, Void>
	{
		ProgressDialog progressDialog = new ProgressDialog(LocationFull.this);
		InputStream is = null ;
		String result = "";
		@Override
		protected void onPreExecute() {
			progressDialog.setMessage("Connecting, Please Wait..");
			progressDialog.setTitle("");
			progressDialog.show();





		}
		@Override
		protected Void doInBackground(String... params)
		{

			showimage();

			return null;
		}


		@Override
		protected void onPostExecute(Void v)
		{
			
			this.progressDialog.dismiss();
		}
	}//task
	
	//pinch view
	  public void usingSimpleImage(ImageView imageView) {
	        ImageAttacher mAttacher = new ImageAttacher(imageView);
	        ImageAttacher.MAX_ZOOM = 2.6f; // Double the current Size
	        ImageAttacher.MIN_ZOOM = 0.5f; // Half the current Size
	        MatrixChangeListener mMaListener = new MatrixChangeListener();
	        mAttacher.setOnMatrixChangeListener(mMaListener);
	        PhotoTapListener mPhotoTap = new PhotoTapListener();
	        mAttacher.setOnPhotoTapListener(mPhotoTap);
	    }

	    private class PhotoTapListener implements OnPhotoTapListener {

	        @Override
	        public void onPhotoTap(View view, float x, float y) {
	        }
	    }

	    private class MatrixChangeListener implements OnMatrixChangedListener {

	        @Override
	        public void onMatrixChanged(RectF rect) {

	        }
	    }//pinch view
}
