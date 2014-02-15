package com.kpu.plantid;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.ExifInterface;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.kpu.plantidAdmin.R;
import com.mysql.jdbc.PreparedStatement;

public class Add extends Activity {
    static final int idIntentid = Menu.FIRST + 2;
    private static final String url = "jdbc:mysql://107.170.241.190:3306/plantid";
    private static final String user = "aaron";
    private static final String pass = "kpuaaron";

    private int serverResponseCode = 0;
    Float Latitude, Longitude;
    PreparedStatement ps;
    String path;
    String fam, species, common, des, pest, zone, email,animal,disease;
    String dateString;
    Float lat, lon;
    ProgressDialog progressDialog;
    AlertDialog alert;
    String outPath, lonString, latString;
    Uri selectedImage = null;
    File imageFile = null;
    
    InputStream imageStream = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(android.R.style.Theme_Light);
        setTitle("Add Pest Image");
        System.gc();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        setTitleColor(Color.GREEN);
        if (imageStream != null)
            try {
                imageStream.reset();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            try {

                common = extras.getString("common");
                species = extras.getString("species");
                fam = extras.getString("family");
                zone = extras.getString("zone");
                
                TextView s = (TextView) findViewById(R.id.textView7);
                TextView c = (TextView) findViewById(R.id.textView8);

               
                s.setText(species);
                c.setText(common);

            } catch (Exception e) {

            }
        }
        //get android account
        Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
        Account[] accounts = AccountManager.get(getBaseContext()).getAccounts();
        for (Account account : accounts) {
            if (emailPattern.matcher(account.name).matches()) {
                email = account.name;

            }
        }

