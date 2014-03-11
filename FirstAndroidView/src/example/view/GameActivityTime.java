package example.view;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.DragEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import example.control.Engine;
import example.stockage.Coordinate;
import example.stockage.Table;

public class GameActivityTime extends Activity {
	
	private Chronometer chronometer;
	Boolean first;
    ImageView firstIm;
    Engine engine;
    Table table;
    int score;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_time);
        firstIm = new ImageView(this);
        first = true;
        score = 0;
        engine = new Engine();
        engine.initPart();
        
        table = engine.getTable(); 
        
        final TableLayout grid = (TableLayout)this.findViewById(R.id.TableLayoutTime);
        grid.setShrinkAllColumns(true);

        startTimer(); // demarre le chronometre
        
        	//Instanciation de la grille
        	for (int i = 0; i < 8; i++) {

                TableRow tr = new TableRow(this);
                tr.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

    	        for (int j = 0; j < 8; j++) {
    	        	ImageView iv = new ImageView(this);
    	            
    	            int color = table.getColor(i,j);
    	            
    	            switch (color){
    	            	case 1 : iv.setImageDrawable(resize(getResources().getDrawable(R.drawable.bej1),100,100));
    	            	break;
    	            	
    	            	case 2 : iv.setImageDrawable(resize(getResources().getDrawable(R.drawable.bej2),100,100));
    	            	break;
    	            	
    	            	case 3 : iv.setImageDrawable(resize(getResources().getDrawable(R.drawable.bej3),100,100));
    	            	break;
    	            	
    	            	case 4 : iv.setImageDrawable(resize(getResources().getDrawable(R.drawable.bej4),100,100));
    	            	break;
    	            	
    	            	case 5 : iv.setImageDrawable(resize(getResources().getDrawable(R.drawable.bej5),100,100));
    	            	break;

    	            }
    	            //on donne un identifiant à chaque element de la grille pour pouvoir
    	            //les modifier facilement par la suite
    	            iv.setId(i+10*j);
    	            iv.setOnClickListener(new View.OnClickListener() {
    	               @Override
    	               public void onClick(View v) {
    	            	   
    	            	   if(getTime()<60){
    	                   	
    	            		   if (first) {
        	            		   first = false;
        	            		   firstIm = (ImageView) v;
        	            	   }
        	            	   else {
        	            		   first = true;
        	            		   int id1 = firstIm.getId();
        	            		   int x = id1 % 10;
        	            		   int y = id1 / 10;
        	            		   int id2 = ((ImageView) v).getId();
        	            		   int k = id2 % 10;
        	            		   int l = id2 / 10;
        	            		   if (engine.validMove(x, y, k, l)) {
        	            			   score +=  engine.playMove(x, y, k, l);
    		            			   final TextView textViewScore = (TextView) findViewById(R.id.textView_score_time);
    		            			   textViewScore.setText(""+score);
    		            			   showTable();
        	            		   }
        	            	   }
    	                   }
    	            	   
    	            	   else{
    	            		   //cette partie sert à enregistrer le score dans une preference afin de pouvoir
    	            		   //le récupérer à partir d'une autre activité
    	            		   SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
    	            		   SharedPreferences.Editor editor = preferences.edit();
    	            		   editor.putInt("score", score);
    	            		   editor.commit();
    	            		   
    	            		   Intent intent = new Intent(GameActivityTime.this, EndOfGameActivity.class);
    	            		   startActivity(intent);
    	            	   }
    	            	   
    	               }
    	            });
    	        
    	            tr.addView(iv);
    	           
    	        }
    	        
    	        grid.addView(tr);

            }
        
        	final Button exitButton = (Button) findViewById(R.id.buttonQuitTime);
    		exitButton.setOnClickListener(new OnClickListener() {

    			@Override
    			public void onClick(View v) {
    				Intent intent = new Intent(GameActivityTime.this, ExitActivity.class);
    				startActivity(intent);
    			}
    		});     	
        
        
    }
    
    private void startTimer(){
    	
        chronometer = (Chronometer) findViewById(R.id.chronometer);
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
        
       
    }
    
    //retourne le temps affich� par le chronometre
    private int getTime(){
    	long timeElapsed = SystemClock.elapsedRealtime() - chronometer.getBase();
        int seconds = (int) timeElapsed/ 1000;
        return seconds;
    }
    
    //permet d'afficher la grille
    private void showTable() {
    	table = engine.getTable();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				ImageView iv = (ImageView) findViewById(i+10*j);
				int color = table.getColor(i,j);
				
				switch (color){
					case 1 : iv.setImageDrawable(resize(getResources().getDrawable(R.drawable.bej1),100,100));
					break;
          	
          			case 2 : iv.setImageDrawable(resize(getResources().getDrawable(R.drawable.bej2),100,100));
          			break;
          	
          			case 3 : iv.setImageDrawable(resize(getResources().getDrawable(R.drawable.bej3),100,100));
          			break;
           	
           			case 4 : iv.setImageDrawable(resize(getResources().getDrawable(R.drawable.bej4),100,100));
           			break;
           	
           			case 5 : iv.setImageDrawable(resize(getResources().getDrawable(R.drawable.bej5),100,100));
           			break;
					}
				}
			}
    }
    
    //permet de redimensionner les gemmes
    private Drawable resize(Drawable image, int x, int y) {
        Bitmap b = ((BitmapDrawable)image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, x, y, false);
        return new BitmapDrawable(getResources(), bitmapResized);
    }
}