package com.tutos.android.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class GameActivity extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        
        final Button modesButton = (Button) findViewById(R.id.exit_button);
        modesButton.setOnClickListener(new OnClickListener() {
      			
        @Override
        public void onClick(View v) {
      	Intent intent = new Intent(GameActivity.this, ExitActivity.class);
      	startActivity(intent);
      	}
      });
        
        
    } 
}