package com.tutos.android.ui;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

public class ScoresActivity extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        for(int i = 1; i < 6; i++) {
        	int id = 0;
        	switch (i) {
        		case 1 : id = R.id.textView1;
        		break;
        		case 2 : id = R.id.textView2;
        		break;
        		case 3 : id = R.id.textView3;
        		break;
        		case 4 : id = R.id.textView4;
        		break;
        		case 5 : id = R.id.textView5;
        		break;
        	}
        	int idScore = i * 10;
        	int score = preferences.getInt(Integer.toString(idScore), 0);
        	if (score != 0) {
        		String chaine = Integer.toString(i) + ". " + preferences.getString(Integer.toString(i), "null")
        				+ " " + Integer.toString(score);
        	    TextView vue = (TextView)findViewById(id);
        	    vue.setText(chaine);
        	}
        }
        
    } 
}