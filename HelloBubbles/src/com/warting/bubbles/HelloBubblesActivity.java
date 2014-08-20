package com.warting.bubbles;

import java.util.Random;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import de.svenjacobs.loremipsum.LoremIpsum;

public class HelloBubblesActivity extends Activity {
	private com.warting.bubbles.DiscussArrayAdapter adapter;
	private ListView lv;
	private LoremIpsum ipsum;
	private EditText editText1;
	private static Random random;
	private Button sendBtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_discuss);
		random = new Random();
		ipsum = new LoremIpsum();

		lv = (ListView) findViewById(R.id.listView1);
		sendBtn = (Button) findViewById(R.id.new_msg_btn);
		sendBtn.setOnClickListener(new Button.OnClickListener(){

			@Override
			public void onClick(View v) {
				submitMsg(editText1);
			}
			
		});

		adapter = new DiscussArrayAdapter(getApplicationContext(), R.layout.listitem_discuss);

		lv.setAdapter(adapter);

		editText1 = (EditText) findViewById(R.id.editText1);
		editText1.setOnKeyListener(new OnKeyListener() {
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// If the event is a key-down event on the "enter" button
				if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
					submitMsg(editText1);
					return true;
				}
				return false;
			}
		});

		addItems();
	}
	/// Perform action on key press
	void submitMsg(EditText newMsg) {
		NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    	Notification notification = new Notification(R.drawable.ic_launcher, "A new notification", System.currentTimeMillis());
    	notification.flags |= Notification.FLAG_AUTO_CANCEL;
    	String msg = newMsg.getText().toString();
    	
    	Intent intent = new Intent(this,HelloBubblesActivity.class);
    	PendingIntent activity = PendingIntent.getActivity(this, 0, intent, 0);
    	
    	notification.setLatestEventInfo(this, "New Chat Message", msg, activity);
    	notification.number ++;
		adapter.add(new OneComment(false, msg));
		newMsg.setText("");
		
    	notificationManager.notify(0, notification);
		
	}

	private void addItems() {
		adapter.add(new OneComment(true, "Hello bubbles!"));

		for (int i = 0; i < 4; i++) {
			boolean left = getRandomInteger(0, 1) == 0 ? true : false;
			int word = getRandomInteger(1, 10);
			int start = getRandomInteger(1, 40);
			String words = ipsum.getWords(word, start);

			adapter.add(new OneComment(left, words));
		}
	}

	private static int getRandomInteger(int aStart, int aEnd) {
		if (aStart > aEnd) {
			throw new IllegalArgumentException("Start cannot exceed End.");
		}
		long range = (long) aEnd - (long) aStart + 1;
		long fraction = (long) (range * random.nextDouble());
		int randomNumber = (int) (fraction + aStart);
		return randomNumber;
	}

}