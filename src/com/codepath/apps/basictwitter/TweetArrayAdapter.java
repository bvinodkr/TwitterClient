package com.codepath.apps.basictwitter;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.net.ParseException;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.basictwitter.models.Tweet;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetArrayAdapter extends ArrayAdapter<Tweet> {

	public TweetArrayAdapter(Context context, List<Tweet> objects) {
		super(context, 0, objects);
		
	}
	// getRelativeTimeAgo("Mon Apr 01 21:16:23 +0000 2014");
	public String getRelativeTimeAgo(String rawJsonDate) {
		String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
		SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
		sf.setLenient(true);
	 
		String relativeDate = "";
		try {
			long dateMillis = sf.parse(rawJsonDate).getTime();
			relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
					System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (java.text.ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
		return relativeDate;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Tweet tweet = getItem (position);
		View v;
		if (convertView == null)
		{
			LayoutInflater inflator = LayoutInflater.from(getContext());
			v = inflator.inflate(R.layout.twitter_item, parent, false);
		}
		else
		{
			v = convertView;
		}
		
		ImageView ivProfileImage = (ImageView)v.findViewById(R.id.ivProfileImage);
		ivProfileImage.setTag(tweet.getUser().getScreenName());
		ivProfileImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View imageView) {
				
				Intent i = new Intent (getContext(), ProfileActivity.class); 
				i.putExtra("screenName", (String)imageView.getTag());
				imageView.getContext().startActivity (i);
			}
		});
		TextView tvUserName = (TextView)v.findViewById(R.id.tvUserName);
		TextView tvBody = (TextView)v.findViewById(R.id.tvBody);
		TextView tvRelativeTime = (TextView)v.findViewById(R.id.tvRelativeTime);
		ivProfileImage.setImageResource(android.R.color.transparent);
		ImageLoader imageLoader = ImageLoader.getInstance();
		imageLoader.displayImage(tweet.getUser().getProfileImageUrl(), ivProfileImage);
		tvUserName.setText(tweet.getUser().getScreenName());
		tvBody.setText (tweet.getBody());
		tvRelativeTime.setText(getRelativeTimeAgo(tweet.getCreatedAt()));
		return v;
	}

}