        load();


    }

    @Override
    protected void onPause() {
        super.onPause();
        System.gc();
    }
   
    public void choose(View view)
    {
    	load();
    }
    public void load() {

        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, idIntentid);


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);

        }
    }



    public void upload(View view) {


        
        EditText e3 = (EditText) findViewById(R.id.editText3);
        Spinner p = (Spinner) findViewById(R.id.spinner2);
        Spinner o = (Spinner) findViewById(R.id.spinner3);
        disease = p.getSelectedItem().toString();
        animal=o.getSelectedItem().toString();
        des = e3.getText().toString();
        
        if (disease.equals("NONE") && animal.equals("NONE"))
        	{
        	pest="NONE";
        	}
        if (disease.equals("NONE") && !animal.equals("NONE"))
    	{
    	pest=animal;
    	}
        if (!disease.equals("NONE") && animal.equals("NONE"))
    	{
    	pest=disease;
    	}
        
        if (!disease.equals("NONE") && !animal.equals("NONE"))
        	{
        	Toast toast = Toast.makeText(Add.this, "You can only choose one pest susceptability.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            p.setSelection(0);
            o.setSelection(0);
        	}
        else{//2nd else
       

        if (des.isEmpty() || pest.equals("NONE")) {
            Toast toast = Toast.makeText(Add.this, "You are missing a description or a pest susceptability.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(Add.this);
            builder.setMessage("ADD \nFamily Name: " + fam + "\nScientific Name: " + species + "\nCommon Name: " + common +
                    "\nPest Susceptability: " + pest + "\nHardiness Zone: " + zone + "\nDescription: " + des + "\nContributed By: " + email).setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }//else
    }//2nd else
    }

    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case DialogInterface.BUTTON_POSITIVE:
                    new task3().execute();

                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        }
    };


    class task3 extends AsyncTask<String, String, Void> {
        ProgressDialog progressDialog = new ProgressDialog(Add.this);
        InputStream is = null;
        String result = "";
		

        @Override
        protected void onPreExecute() {
            progressDialog.setMessage("Connecting, Please Wait..");
            progressDialog.setTitle("Pest Database...");
            progressDialog.show();


        }

        @Override
        protected Void doInBackground(String... params) {

            try {
                upload();
                
            } catch (ClassNotFoundException e) {
            	this.progressDialog.dismiss();
                e.printStackTrace();
            } catch (FileNotFoundException e) {
            	this.progressDialog.dismiss();
                e.printStackTrace();
            } catch (SQLException e) {
            	this.progressDialog.dismiss();
                e.printStackTrace();
            } catch (IOException e) {
            	this.progressDialog.dismiss();
                e.printStackTrace();
            }

            return null;
        }


        @Override
        protected void onPostExecute(Void v) {

            this.progressDialog.dismiss();
            Toast toast = Toast.makeText(Add.this, "Succesful Upload", 5);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            Intent openMainActivity = new Intent(getApplicationContext(), Start.class);
            openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(openMainActivity);
            finish();
        }
    }//task

    public void upload() throws ClassNotFoundException, SQLException, IOException {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, pass);
         
            String insert = "insert into pest(scientific_name,family_name,common_name,img,lat,lon,des,pest,zone,user,filename,dd) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";


            EditText e3 = (EditText) findViewById(R.id.editText3);

            des = e3.getText().toString();
           
           
            
            
           File file = new File(getRealPathFromURI(selectedImage));
           //get the filename
           
           String ifilename = getRealPathFromURI(selectedImage);
          
           FileInputStream fis = new FileInputStream(file);
           con.setAutoCommit(false);

            ps = (PreparedStatement) con.prepareStatement(insert);
            ps.setString(1, species);
            ps.setString(2, fam);
            ps.setString(3, common);
            ps.setBinaryStream(4, fis, (int) file.length());
            ps.setString(5, latString);
            ps.setString(6, lonString);
            ps.setString(7, des);
            ps.setString(8, pest);
            ps.setString(9, zone);
            ps.setString(10, email);
            ps.setString(11, ifilename);
            ps.setString(12, dateString);
            ps.executeUpdate();
            con.commit();
            ps.close();
            fis.close();
            
            
            Log.d("file",ifilename);
            JSONObject json;
        	JSONParser jsonParser = new JSONParser();
        	String url_create_product = "http://107.170.241.190/uploads/clone.php";
        	 List<NameValuePair> params = new ArrayList<NameValuePair>();
             params.add(new BasicNameValuePair("new", ifilename));
            json = jsonParser.makeHttpRequest(url_create_product,
                     "POST", params);
            
            
            
        }//try original
        catch (Exception e) {

            
        }


    }


   

	public void reload(View view) {

        Intent a = new Intent(getApplicationContext(), Add.class);
        startActivity(a);
        finish();
    }

    private Float convertToDegree1(String stringDMS) {
        Float result = null;


        String[] DMS = stringDMS.split(",", 3);

        String[] stringD = DMS[0].split("/", 2);
        Double D0 = new Double(stringD[0]);
        Double D1 = new Double(stringD[1]);
        Double FloatD = D0 / D1;

        String[] stringM = DMS[1].split("/", 2);
        Double M0 = new Double(stringM[0]);
        Double M1 = new Double(stringM[1]);
        Double FloatM = M0 / M1;

        String[] stringS = DMS[2].split("/", 2);
        Double S0 = new Double(stringS[0]);
        Double S1 = new Double(stringS[1]);
        Double FloatS = S0 / S1;

        result = new Float(FloatD + (FloatM / 60) + (FloatS / 3600));

        return result;


    };


    @SuppressWarnings("deprecation")
    @Override

    protected void onActivityResult(int requestCode, int resultcode, Intent intent) {
        super.onActivityResult(resultcode, resultcode, intent);
        try {
            if (requestCode == idIntentid) {

                selectedImage = intent.getData();

                try {
                    imageStream = getContentResolver().openInputStream(selectedImage);
                    int length = imageStream.available();


                } catch (Exception e) {
                    Toast toast = Toast.makeText(Add.this, "Error", 5);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    finish();

                }


                imageFile = new File(getRealPathFromURI(selectedImage));


                try {
                    ExifInterface exif = new ExifInterface(imageFile.getAbsolutePath());
                    String LATITUDE = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE);
                    String LATITUDE_REF = exif.getAttribute(ExifInterface.TAG_GPS_LATITUDE_REF);
                    String LONGITUDE = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE);
                    String LONGITUDE_REF = exif.getAttribute(ExifInterface.TAG_GPS_LONGITUDE_REF);
                    dateString = exif.getAttribute(ExifInterface.TAG_DATETIME);
                    
                  //date format
                    Log.d("first",dateString);
                    SimpleDateFormat fromUser = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
                    SimpleDateFormat myFormat = new SimpleDateFormat("dd-MMM-yyyy hh:mm a");
                    
                    try {

                        String reformattedStr = myFormat.format(fromUser.parse(dateString));
                        dateString=reformattedStr;
                        Log.d("second",dateString);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    

                    if ((LATITUDE != null)
                            && (LATITUDE_REF != null)
                            && (LONGITUDE != null)
                            && (LONGITUDE_REF != null)) {

                        if (LATITUDE_REF.equals("N")) {
                            Latitude = convertToDegree(LATITUDE);
                        } else {
                            Latitude = 0 - convertToDegree(LATITUDE);
                        }

                        if (LONGITUDE_REF.equals("E")) {
                            Longitude = convertToDegree(LONGITUDE);
                        } else {
                            Longitude = 0 - convertToDegree(LONGITUDE);
                        }
                        latString = String.valueOf(Latitude);
                        lonString = String.valueOf(Longitude);
                        
                    } else {
                        //if not
                        latString = "none";
                        lonString = "none";
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage("There was no gps meta-data in this image. You can still submit this image but the location will not be provided. \nEnable GPS tag or GPS Sharing on camera to submit images with locations.")
                                .setCancelable(false)
                                .setTitle("No GPS Location")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        alert.dismiss();
                                    }
                                });
                        alert = builder.create();
                        alert.show();
                        
                        
                        
                    }


                } catch (Exception e) {

                }
                try {
                    //display image

                    final BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize =8;

                    Bitmap yourSelectedImage = BitmapFactory.decodeStream(getContentResolver().openInputStream(selectedImage), null, options);

                    ImageView iv = (ImageView) findViewById(R.id.jon12);
                    iv.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                    iv.setAdjustViewBounds(true);
                    iv.setImageBitmap(yourSelectedImage);

                    

                } catch (Exception e) {

                    Toast toast = Toast.makeText(Add.this, "Error", 5);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    finish();
                }


            }
        }//try
        catch (Exception e) {
            finish();
        }
    }


    private String getRealPathFromURI(Uri contentURI) {
        Cursor cursor = getContentResolver()
                .query(contentURI, null, null, null, null);
        try {


            if (cursor == null)
                return contentURI.getPath();
            else {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                return cursor.getString(idx);
            }
        } finally {

            cursor.close();
        }

    }

    //converting
    private Float convertToDegree(String stringDMS) {
        Float result = null;
        String[] DMS = stringDMS.split(",", 3);

        String[] stringD = DMS[0].split("/", 2);
        Double D0 = new Double(stringD[0]);
        Double D1 = new Double(stringD[1]);
        Double FloatD = D0 / D1;

        String[] stringM = DMS[1].split("/", 2);
        Double M0 = new Double(stringM[0]);
        Double M1 = new Double(stringM[1]);
        Double FloatM = M0 / M1;

        String[] stringS = DMS[2].split("/", 2);
        Double S0 = new Double(stringS[0]);
        Double S1 = new Double(stringS[1]);
        Double FloatS = S0 / S1;

        result = new Float(FloatD + (FloatM / 60) + (FloatS / 3600));

        return result;


    };
    
    public  String EncodeToString(Bitmap image)
    {                   
         Bitmap immagex=image;
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         immagex.compress(Bitmap.CompressFormat.JPEG, 100, baos);
         byte[] b = baos.toByteArray();
         String imageEncoded = Base64.encodeToString(b,Base64.DEFAULT);            
         return imageEncoded;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return (String.valueOf(Latitude)
                + ", "
                + String.valueOf(Longitude));
    }

    public int getLatitudeE6() {
        return (int) (Latitude * 1000000);
    }

    public int getLongitudeE6() {
        return (int) (Longitude * 1000000);
    }

  

}


