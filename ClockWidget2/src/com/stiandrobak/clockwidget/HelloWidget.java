package com.stiandrobak.clockwidget;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.util.Log;
import android.widget.RemoteViews;

public class HelloWidget extends AppWidgetProvider{
	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds ){
		Timer timer = new Timer();
		//Log.d(null,"Current time is:"+Calendar.getInstance().getTimeInMillis());
		timer.scheduleAtFixedRate(new MyTime(context,appWidgetManager,appWidgetIds), 7, 3000);
	}
	
	private class MyTime extends TimerTask {
		int[] views ={R.id.image12,R.id.image11,R.id.image10,R.id.image09,R.id.image08,R.id.image07,R.id.image06,R.id.image05,R.id.image04,R.id.image03,R.id.image02,R.id.image01};
		RemoteViews remoteViews;
		AppWidgetManager appWidgetManager;
		int[] appWidgetIds;
		public MyTime(Context context, AppWidgetManager appWidgetManager,int[] appWidgetIds) {
			this.appWidgetManager = appWidgetManager;
			this.appWidgetIds = appWidgetIds;
			remoteViews = new RemoteViews(context.getPackageName(), R.layout.main);
		}
		
		@Override
		public void run() {
			//long num = Calendar.getInstance().getTime().getTime()/1000L;
			Calendar cal = Calendar.getInstance();
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE,0);
			cal.set(Calendar.SECOND,0);
			cal.set(Calendar.MILLISECOND,0);
			long num = (Calendar.getInstance().getTimeInMillis()/1000L);
			num -= (cal.getTimeInMillis()/1000L);
			pickImage((views.length - 1), (int)num);
			appWidgetManager.updateAppWidget(appWidgetIds,remoteViews);
		}
		
		public void pickImage(int pow, int time){
			if(time == 1 && pow == 0){
				remoteViews.setImageViewResource(views[pow], R.drawable.dot_active);
				return;
			}else if(time == 0 && pow == 0){
				remoteViews.setImageViewResource(views[pow], R.drawable.dot_inactive);
				return;
			}
			if(time >= (int)java.lang.Math.pow(2,pow) * 60){
				remoteViews.setImageViewResource(views[pow], R.drawable.dot_active);
				time = time - ((int)java.lang.Math.pow(2, pow) * 60);
				//Log.d(null,"Time: "+time + " and " + pow);
			}else{
				remoteViews.setImageViewResource(views[pow], R.drawable.dot_inactive);
			}
			if(pow == 0){
				return;
			}
			pickImage((pow-1), time);
		}
	} 
}