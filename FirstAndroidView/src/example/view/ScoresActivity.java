package example.view;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ScoresActivity extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        for(int i = 1; i < 6; i++) {
        	int id = 0;
        	switch (i) {
        		case 1 : id = R.id.textView2;
        		break;
        		case 2 : id = R.id.textView3;
        		break;
        		case 3 : id = R.id.textView4;
        		break;
        		case 4 : id = R.id.textView5;
        		break;
        		case 5 : id = R.id.textView6;
        		break;
        	}
        	int idScore = i * 10;
        	int score = preferences.getInt(Integer.toString(idScore), 0);
        	String chaine = Integer.toString(i) + ". " + preferences.getString(Integer.toString(i), "null")
        			+ " " + Integer.toString(score);
        	TextView vue = (TextView)findViewById(id);
        	vue.setText(chaine);
        }
        
        final Button returnButton = (Button) findViewById(R.id.buttonReturn);
		returnButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ScoresActivity.this, MainActivity.class);
				startActivity(intent);
			}
		}); 
		
		
		final Button resetButton = (Button) findViewById(R.id.reset);
		resetButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				SharedPreferences.Editor editor = preferences.edit();
				editor.clear();
				editor.commit();
				Intent intent = new Intent(ScoresActivity.this, ScoresActivity.class);
				startActivity(intent);
			}
		});
        
    } 
}