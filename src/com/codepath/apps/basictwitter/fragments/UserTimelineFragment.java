package com.codepath.apps.basictwitter.fragments;

import com.codepath.apps.basictwitter.TwitterApplication;
import com.codepath.apps.basictwitter.TwitterClient;
import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

public class UserTimelineFragment extends TweetsListFragment {
	private TwitterClient client;
	private String screenName;
	
	public UserTimelineFragment ()
	{
		screenName = null;
	}
	
	public void setScreenName (String screenName)
	{
		this.screenName = screenName;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		client = TwitterApplication.getRestClient();
		populateTimeline("");
	}
	
	public void populateTimeline (String max_id) {
		client.getUserTimeline(new JsonHttpResponseHandler ()
		{
			@Override
			public void onSuccess(org.json.JSONArray json) 
			{
				//Log.d("debug", json.toString());
				addAll(Tweet.fromJsonArray(json));
				 //lvTweets.onRefreshComplete();
			};
			
			@Override
			public void onFailure(Throwable arg0, String arg1) 
			{
				Log.d("debug", arg0.toString());
				Log.d("debug", arg1);
			};
		}
		,  screenName);
	}
}
