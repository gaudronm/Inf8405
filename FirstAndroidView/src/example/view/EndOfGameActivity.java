package example.view;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EndOfGameActivity extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_of_game);
        
        final Button scoresButton = (Button) findViewById(R.id.connect);
        scoresButton.setOnClickListener(new OnClickListener() {
      			
	        @Override
	        public void onClick(View v) {
		        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		 		SharedPreferences.Editor editor = preferences.edit();
		 		int score = preferences.getInt("score", 0);
		 		EditText eTexte = (EditText)findViewById(R.id.username);	    
		 		String name = eTexte.getText().toString();
		 		for(int i = 1; i < 6; i++) {
		 			int idScore = i * 10;
		 			int tempScore = preferences.getInt(Integer.toString(idScore), 0);
		 			if (score >= tempScore) {
		 				String tempName = preferences.getString(Integer.toString(i), "null");
		 				editor.putString(Integer.toString(i), name);
		 				editor.commit();
		 				editor.putInt(Integer.toString(idScore), score);
		 				editor.commit();
		 				score = tempScore;
		 				name = tempName;
		 			}
		 		}
		      	Intent intent = new Intent(EndOfGameActivity.this, ScoresActivity.class);
		      	startActivity(intent);
	      	}
        });
        
        final Button ignoreButton = (Button) findViewById(R.id.ignore);
		ignoreButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(EndOfGameActivity.this, MainActivity.class);
				startActivity(intent);
			}
		});
    } 
}


