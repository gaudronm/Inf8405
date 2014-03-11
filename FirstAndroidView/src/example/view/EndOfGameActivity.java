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

public class EndOfGameActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_end_of_game);


		// on récupere le score obtenu dans la partie
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		final int score = preferences.getInt("score", 0);

		final TextView textViewScore = (TextView) findViewById(R.id.textViewScore);
		textViewScore.setText(""+score);

		final Button scoresButton = (Button) findViewById(R.id.connect);
		scoresButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				SharedPreferences.Editor editor = preferences.edit();
				editor.putInt("score", score);
				editor.commit();
				Intent intent = new Intent(EndOfGameActivity.this, RecordScoreActivity.class);
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


