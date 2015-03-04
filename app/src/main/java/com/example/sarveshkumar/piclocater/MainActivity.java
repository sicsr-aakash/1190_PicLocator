package com.example.sarveshkumar.piclocater;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    TextView touchedXY, invertedXY, imgSize, colorRGB;
    ImageView imgSource1, imgSource2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        touchedXY = (TextView)findViewById(R.id.xy);
        invertedXY = (TextView)findViewById(R.id.invertedxy);
        imgSize = (TextView)findViewById(R.id.size);
        colorRGB = (TextView)findViewById(R.id.colorrgb);
        imgSource1 = (ImageView)findViewById(R.id.source1);
        imgSource2 = (ImageView)findViewById(R.id.source2);

        imgSource1.setOnTouchListener(imgSourceOnTouchListener);
        imgSource2.setOnTouchListener(imgSourceOnTouchListener);

    }

    View.OnTouchListener imgSourceOnTouchListener
            = new View.OnTouchListener(){

        @Override
        public boolean onTouch(View view, MotionEvent event) {

            float eventX = event.getX();
            float eventY = event.getY();
            float[] eventXY = new float[] {eventX, eventY};

            Matrix invertMatrix = new Matrix();
            ((ImageView)view).getImageMatrix().invert(invertMatrix);

            invertMatrix.mapPoints(eventXY);
            int x = Integer.valueOf((int)eventXY[0]);
            int y = Integer.valueOf((int)eventXY[1]);

            touchedXY.setText(
                    "touched position: "
                            + String.valueOf(eventX) + " / "
                            + String.valueOf(eventY));
            invertedXY.setText(
                    "touched position: "
                            + String.valueOf(x) + " / "
                            + String.valueOf(y));

            Drawable imgDrawable = ((ImageView)view).getDrawable();
            Bitmap bitmap = ((BitmapDrawable)imgDrawable).getBitmap();

            imgSize.setText(
                    "drawable size: "
                            + String.valueOf(bitmap.getWidth()) + " / "
                            + String.valueOf(bitmap.getHeight()));

            //Limit x, y range within bitmap
            if(x < 0){
                x = 0;
            }else if(x > bitmap.getWidth()-1){
                x = bitmap.getWidth()-1;
            }

            if(y < 0){
                y = 0;
            }else if(y > bitmap.getHeight()-1){
                y = bitmap.getHeight()-1;
            }

            int touchedRGB = bitmap.getPixel(x, y);

            colorRGB.setText("touched color: " + "#" + Integer.toHexString(touchedRGB));
            colorRGB.setTextColor(touchedRGB);

            return true;
        }};

}




