package com.codepath.apps.basictwitter.fragments;

import java.util.ArrayList;

import com.codepath.apps.basictwitter.EndlessScrollListener;
import com.codepath.apps.basictwitter.R;
import com.codepath.apps.basictwitter.TweetArrayAdapter;
import com.codepath.apps.basictwitter.models.Tweet;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public abstract class TweetsListFragment extends Fragment {
	private ArrayList<Tweet> tweets;
	private ArrayAdapter<Tweet> aTweets;
	//private PullToRefreshListView lvTweets;
	private ListView lvTweets;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
		
		lvTweets = (ListView)v.findViewById(R.id.lvTweets);
		lvTweets.setAdapter(aTweets);
		lvTweets.setOnScrollListener(new EndlessScrollListener ()
		{

			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				 customLoadMoreDataFromApi(page, totalItemsCount); 
				
			}
			
		}
		);
		return v;
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
    
    public abstract void populateTimeline (String max_id);
    
	@Override
	public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			tweets = new ArrayList<Tweet> ();
			aTweets = new TweetArrayAdapter(getActivity(), tweets);
	}
	
	public void addAll (ArrayList<Tweet> tweets)
	{
		aTweets.addAll(tweets);
	}
}
