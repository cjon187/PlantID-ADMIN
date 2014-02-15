package com.kpu.plantid;








import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.kpu.plantidAdmin.R;

public class Search extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(android.R.style.Theme_Black);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setTitle("KPU Plant Database App");
		setTitleColor(Color.GREEN);
		
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
		Intent a = new Intent(getApplicationContext(),Favlist.class);
		startActivity(a);
	}
	
	public void query(View view)
	{
		runOnUiThread(new Runnable()
	    {   @Override
	        public void run()
	        {
	    	EditText e1 = (EditText)findViewById(R.id.editText1);
			
			String species = e1.getText().toString();
		
			Spinner z = (Spinner)findViewById(R.id.spinner2);
			String zone = z.getSelectedItem().toString();
			
			Spinner x = (Spinner)findViewById(R.id.spinner3);
			String disease = x.getSelectedItem().toString();
			
			Spinner o = (Spinner)findViewById(R.id.spinner10);
			String animal = o.getSelectedItem().toString();
			
			Spinner y = (Spinner)findViewById(R.id.spinner1);
			String  month = y.getSelectedItem().toString();
			
			Spinner w = (Spinner)findViewById(R.id.spinner4);
			String  flowerc = w.getSelectedItem().toString();
			
			Spinner v = (Spinner)findViewById(R.id.spinner5);
			String  leaff =v.getSelectedItem().toString();	
			
			Spinner q = (Spinner)findViewById(R.id.spinner6);
			String  leafa =q.getSelectedItem().toString();	
			
			Spinner n = (Spinner)findViewById(R.id.spinner7);
			String  type =n.getSelectedItem().toString();
			
			Spinner m = (Spinner)findViewById(R.id.spinner8);
			String  leafs =m.getSelectedItem().toString();
			
			Spinner t = (Spinner)findViewById(R.id.spinner9);
			String  fruitc =t.getSelectedItem().toString();
			
			String pest;
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
				Toast toast = Toast.makeText(Search.this, "You can only choose one pest susceptability.", Toast.LENGTH_LONG);
	            toast.setGravity(Gravity.CENTER, 0, 0);
	            toast.show();
	            x.setSelection(0);
	            o.setSelection(0);
			}
			
			else{
			
			
			Intent i = new Intent(getApplicationContext(), MainActivity.class);
	 		
	 		i.putExtra("species",species);
	 		i.putExtra("zone",zone);
	 		i.putExtra("pest",pest);
	 		i.putExtra("month", month);
	 		i.putExtra("flowerc",flowerc);
	 		i.putExtra("leaff",leaff);
	 		i.putExtra("leafa",leafa);
	 		i.putExtra("leafs",leafs);
	 		i.putExtra("type", type);
	 		i.putExtra("fruitc",fruitc);
	 		startActivity(i);
	        }
	        }
	        });//runonui 
		
	}
	
	public void clear(View view)
	{
		clear1();
	}
	
	public void clear1()
	{
		
		
		Spinner b = (Spinner)findViewById(R.id.spinner2);
		b.setSelection(0);
		Spinner c = (Spinner)findViewById(R.id.spinner3);
		c.setSelection(0);
		EditText e1 = (EditText)findViewById(R.id.editText1);
		e1.setText("");
		Spinner d = (Spinner)findViewById(R.id.spinner1);
		d.setSelection(0);
		Spinner r = (Spinner)findViewById(R.id.spinner4);
		r.setSelection(0);
		Spinner l = (Spinner)findViewById(R.id.spinner5);
		l.setSelection(0);
		Spinner q = (Spinner)findViewById(R.id.spinner6);
		q.setSelection(0);
		Spinner e = (Spinner)findViewById(R.id.spinner8);
		e.setSelection(0);
		Spinner rr = (Spinner)findViewById(R.id.spinner7);
		rr.setSelection(0);
		Spinner r1 = (Spinner)findViewById(R.id.spinner9);
		r1.setSelection(0);
		
		Spinner ra = (Spinner)findViewById(R.id.spinner10);
		ra.setSelection(0);
		
		
	}
	public void add(View v)
	{
		Intent a = new Intent(getApplicationContext(),Add.class );
		startActivity(a);
	}
}	
