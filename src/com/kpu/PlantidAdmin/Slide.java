package com.kpu.PlantidAdmin;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.androidquery.AQuery;
import com.imagezoom.ImageAttacher;
import com.imagezoom.ImageAttacher.OnMatrixChangedListener;
import com.imagezoom.ImageAttacher.OnPhotoTapListener;
import com.kpu.PlantidAdmin.R;




import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.Toast;

public class Slide extends Activity {
String file1,species,pick;
private AQuery aq;

int counter=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(android.R.style.Theme_Black);
		setContentView(R.layout.activity_slide);
		setTitleColor(Color.GREEN);
		System.gc();
		aq = new AQuery(Slide.this);
		Bundle extras = getIntent().getExtras();
		ImageView imageView1 = null,imageView2,imageView3,imageView4,imageView5,imageView6 = null,imageView7;
		ImageView imageView8,imageView9,imageView10,imageView11 = null,imageView12,imageView13,imageView14;
		
		if (extras != null) {
			try{
				file1 = extras.getString("file1");
				 species = extras.getString("species");
				pick = extras.getString("pick");
			}

			catch (Exception e)
			{
				Toast.makeText(getApplicationContext(), "ERROR on TExt" , Toast.LENGTH_LONG).show();
			}
		}
		
		
		//show image
		
		String url1 = "http://107.170.241.190/plantdb/l/"+file1+"h1.jpg";  
		 imageView1 = (ImageView)findViewById(R.id.imageView1);
        aq.id(R.id.imageView1).image(url1).visible();
        usingSimpleImage(imageView1);
		
        String url2 = "http://107.170.241.190/plantdb/l/"+file1+"h2.jpg";  
		 imageView2 = (ImageView)findViewById(R.id.imageView2);
        aq.id(R.id.imageView2).image(url2).visible();
        usingSimpleImage(imageView2);
        
        String url3 = "http://107.170.241.190/plantdb/l/"+file1+"h3.jpg";  
		 imageView3 = (ImageView)findViewById(R.id.imageView3);
        aq.id(R.id.imageView3).progress(R.id.progressBar1).image(url3).visible();
        usingSimpleImage(imageView3);
        
        String url4 = "http://107.170.241.190/plantdb/l/"+file1+"h4.jpg";  
		 imageView4 = (ImageView)findViewById(R.id.imageView4);
        aq.id(R.id.imageView4).image(url4).visible();
        usingSimpleImage(imageView4);
        
        String url5 = "http://107.170.241.190/plantdb/l/"+file1+"h5.jpg";  
		 imageView5 = (ImageView)findViewById(R.id.imageView5);
        aq.id(R.id.imageView5).image(url5).visible();
        usingSimpleImage(imageView5);
        
        String url6 = "http://107.170.241.190/plantdb/l/"+file1+"l1.jpg";  
		 imageView6 = (ImageView)findViewById(R.id.imageView6);
        aq.id(R.id.imageView6).image(url6).visible();
        usingSimpleImage(imageView6);
        
        String url7 = "http://107.170.241.190/plantdb/l/"+file1+"l2.jpg";  
		 imageView7 = (ImageView)findViewById(R.id.imageView7);
        aq.id(R.id.imageView7).image(url7).visible();
        usingSimpleImage(imageView7);
        
        String url8 = "http://107.170.241.190/plantdb/l/"+file1+"l3.jpg";  
		 imageView8 = (ImageView)findViewById(R.id.imageView8);
        aq.id(R.id.imageView8).image(url8).visible();
        usingSimpleImage(imageView8);
        
        String url9 = "http://107.170.241.190/plantdb/l/"+file1+"f1.jpg";  
		 imageView9 = (ImageView)findViewById(R.id.imageView9);
        aq.id(R.id.imageView9).image(url9).visible();
        usingSimpleImage(imageView9);
        
        String url10 = "http://107.170.241.190/plantdb/l/"+file1+"f2.jpg";  
		 imageView10 = (ImageView)findViewById(R.id.imageView10);
        aq.id(R.id.imageView10).image(url10).visible();
        usingSimpleImage(imageView10);
        
        String url11 = "http://107.170.241.190/plantdb/l/"+file1+"k.jpg";  
		 imageView11 = (ImageView)findViewById(R.id.imageView11);
        aq.id(R.id.imageView11).image(url11).visible();
        usingSimpleImage(imageView11);
        
        String url12 = "http://107.170.241.190/plantdb/l/"+file1+"o.jpg";  
		imageView12 = (ImageView)findViewById(R.id.imageView12);
        aq.id(R.id.imageView12).image(url12).visible();
        usingSimpleImage(imageView12);
        
        String url13 = "http://107.170.241.190/plantdb/l/"+file1+"t1.jpg";  
		 imageView13 = (ImageView)findViewById(R.id.imageView13);
        aq.id(R.id.imageView13).image(url13).visible();
        usingSimpleImage(imageView13);
        
        String url14 = "http://107.170.241.190/plantdb/l/"+file1+"t2.jpg";  
		imageView14 = (ImageView)findViewById(R.id.imageView14);
        aq.id(R.id.imageView14).image(url14).visible();
        usingSimpleImage(imageView14);
        
        
        
		//display image count
		setTitle(" Images of "+species);
		
			
		

	}
	@Override
	protected void onPause()
	{
	    super.onPause();
	    System.gc();
	}
	
	
	
	
	
 
	
//pinch view
  public void usingSimpleImage(ImageView imageView) {
	  
        try {
			ImageAttacher mAttacher = new ImageAttacher(imageView);
			ImageAttacher.MAX_ZOOM = 2.0f; // Double the current Size
			ImageAttacher.MIN_ZOOM = 0.5f; // Half the current Size
			MatrixChangeListener mMaListener = new MatrixChangeListener();
			mAttacher.setOnMatrixChangeListener(mMaListener);
			PhotoTapListener mPhotoTap = new PhotoTapListener();
			mAttacher.setOnPhotoTapListener(mPhotoTap);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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

