package com.kpu.PlantidAdmin;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.http.util.ByteArrayBuffer;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.google.android.gms.internal.i;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMap.OnInfoWindowClickListener;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.kpu.PlantidAdmin.R;

public class Kwantlen extends Activity {
	LatLng LOCATION1,LOCATION,myLocation;
	Location location;
	String species,common,family,latitude,longitude,id,id1;
	GoogleMap map;
	private static final String url = "jdbc:mysql://107.170.241.190:3306/plantid";
	private static final String user = "aaron";
	private static final String pass = "kpuaaron";
	ResultSet rs1;
	int count=0;
	private AQuery aq;
	Dialog alert;
	public static Activity fa1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kwantlen);
		fa1 = this;
		Bundle extras = getIntent().getExtras();
		 
		if (extras != null) {
			try{
				
				species = extras.getString("species");
				latitude=extras.getString("latitude");
				longitude=extras.getString("longitude");
				common = extras.getString("common");
				double lat1 = Double.parseDouble(latitude);
				double lon1 = Double.parseDouble(longitude);
				//LOCATION = new LatLng(lat1, lon1);
				map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
		        map.setMapType(GoogleMap.MAP_TYPE_HYBRID);
		        map.setMyLocationEnabled(true);
		        location= map.getMyLocation();
			}

			catch (Exception e)
			{
				Toast.makeText(getApplicationContext(), "ERROR on TExt" , Toast.LENGTH_LONG).show();
			}

			//show message
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Tap the markers twice to see the image thumbnail.")
                    .setCancelable(false)
                    .setTitle("Thumbnail")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            alert.dismiss();
                        }
                    });
            alert = builder.create();
            alert.show();
			
			
		//try        
		try{
				
								
				 String query ="select id,lat,lon,user,filename,des,scientific_name from plantlocation where scientific_name ='"+species+"' and common_name ='"+common+"'";
				
		            Class.forName("com.mysql.jdbc.Driver");
					Connection con1 = DriverManager.getConnection(url, user, pass);
					Statement st =con1.createStatement();
					rs1 =st.executeQuery(query);
				
					if (rs1.next()) {  
				        // here you know that there is at least one record, which is what your question was about  
				   do {  
				        // here you do whatever needs to be done for each record. Note that it will be called for the first record.
					   try {
							
							id1 = rs1.getString("id");
							final String user = rs1.getString("user");
							//get image from web
							String temp=rs1.getString("filename");
							String result = temp.substring(temp.lastIndexOf('/')+1).trim();
						    
							
							double lat = Double.parseDouble(rs1.getString("lat"));
							double lon = Double.parseDouble(rs1.getString("lon"));
							LOCATION1 = new LatLng(lat, lon);
							
							map.addMarker(new MarkerOptions()
							.title("#"+rs1.getString("id"))
							  .snippet(" "+rs1.getString("des")+"\n "+rs1.getString("user")+":"+result)
							  .position(LOCATION1)
							  .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
							count=count+1;
						
							map.moveCamera(CameraUpdateFactory.newLatLngZoom(LOCATION1, 11));
						
							
							//info adapter
							map.setInfoWindowAdapter(new InfoWindowAdapter() {

					            // Use default InfoWindow frame
					            @Override
					            public View getInfoWindow(Marker marker) {              
					                return null;
					            }           

					            // Defines the contents of the InfoWindow
					            @Override
					            public View getInfoContents(Marker marker) {

					                // Getting view from the layout file info_window_layout
					                View v = getLayoutInflater().inflate(R.layout.superview, null);

					                // Getting reference to the TextView to set title
					                aq = new AQuery(v);
					                TextView note1 = (TextView) v.findViewById(R.id.TextView01);
					                TextView note = (TextView) v.findViewById(R.id.note);
					                
					                String snip = marker.getSnippet().toString();
					                
					                Log.d("snip",snip);
					                
					                String[] parts = snip.split(":");
					                String part1 = parts[0]; //email
					                String part2 = parts[1]; //file
					                
					                note.setText(Html.fromHtml("<b>"+marker.getTitle()+"</b>"));
									note1.setText(part1);
									            
									
									String d = "http://107.170.241.190/uploads/plantlocations/"+part2;
									
									aq.id(R.id.imageView1).image(d, true, true, 200, 200).visible();		

					                // Returning the view containing InfoWindow contents
					                return v;

					            }

					        });
							 
							
							
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				    } while (rs1.next());  
				} else {  
				    // here you do whatever needs to be done when there is no record  
					 Toast toast = Toast.makeText(Kwantlen.this, "No plants locations added yet.", Toast.LENGTH_LONG);
			            toast.setGravity(Gravity.CENTER, 0, 0);
			            toast.show();
					finish();
					
				}  		
				
					
								
				//map.moveCamera(CameraUpdateFactory.newLatLngZoom(LOCATION, 11));
				setTitle(count+ " Locations Found");
			}
			catch (Exception e)
			{
				
			}//try

		}
		map.setOnInfoWindowClickListener(new OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
               
            	String temp = marker.getTitle().toString();
                String result = temp.substring(temp.lastIndexOf('#')+1).trim();
               
               try {
				String query ="select common_name,family_name,id from plantlocation where id ='"+result+"'";
					
				    Class.forName("com.mysql.jdbc.Driver");
					Connection con2 = DriverManager.getConnection(url, user, pass);
					Statement st2 =con2.createStatement();
					ResultSet rs2 =st2.executeQuery(query);
					while (rs2.next())
					{
						common = rs2.getString("common_name");
						family = rs2.getString("family_name");
						id = rs2.getString("id");
					
					}
					
					
					
					
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
               
               Intent intent = new Intent(Kwantlen.this,InfoLocationMap.class);
               intent.putExtra("id",id);
               intent.putExtra("common",common);
               intent.putExtra("family",family);
               intent.putExtra("species",species);
               startActivity(intent);
               
            }
        });
	}

	private void centerMapOnMyLocation() {

	    map.setMyLocationEnabled(true);

	    location = map.getMyLocation();

	    if (location != null) {
	        myLocation = new LatLng(location.getLatitude(),
	                location.getLongitude());
	    }
	    map.animateCamera(CameraUpdateFactory.newLatLngZoom(myLocation,
	            15));
	}
	
	//download url
	
}
