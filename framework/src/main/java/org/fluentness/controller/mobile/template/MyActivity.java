package org.fluentness.controller.mobile.template;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

public class MyActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView text = new TextView(this);
        text.setText("Hallo Welt");
        text.setTextColor(Color.BLUE);
        setContentView(text);
    }
}
