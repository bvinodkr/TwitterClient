package com.codepath.apps.basictwitter.models;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.net.Uri;

public class Tweet {

	private String body;
	private long uid;
	private String createdAt;
	private String id;
	private User user;
	
	public static Tweet fromJson (JSONObject jsonObject)
	{
		Tweet tweet = new Tweet ();
		//Extract values from json
		
		try{
			tweet.body = Uri.decode(jsonObject.getString("text"));
			tweet.uid = jsonObject.getLong("id");
			tweet.createdAt = jsonObject.getString("created_at");
			tweet.id = jsonObject.getString("id");
			tweet.user = User.fromJson (jsonObject.getJSONObject("user"));
		}
		catch (JSONException e)
		{
			e.printStackTrace();
			return null;
		}
		return tweet;
	}

	public String getId() {
		return id;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public static ArrayList<Tweet> fromJsonArray(JSONArray json) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet> (json.length());
		
		for (int i = 0; i < json.length(); i++)
		{
			JSONObject tweetJson = null;
			try
			{
				tweetJson = json.getJSONObject(i);
				
			}
			catch (JSONException e)
			{
				continue;
			}
			Tweet tweet = Tweet.fromJson(tweetJson);
			tweets.add(tweet);
		}
		return tweets;
	}
	
	
	@Override
	public String toString() {
		return getBody() + "-" + getUser().getScreenName();
	}
}
