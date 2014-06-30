package com.codepath.apps.basictwitter;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.codepath.apps.basictwitter.fragments.HomeTimelineFragment;
import com.codepath.apps.basictwitter.fragments.MentionsTimelineFragment;
import com.codepath.apps.basictwitter.listeners.FragmentTabListener;

public class TimelineActivity extends FragmentActivity {



	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);

		//lvTweets = (PullToRefreshListView)findViewById(R.id.lvTweets);
		setupTabs();
		
	
		

/*		lvTweets.setOnRefreshListener(new OnRefreshListener ()
		{

			@Override
			public void onRefresh() {
				populateTimeline ("");
			}
			
		});
		*/
		
		getActionBar().setTitle("Twitter Timeline");
	}

	
	private void setupTabs() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		actionBar.setDisplayShowTitleEnabled(true);

		Tab tab1 = actionBar
		    .newTab()
		    .setText("Home")
		    .setIcon(R.drawable.ic_home)
		    .setTag("HomeTimelineFragment")
		    .setTabListener(new FragmentTabListener<HomeTimelineFragment>(R.id.flContainer, this,
                        "home", HomeTimelineFragment.class));

		actionBar.addTab(tab1);
		actionBar.selectTab(tab1);

		Tab tab2 = actionBar
		    .newTab()
		    .setText("Mentions")
		    .setIcon(R.drawable.ic_mention)
		    .setTag("MentionsTimelineFragment")
		    .setTabListener(new FragmentTabListener<MentionsTimelineFragment>(R.id.flContainer, this,
                        "mentions", MentionsTimelineFragment.class));
		actionBar.addTab(tab2);
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
	
	public void onProfile (MenuItem mi)
	{
		Intent i = new Intent (this, ProfileActivity.class);
		
		startActivity (i);
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
					/*
					client.postTweet(new JsonHttpResponseHandler ()
					{
						@Override
						public void onSuccess(int arg0, JSONObject arg1) {
							Tweet tweet = Tweet.fromJson(arg1);
							//aTweets.insert(tweet, 0);
						}
						
						@Override
						public void onFailure(Throwable arg0, String arg1) 
						{
							Log.d("debug", arg0.toString());
							Log.d("debug", arg1);
						};
					}, msg);
					*/
			 }
		 }
	}
}

