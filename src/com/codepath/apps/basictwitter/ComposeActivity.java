package com.codepath.apps.basictwitter;

import org.json.JSONObject;

import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class ComposeActivity extends Activity {

	TextView tvScreenName;
	TextView tvUserName;
	EditText etCompose;
	ImageView ivProfileImage;
	Button post;
	private TwitterClient client;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		String screenName = getIntent ().getStringExtra("screenName");
		String userName = getIntent ().getStringExtra("userName");
		setupViews (screenName, userName);
		client = TwitterApplication.getRestClient();
	}
	
	public void setupViews (String screenName, String userName)
	{
		etCompose = (EditText)findViewById(R.id.etComposeText);
		post = (Button)findViewById (R.id.post);
		tvScreenName = (TextView)findViewById (R.id.tvComposeScreenName);
		tvUserName = (TextView)findViewById(R.id.tvComposeUserName);
		ivProfileImage = (ImageView)findViewById(R.id.ivComposeProfileImage);
		
		tvScreenName.setText(screenName);
		tvUserName.setText(userName);
		
	}
	
	public void onPost (View v)
	{
		Intent i = new Intent ();
		String msg = etCompose.getText().toString();

		i.putExtra ("msg", msg);
		setResult(RESULT_OK, i);
		finish ();
	}
}
