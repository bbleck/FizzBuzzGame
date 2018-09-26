package edu.cnm.deepdive.fizzbuzzgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.ToggleButton;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
  private TextView valueDisplay;
  private int value;
  private Random rng;
  private final int BOUND = 100;
  private Timer timer;
  private TextView correctTally;
  private TextView incorrectTally;
  private ToggleButton fizzButton;
  private ToggleButton buzzButton;
  private int correct;
  private int incorrect;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    initViews();
    initData();
  }

  @Override
  protected void onStart() {
    super.onStart();
    timer = new Timer();
    timer.scheduleAtFixedRate(new UpdateTask(), 1000, 3000);
  }

  @Override
  protected void onStop() {
    timer.cancel();
    timer = null;
    super.onStop();
  }

  private void initData() {
    rng = new Random();
  }

  private void initViews(){
    valueDisplay = findViewById(R.id.value_display);
    fizzButton = findViewById(R.id.fizz_toggle);
    buzzButton = findViewById(R.id.buzz_toggle);
    correctTally = findViewById(R.id.correct_tally);
    incorrectTally = findViewById(R.id.incorrect_tally);
  }

  private void refresh(){
    if(value!=0){
      if(((value%3==0)==fizzButton.isChecked())
          && ((value%5==0)==buzzButton.isChecked())){
        correct++;
      }else{
        incorrect++;
      }
      correctTally.setText(getString(R.string.correct_tally_format, correct));
      incorrectTally.setText(getString(R.string.incorrect_tally_format, incorrect));
    }
    value = rng.nextInt(BOUND)+1;
    setValueDisplay(Integer.toString(value));
    fizzButton.setChecked(false);
    buzzButton.setChecked(false);
  }

  private class UpdateTask extends TimerTask {

    @Override
    public void run() {
      runOnUiThread(new Updater());
    }
  }

  private class Updater implements Runnable{

    @Override
    public void run() {
      refresh();
    }
  }

  private void setValueDisplay(String value){
    valueDisplay.setText(value);
  }


}
