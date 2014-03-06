package com.tutos.android.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ModeActivity extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);
        
        final Button modesButton = (Button) findViewById(R.id.limited_time_button);
        modesButton.setOnClickListener(new OnClickListener() {
      			
        @Override
        public void onClick(View v) {
      	Intent intent = new Intent(ModeActivity.this, GameActivityTime.class);
      	startActivity(intent);
      	}
      });
        
        final Button exitButton = (Button) findViewById(R.id.limited_moves_button);
        exitButton.setOnClickListener(new OnClickListener() {
      			
        @Override
        public void onClick(View v) {
      	Intent intent = new Intent(ModeActivity.this, GameActivityMove.class);
      	startActivity(intent);
      	}
      });
    } 
}