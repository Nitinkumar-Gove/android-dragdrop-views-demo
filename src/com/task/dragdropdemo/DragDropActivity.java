package com.task.dragdropdemo;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.widget.TextView;
import android.widget.Toast;


public class DragDropActivity extends ActionBarActivity {

	private TextView txtRedShape, txtGreenShape, txtDropZone;
	
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drag_drop);
		
		if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.HONEYCOMB) {
		     // only for honeycomb and newer versions
			Toast.makeText(getApplicationContext(), "Your phone seems to be running lower versions of android."+ "\n" +"Please update to v3.0 or above !",Toast.LENGTH_SHORT).show();
		}
		
		// initialize views to drag
		txtRedShape=(TextView)findViewById(R.id.viewRed);
		txtRedShape.setTag("Red");
		
		txtGreenShape=(TextView)findViewById(R.id.viewGreen);
		txtGreenShape.setTag("Green");
		
		// initialize dropzone view
		txtDropZone=(TextView)findViewById(R.id.viewDropZone);
		
		// initialize listeners
		txtRedShape.setOnTouchListener(new TouchListener());
		txtGreenShape.setOnTouchListener(new TouchListener());
		
		
		// handle the drop event on drop zone
		txtDropZone.setOnDragListener(new OnDragListener() {
			
			@Override
			public boolean onDrag(View v, DragEvent event) {
				// TODO Auto-generated method stub
				
				int action = event.getAction();
		        switch(action) 
		        {
				    case DragEvent.ACTION_DRAG_STARTED: {
				           
				        return true;
				    }
				    case DragEvent.ACTION_DRAG_LOCATION: {
				        return true;
				    }
				    case DragEvent.ACTION_DRAG_ENTERED: {
				        
				        return true;
				    }
				    case DragEvent.ACTION_DROP: {
				    	
				    	View source = (View) event.getLocalState();
				    	TextView tv=(TextView)source;
				    	String tag=(String) tv.getTag();
				        Toast.makeText(getApplicationContext(), tag, Toast.LENGTH_SHORT).show();
				        
				        //v.invalidate();
				        return true;
				    }
				    default: {
				        return false;
				    }
		        }
				
				
			}
		});
		
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.drag_drop, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public void onBackPressed() {
	    new AlertDialog.Builder(this)
	        .setIcon(android.R.drawable.ic_dialog_alert)
	        .setTitle("Exit App")
	        .setMessage("Do you want to close the application ?")
	        .setPositiveButton("Yes", new DialogInterface.OnClickListener()
	    {
	        @Override
	        public void onClick(DialogInterface dialog, int which) {
	            finish();    
	        }

	    })
	    .setNegativeButton("No", null)
	    .show();
	}
	
	// class to detect start of the drag operation
	
	@SuppressLint("ClickableViewAccessibility")
	private final class TouchListener implements OnTouchListener
	{


		@TargetApi(Build.VERSION_CODES.HONEYCOMB)
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				
			    //setup drag
				ClipData data = ClipData.newPlainText("", "");
				DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
				
				//start dragging the item touched
				v.startDrag(data, shadowBuilder, v, 0);
				
			    return true;
			} 
			else {
			    return false;
			}
			
		}
		
	}
	
}
