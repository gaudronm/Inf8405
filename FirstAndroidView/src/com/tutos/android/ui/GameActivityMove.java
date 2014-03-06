package com.tutos.android.ui;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import control.Engine;
import stockage.Table;
import stockage.Coordinate;

public class GameActivityMove extends Activity {
	
	Boolean first;
    ImageView firstIm;
    Engine engine;
    Table table;
    int score;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        firstIm = new ImageView(this);
        first = true;
        score = 0;
        engine = new Engine();
        engine.initPart();
        
        table = engine.getTable(); 
        
        final TableLayout grid = (TableLayout)this.findViewById(R.id.TableLayout1);
        grid.setShrinkAllColumns(true);

        
        for (int i = 0; i < 8; i++) {

            TableRow tr = new TableRow(this);
            tr.setLayoutParams(new TableRow.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));

	        for (int j = 0; j < 8; j++) {
	        	ImageView iv = new ImageView(this);
	            
	            int color = table.getColor(i,j);
	            System.out.println(color);
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
	            iv.setId(i+10*j);
	            iv.setOnClickListener(new View.OnClickListener() {
	               @Override
	               public void onClick(View v) {
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
	            			   engine.playMove(x, y, k, l);
	            			   ShowTable();
	            		   }
	            	   }
	               }
	            });
	        
	            tr.addView(iv);
	           
	        }
	        
	        grid.addView(tr);

        }
        
        
    }
    
    private void ShowTable() {
    	table = engine.getTable();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				ImageView iv = (ImageView) findViewById(i+10*j);
				int color = table.getColor(i,j);
				System.out.println(color);
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
    
    private Drawable resize(Drawable image, int x, int y) {
        Bitmap b = ((BitmapDrawable)image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, x, y, false);
        return new BitmapDrawable(getResources(), bitmapResized);
    }
}