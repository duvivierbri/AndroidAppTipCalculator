/*This calculator is meant to help the user calculate how much they should tip depending on the bill and the percentage of their choice
They add their amount in the text box, and then they use the seekBar to slide it across the screen and indicate what percentage of the
bill they'd like to tip

Other features of this app include a "Reset" button and 5%, 10%, 15% and 20% button shortcuts since these are common percentages people use to tip
*/
package com.example.tipcalculator_miniproject1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast; // creates messages for the user

public class MainActivity extends AppCompatActivity {
    private int percentage = 0; //this number will be the percentage shown on the progress bar
    private TextView percentDisplay; //creating variable to represent the percent display TextView
    private EditText userInputAmount;
    private TextView userTip;
    private TextView userTotal;
    private SeekBar seekBar; //This represents the draggable seekBar
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        percentDisplay = (TextView)findViewById(R.id.percentText); //Assigning percent display to it's TextView box (next to the slider)
        seekBar = (SeekBar)findViewById(R.id.userSeekBar);
        userInputAmount = (EditText)findViewById(R.id.userInput); //Assigning userInputAmount to the box the user types in at the top of the app
        userTip = (TextView)findViewById(R.id.tipAmount); //This is the box that displays the tip
        userTotal = (TextView)findViewById(R.id.totalAmount);
        percentDisplay.setText("0%"); //ensuring the percent display starts by displaying "0%" on startup

        //SEEK BAR LISTENER CODE
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() { //LISTENERS are for obtaining information on a widget
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) { //What happens WHILE the user changes the progress bar
                percentage = progress;
                percentDisplay.setText(Integer.toString(percentage) + "%"); //Updates the percent text next to the seekBar
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { //What happens after the user stops moving the seekBar
                String input = userInputAmount.getText().toString(); //takes user input....
                int barNum = seekBar.getProgress();
                Log.d("TAG", "Inputted: " + input);
                Toast warningMessage = Toast.makeText(getApplicationContext(), "Please add a number!", Toast.LENGTH_SHORT); //warning message to the user if they don't input a number

                if (input.isEmpty()){
                    //Checks to make sure the user put an actual number first - PREVENTS CRASHES
                    warningMessage.show();
                }else if (userInputAmount.getText().toString().equals(".")){
                    warningMessage.show();
                }else{
                    //If there's a number, THEN the app does the calculations
                    double inputAsDouble = Double.parseDouble(input); //turns user input into a double
                    double calculatedTip = calculateTip(inputAsDouble, barNum);
                    userTip.setText("$" + String.format("%.2f", calculatedTip)); //Display the calculated tip (formatted to two decimal spaces)
                    double finalTotal = calculateTotal(calculatedTip, inputAsDouble);
                    userTotal.setText("$" + String.format("%.2f", finalTotal)); //Display the total amount (formatted to two decimal spaces)
                }
            }
        });
    }

    public double calculateTip(double currAmount, double barPercent){ //This function calculates the percentage tip and returns it as a double
        double bPercent = barPercent/100;
        double tip;
        tip = currAmount * bPercent;
        Log.d("N","Amount: " + currAmount + "|   Percent: " + bPercent + "%");
        return tip;
    }

    public double calculateTotal(double tip, double amount){ //This function adds the initial amount and the tip to show what total amount the person has to pay
        double returnedAmount = tip + amount;

        return returnedAmount;
    }

    //RESET BUTTON CODE...
    public void resetValues(View view){
        //The reset button resets all values back to 0
        userInputAmount.setText("");
        userTip.setText("$0.00");
        userTotal.setText("$0.00");
        seekBar.setProgress(0);
    }

    //SHORTCUT BUTTON CODES

    //All shortcut buttons use the shortCutFunction
    public void shortCutFunction(int progress){
        String inputForShortcut = userInputAmount.getText().toString(); //takes user input
        Toast warningMessage = Toast.makeText(getApplicationContext(), "Please add a number!", Toast.LENGTH_SHORT); //warning message to the user

        //Only works if the user types in a number
        if (inputForShortcut.isEmpty()){
            warningMessage.show();
        } else if (inputForShortcut.equals(".")){
            warningMessage.show();
        } else {
            seekBar.setProgress(progress);
            percentDisplay.setText(progress + "%");
            double tip = calculateTip(Double.parseDouble(inputForShortcut), progress);
            userTip.setText("$" + String.format("%.2f", tip)); //Display the calculated tip (formatted to two decimal spaces)
            double total = calculateTotal(tip, Double.parseDouble(inputForShortcut));
            userTotal.setText("$" + String.format("%.2f", total)); //Display the total amount (formatted to two decimal spaces)
        }
    }

    //SHORTCUT BUTTONS CODE
    //Five Percent button
    public void fiveP(View view) {
        shortCutFunction(5);
    }

    //Ten Percent button
    public void tenP(View view){
        shortCutFunction(10);
    }

    //Fifteen Percent button
    public void fifteenP(View view){
        shortCutFunction(15);
    }

    //Twenty Percent button
    public void twentyP(View view){
        shortCutFunction(20);
    }
}