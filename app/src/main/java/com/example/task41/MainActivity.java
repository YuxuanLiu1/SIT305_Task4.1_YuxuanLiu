package com.example.task41;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Chronometer chronometer;
    TextView textView;
    ImageButton imageButton, imageButton2, imageButton3;
    long stopset;
    EditText workout;
    boolean run, change, click, press, going = false;
    SharedPreferences sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chronometer = findViewById(R.id.chronometer);
        textView = findViewById(R.id.textView);
        workout = findViewById(R.id.workout);
        saving();

        if (savedInstanceState != null) {
            String result = savedInstanceState.getString("result");
            boolean clicked = savedInstanceState.getBoolean("click");
            boolean changed = savedInstanceState.getBoolean("change");
            boolean pressed = savedInstanceState.getBoolean("press");
            long stop_set = savedInstanceState.getLong("stop_set");
            textView.setText(result);

            if (clicked) {
                Long a3 = savedInstanceState.getLong("time");
                chronometer.setBase(a3);
                chronometer.start();
                run = true;
                going = true;
                click = true;
                change = false;
            } else if (changed) {
                chronometer.stop();
                run = false;
                change = false;
                click = false;
                press = true;

            } else if (pressed) {
                chronometer.stop();
                chronometer.setBase(SystemClock.elapsedRealtime() - stop_set);
                going = false;
                run = false;
                change = false;
                click = false;
            }
        }
    }

    private void saving() {
        SharedPreferences sp = getSharedPreferences("save", Context.MODE_PRIVATE);
        String a3 = sp.getString("a1", "");
        String a4 = sp.getString("a2", "");
        textView.setText("Last time, you spent " + a3 + " on " + a4 );
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("result", textView.getText().toString());
        outState.putLong("time", chronometer.getBase());
        outState.putBoolean("click", click);
        outState.putBoolean("press", press);
        outState.putBoolean("change", change);
        outState.putLong("stop_set", stopset);

    }

    public void start(View v) {
        if (workout.getText().toString().isEmpty()) {
            Toast.makeText(this, "Enter type of workout first", Toast.LENGTH_SHORT).show();
        } else {
            if (!run) {
                chronometer.setBase(SystemClock.elapsedRealtime() - stopset);
            }

            chronometer.setBase(SystemClock.elapsedRealtime() - stopset);
            chronometer.start();
            run = true;
            going = true;
            click = true;
            change = false;

        }
    }

    public void stop(View v) {
        if (run) {
            chronometer.stop();
            stopset = SystemClock.elapsedRealtime() - chronometer.getBase();
            run = false;
            change = false;
            click = false;
            press = true;
        }
    }

    public void reset(View v) {
        String a1 = chronometer.getText().toString();
        chronometer.setBase(SystemClock.elapsedRealtime());
        String a2 = workout.getText().toString();
        textView.setText("You spent " + a1 + " on " + a2 + " last time.");
        going = false;
        stopset = 0;
        chronometer.stop();
        run = false;
        change = false;
        click = false;
        sv = getSharedPreferences("save", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sv.edit();
        editor.putString("a1", a1);
        editor.putString("a2", a2);
        editor.apply();
    }
}