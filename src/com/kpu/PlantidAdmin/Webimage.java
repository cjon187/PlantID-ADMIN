package com.kpu.PlantidAdmin;



import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import com.kpu.PlantidAdmin.R;

public class Webimage extends Activity {
	ProgressDialog prDialog;	
String image;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_webimage);
		Bundle extras = getIntent().getExtras();
		Button button1 = (Button)findViewById(R.id.button1);
		button1.getBackground().setColorFilter(0xFF33CC00, PorterDuff.Mode.MULTIPLY);	
		if (extras != null) {
			try{
				
				image = extras.getString("image");
				
			}
			catch(Exception e)
			{
				
			}
		}//if
		
		WebView w = (WebView)findViewById(R.id.webView1);		
		w.setWebViewClient(new WebViewClient() {
		    ProgressDialog prDialog;
		    @Override
		    public void onPageStarted(WebView view, String url, Bitmap favicon) {
		        prDialog = ProgressDialog.show(Webimage.this, null, "loading, please wait...");
		        super.onPageStarted(view, url, favicon);
		    }

		    @Override
		    public void onPageFinished(WebView view, String url) {
		        prDialog.dismiss();
		        super.onPageFinished(view, url);
		    }
		});

		w.loadUrl(image);
		w.getSettings().setSupportZoom(true);
        w.getSettings().setBuiltInZoomControls(true);


		
		
		//WebView w = (WebView)findViewById(R.id.webView1);
		//w.getSettings().setBuiltInZoomControls(true);
		//w.loadUrl(image);
		
	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.webimage, menu);
		return true;
	}
	
	
	
	public void back(View view)
	{
		finish();
	}
	private void handleOnBackButton() {
		prDialog.dismiss();
	  }
    
}
