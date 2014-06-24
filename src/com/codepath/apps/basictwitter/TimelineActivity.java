package com.codepath.apps.basictwitter;

import java.util.ArrayList;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.codepath.apps.basictwitter.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import eu.erikw.PullToRefreshListView;
import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class TimelineActivity extends Activity {

	private TwitterClient client;
	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	//private PullToRefreshListView lvTweets;
	private ListView lvTweets;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		client = TwitterApplication.getRestClient();
		populateTimeline ("");
		//lvTweets = (PullToRefreshListView)findViewById(R.id.lvTweets);
		lvTweets = (ListView)findViewById(R.id.lvTweets);
		tweets = new ArrayList<Tweet> ();
		aTweets = new TweetArrayAdapter(this, tweets);
		lvTweets.setAdapter(aTweets);
		
		lvTweets.setOnScrollListener(new EndlessScrollListener ()
		{

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				 customLoadMoreDataFromApi(page, totalItemsCount); 
				
			}
			
		}
		);
/*		lvTweets.setOnRefreshListener(new OnRefreshListener ()
		{

			@Override
			public void onRefresh() {
				populateTimeline ("");
			}
			
		});
		*/
	}

	
	// Append more data into the adapter
    public void customLoadMoreDataFromApi(int page, int offset) {
      // This method probably sends out a network request and appends new data items to your adapter. 
      // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
      // Deserialize API response and then construct new objects to append to the adapter
    	Log.d("debug", "page =" + page + " offset="+offset);
    	Log.d ("debug", "number of tweets in list = " + aTweets.getCount ());
    	if (aTweets.getCount () == offset)
    	{
    	   	String max_id = aTweets.getItem(offset-1).getId();
        	populateTimeline (max_id);	
    	}
    }
    
	public void populateTimeline (String max_id) {
		client.getHomeTimeline(new JsonHttpResponseHandler ()
		{
			@Override
			public void onSuccess(org.json.JSONArray json) 
			{
				//Log.d("debug", json.toString());
				aTweets.addAll(Tweet.fromJsonArray(json));
				 //lvTweets.onRefreshComplete();
			};
			
			@Override
			public void onFailure(Throwable arg0, String arg1) 
			{
				Log.d("debug", arg0.toString());
				Log.d("debug", arg1);
			};
		}
		, max_id);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		 getMenuInflater().inflate(R.menu.compose, menu);
	      return true;
	}
	
	public void onCompose (MenuItem mi)
	 {
		 Intent i = new Intent (this, ComposeActivity.class);
		 //pass data
		 //get this from API?
		 i.putExtra("screenName", "Vinod Balakrishnan");
		 i.putExtra("userName", "bvinodkr");
		 startActivityForResult(i, 50);
	 }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		 if (resultCode == RESULT_OK)
		 {
			 if (requestCode == 50)
			 {
				 String msg = data.getStringExtra("msg");
				 //settings = (Settings)data.getSerializableExtra("value");
				 //Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
				 //searchQuery(0, true);
					msg = Uri.encode(msg);
					client.postTweet(new JsonHttpResponseHandler ()
					{
						@Override
						public void onSuccess(int arg0, JSONObject arg1) {
							Tweet tweet = Tweet.fromJson(arg1);
							aTweets.insert(tweet, 0);
						}
						
						@Override
						public void onFailure(Throwable arg0, String arg1) 
						{
							Log.d("debug", arg0.toString());
							Log.d("debug", arg1);
						};
					}, msg);
			 }
		 }
	}
}

