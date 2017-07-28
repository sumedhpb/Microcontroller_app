package com.example.sumedh.microcontroller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

        import android.os.Handler;
        import android.text.method.ScrollingMovementMethod;
        import android.view.View;
        import android.widget.TextView;

public class Main3Activity extends AppCompatActivity {
    public String address;
    TextView response;
    static Main3Activity main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        response = (TextView) findViewById(R.id.textView3);
        //response.setText("Your text will be displayed here");
        //if (MainActivity.dev != null) {
        main = this;
        response.setMovementMethod(new ScrollingMovementMethod());
        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                //response.setText("Data being processed");
                server s;
                s = new server(main, response);
                handler.postDelayed(this, 1000);
            }
        };
        runnable.run();
        //message.setText(getIpAddress());
        //t.setText(server.str);
    }
        /*else{
            response.setText("Cannot receive data");
        }*/

    public void display(View v) {

        /*EditText e2 = (EditText) findViewById(R.id.editText2);
        address=e2.getText().toString();*/
    }

}
//}
