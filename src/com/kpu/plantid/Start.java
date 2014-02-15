package com.kpu.plantid;


import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.kpu.plantidAdmin.R;

public class Start extends Activity {

Button a,b;
String plantnumber,pestnumber,message;
private static final String url = "jdbc:mysql://107.170.241.190:3306/plantid";
private static final String user = "aaron";
private static final String pass = "kpuaaron";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTheme(android.R.style.Theme_Black);
		setContentView(R.layout.activity_start);
		setTitle("KPU Plant Database App");
		System.gc();
		a = (Button)findViewById(R.id.button1);
		b  =(Button)findViewById(R.id.button2);
		
		
		
								
		
		
		/*ImageView img_animation = (ImageView) findViewById(R.id.imageView1);

	    TranslateAnimation animation = new TranslateAnimation(0.0f, 400.0f,
	            0.0f, 0.0f);          //  new TranslateAnimation(xFrom,xTo, yFrom,yTo)
	    animation.setDuration(10000);  // animation duration 
	    animation.setRepeatCount(5);  // animation repeat count
	    animation.setRepeatMode(2);   // repeat animation (left to right, right to left )
	    //animation.setFillAfter(true);      

	    img_animation.startAnimation(animation);  // start animation*/
		
		
		//RotateAnimation anim = new RotateAnimation(0f, 350f, 15f, 15f);
		//anim.setInterpolator(new LinearInterpolator());
		//anim.setRepeatCount(1);
		//anim.setDuration(700);

		// Start animating the image
		//final ImageView splash = (ImageView) findViewById(R.id.imageView1);
		//splash.startAnimation(anim);

		// Later.. stop the animation
		//splash.setAnimation(null);
	}
	@Override
	protected void onPause()
	{
	    super.onPause();
	    System.gc();
	}
	
	
	
	public void plant(View view)
	{
		Intent a = new Intent(getApplicationContext(),MainActivityPest.class);
		startActivity(a);
		global.a=1;
		
	}

	public void pest(View view)
	{
		Intent b = new Intent(getApplicationContext(),MainActivityPest.class);
		startActivity(b);
		global.a=2;
		
	}
	public void info(View view)
	{
		Intent b = new Intent(getApplicationContext(),Information.class);
		startActivity(b);
		finish();
	}
	
}
