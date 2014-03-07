package com.tutos.android.ui;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.support.v4.app.NavUtils;

public class MainActivity extends Activity implements OnClickListener{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final Button scoresButton = (Button) findViewById(R.id.button1);
		scoresButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, ScoresActivity.class);
				startActivity(intent);
			}
		});

		final Button modesButton = (Button) findViewById(R.id.button2);
		modesButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, ModeActivity.class);
				startActivity(intent);
			}
		});

		final Button exitButton = (Button) findViewById(R.id.button3);
		exitButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, ExitActivity.class);
				startActivity(intent);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}


}
