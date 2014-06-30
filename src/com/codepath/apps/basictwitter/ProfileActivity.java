package com.codepath.apps.basictwitter;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.fragments.UserTimelineFragment;
import com.codepath.apps.basictwitter.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);
		String screenName = getIntent().getStringExtra("screenName");
		if (screenName != null)
		{
			//getActionBar().setTitle("@" + screenName);
		}
		loadProfile (screenName);
    	android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
    	
    	FragmentTransaction ft = manager.beginTransaction();
    	UserTimelineFragment fragment = new UserTimelineFragment ();
    	fragment.setScreenName(screenName);
    	ft.replace(R.id.flTimelineContainer, fragment);
    	ft.commit();
	}
	
	private void loadProfile (String screenName)
	{
		TwitterApplication.getRestClient().getProfile(new JsonHttpResponseHandler()
		{
			public void onSuccess(int arg0, org.json.JSONObject object) 
			{
				User u = User.fromJson(object);
				getActionBar().setTitle("@" + u.getScreenName());
				populateProfileHeader (u);
			};
			public void onFailure(Throwable err) 
			{
				Log.d("DEBUG", "error in getting profile " + err.getMessage());
			};
		}
				,screenName);
	}

	protected void populateProfileHeader(User u) {
		TextView tvName = (TextView)findViewById(R.id.tvName);
		TextView tvTagline = (TextView)findViewById(R.id.tvTagline);
		ImageView ivProfile = (ImageView)findViewById(R.id.ivProfileImage);
		TextView tvFollowers = (TextView)findViewById(R.id.tvFollowers);
		TextView tvFollowing = (TextView)findViewById(R.id.tvFollowing);
		tvName.setText(u.getName());
		tvTagline.setText (u.getTagline ());
		tvFollowers.setText(u.getFollowersCount() + " Followers");
		tvFollowing.setText(u.getFollowingCount() + " Following");
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(u.getProfileImageUrl(), ivProfile);		
	}
}
