package com.tutos.android.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ExitActivity extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exit);
        
        
        final Button restartButton = (Button) findViewById(R.id.start_button);
        restartButton.setOnClickListener(new OnClickListener() {
      			
        @Override
        public void onClick(View v) {
      	Intent intent = new Intent(ExitActivity.this, ModeActivity.class);
      	startActivity(intent);
      	}
      });
    } 
}