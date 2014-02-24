package com.tutos.android.ui;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import control.Engine;
import stockage.Table;
import stockage.Coordinate;

public class GameActivity extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game);
        Engine engine = new Engine();
        engine.initPart();
        
        Table table = engine.getTable(); 
        
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
	            	case 1 :  iv.setImageDrawable(resize(getResources().getDrawable(R.drawable.bej1),100,100));
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
	        
	            tr.addView(iv);
	           
	        }
	        
	        grid.addView(tr);

        }
        
        
    }
    
    private Drawable resize(Drawable image, int x, int y) {
        Bitmap b = ((BitmapDrawable)image).getBitmap();
        Bitmap bitmapResized = Bitmap.createScaledBitmap(b, x, y, false);
        return new BitmapDrawable(getResources(), bitmapResized);
    }
}