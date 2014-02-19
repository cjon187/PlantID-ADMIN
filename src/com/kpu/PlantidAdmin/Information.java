package com.kpu.PlantidAdmin;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.regex.Pattern;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.text.Html;
import android.util.Patterns;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import com.kpu.PlantidAdmin.R;

public class Information extends Activity {
TextView msg;
String plantnumber,pestnumber,message,email,submits;
private static final String url = "jdbc:mysql://107.170.241.190:3306/plantid";
private static final String user = "aaron";
private static final String pass = "kpuaaron";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_information);
		msg = (TextView)findViewById(R.id.txtTicker1);
		msg.setSelected(true);
		setTitle("Plant Database Information");
		 //get android account
        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        Account[] accounts = AccountManager.get(getBaseContext()).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                email = account.name;

            }
        }
		new task().execute();
		
	}
	public void onBackPressed() {
	    startActivity(new Intent(this, Start.class));
	    finish();
	}

	class task extends AsyncTask<String, String, Void>
	{
		ProgressDialog progressDialog = new ProgressDialog(Information.this);
		InputStream is = null ;
		String result = "";
		@Override
		protected void onPreExecute() {
			progressDialog.setMessage("Starting, Please Wait..");
			progressDialog.setTitle("Plant Database...");
			progressDialog.show();





		}
		@Override
		protected Void doInBackground(String... params)
		{
			try {
				String query ="SELECT (select message from message) as msg,(Select count(*) from pest where user='"+email+"')as submits,(Select count(*) from pest) as pest,COUNT(*) as jon FROM plant";
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection(url, user, pass);
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(query);
				
				while (rs.next())
				{
						plantnumber=rs.getString("jon");
						pestnumber=rs.getString("pest");
						message=rs.getString("msg");
						submits=rs.getString("submits");
				}
				
				
				
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				

			return null;
		}
		


		@Override
		protected void onPostExecute(Void v)
		{
			progressDialog.dismiss();
			
			TextView t1=(TextView)findViewById(R.id.textView1);
			t1.setText("There are "+plantnumber+" plants in our database.");
			
			TextView t2=(TextView)findViewById(R.id.textView2);
			t2.setText("There are "+pestnumber+" pest images submitted to our databases.");
			
			
			TextView t3=(TextView)findViewById(R.id.textView3);
			t3.setText("You have submitted "+submits+" pest images to the database.");
			
			if (!message.equals("none"))
			{
				msg.setVisibility(View.VISIBLE);
				msg.setText("   "+message+"   ");
			}
			else
			{
				msg.setVisibility(View.GONE);
			}
			
		}
	}//task
	public void kpu(View view)
	{
		
		String url = "http://www.kpu.ca/hort";
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		startActivity(browserIntent);
	}

}
