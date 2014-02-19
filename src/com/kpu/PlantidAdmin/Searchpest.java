package com.kpu.PlantidAdmin;



import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.kpu.PlantidAdmin.R;

public class Searchpest extends Activity {
String animal,disease,species,pest;
Boolean jon = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(android.R.style.Theme_Black);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_searchpest);
		setTitle("KPU Pest Database");
		setTitleColor(Color.RED);
		
		
		//remove keyboard focus on start
		this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
				
	}
	public void onBackPressed() {
	    startActivity(new Intent(this, Start.class));
	    finish();
	}
	@Override
	protected void onPause()
	{
	    super.onPause();
	    System.gc();
	}
	public void fav(View view)
	{
		Intent a = new Intent(getApplicationContext(),Favpestlist.class);
		startActivity(a);
	}
	
	public void query(View view)
	{
		
	    	
	    	EditText e1 = (EditText)findViewById(R.id.editText1);
			
			species = e1.getText().toString();
								
			Spinner x = (Spinner)findViewById(R.id.spinner3);
			disease = x.getSelectedItem().toString();
			
			
			Spinner y = (Spinner)findViewById(R.id.spinner2);
			animal = y.getSelectedItem().toString();
			
			

						
			if (animal.equals("All"))
            {
            	animal="%";
            }
			if (disease.equals("All"))
            {
            	disease="%";
            }
			
			if (animal.equals("%") && disease.equals("%"))
			{
				pest = "%";
			}
			else
			{
				pest = animal+disease;
				pest.replaceAll("%", "");
			}
			
			
			
						
			if (!animal.equals("%")&&(!disease.equals("%")))
			{
				Toast toast = Toast.makeText(Searchpest.this, "You can only choose one pest susceptability.", Toast.LENGTH_LONG);
	            toast.setGravity(Gravity.CENTER, 0, 0);
	            toast.show();
	            clr();
			}
			
			
			else
			{
				next();
			}
			
		
	}
	 public void next()
	 {
		 Intent i = new Intent(getApplicationContext(), MainActivityPest.class);
	 		i.putExtra("species",species);
	 		i.putExtra("pest",pest);
	 		startActivity(i);
	 }
	
	
	public void clear(View view)
	{
		clr();
		
		
	}
	public void clr()
	{
		
		Spinner b = (Spinner)findViewById(R.id.spinner2);
		b.setSelection(0);
		Spinner c = (Spinner)findViewById(R.id.spinner3);
		c.setSelection(0);
		EditText e1 = (EditText)findViewById(R.id.editText1);
		
		e1.setText("");
	}
	public void add(View v)
	{
		Intent a = new Intent(getApplicationContext(),Add.class );
		startActivity(a);
	}
	
}	