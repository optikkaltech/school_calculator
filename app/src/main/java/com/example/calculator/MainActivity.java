package com.example.calculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize number buttons
        Button num0 = findViewById(R.id.num0);
        Button num1 = findViewById(R.id.num1);
        Button num2 = findViewById(R.id.num2);
        Button num3 = findViewById(R.id.num3);
        Button num4 = findViewById(R.id.num4);
        Button num5 = findViewById(R.id.num5);
        Button num6 = findViewById(R.id.num6);
        Button num7 = findViewById(R.id.num7);
        Button num8 = findViewById(R.id.num8);
        Button num9 = findViewById(R.id.num9);

        // Initialize operation buttons
        Button on = findViewById(R.id.on);
        Button off = findViewById(R.id.off);
        Button ac = findViewById(R.id.ac);
        Button del = findViewById(R.id.del);
        Button div = findViewById(R.id.div);
        Button times = findViewById(R.id.times);
        Button min = findViewById(R.id.min);
        Button equal = findViewById(R.id.equal);
        Button plus = findViewById(R.id.plus);
        Button dot = findViewById(R.id.dot);

        // Initialize the display screen
        TextView screen = findViewById(R.id.screen);

        // Set up the "off" button to hide the screen
        off.setOnClickListener(view -> screen.setVisibility(View.GONE));

        // Set up the "on" button to show the screen and reset the text
        on.setOnClickListener(view -> {
            screen.setVisibility(View.VISIBLE);
            screen.setText("0");
        });

        // Create a list of number buttons for easier handling
        ArrayList<Button> nums = new ArrayList<>();
        nums.add(num0);
        nums.add(num1);
        nums.add(num2);
        nums.add(num3);
        nums.add(num4);
        nums.add(num5);
        nums.add(num6);
        nums.add(num7);
        nums.add(num8);
        nums.add(num9);



        // Set up number buttons to append their text to the screen (Display section)
        for (Button button : nums) {
            button.setOnClickListener(view -> {
                // If the screen does not show "0", append the number
                if (!screen.getText().toString().equals("0")) {
                    screen.setText(screen.getText().toString() + button.getText());
                } else {
                    // If the screen shows "0", replace it with the number
                    screen.setText(button.getText());
                }
            });
        }

        // Set up operation buttons to handle different operations (Arithimatic Operations)
        div.setOnClickListener(view -> handleOperation(screen, "/"));
        times.setOnClickListener(view -> handleOperation(screen, "X"));
        min.setOnClickListener(view -> handleOperation(screen, "-"));
        plus.setOnClickListener(view -> handleOperation(screen, "+"));

        // Set up the "equal" button to calculate and display the result
        equal.setOnClickListener(view -> {
            String currentText = screen.getText().toString().trim();
            String[] parts = currentText.split(" ");

            // Ensure there are enough parts for a calculation
            if (parts.length < 3) return;

            // Clean up trailing dots (e.g., "5." becomes "5")
            if (parts[0].endsWith(".")) {
                parts[0] = parts[0].substring(0, parts[0].length() - 1);
            }
            if (parts[2].endsWith(".")) {
                parts[2] = parts[2].substring(0, parts[2].length() - 1);
            }

            double firstNum = Double.parseDouble(parts[0]);
            String operation = parts[1];
            double secondNum = Double.parseDouble(parts[2]);

            double result = 0;
            // Perform the calculation based on the operation
            switch (operation) {
                case "+":
                    result = firstNum + secondNum;
                    break;
                case "-":
                    result = firstNum - secondNum;
                    break;
                case "X":
                    result = firstNum * secondNum;
                    break;
                case "/":
                    result = firstNum / secondNum;
                    break;
            }

            // Display the result as an integer if it's a whole number
            if (result == (int) result) {
                screen.setText(String.valueOf((int) result));
            } else {
                screen.setText(String.valueOf(result));
            }
        });



        // Set up the "delete" button to remove the last character or reset to "0"
        del.setOnClickListener(view -> {
            String str = screen.getText().toString();
            if (str.length() > 1) {
                screen.setText(str.substring(0, str.length() - 1));
            } else if (str.length() == 1 && !str.equals("0")) {
                screen.setText("0");
            }
        });

        // Set up the "dot" button to add a decimal point if not already present
        dot.setOnClickListener(view -> {
            String currentText = screen.getText().toString();
            String[] parts = currentText.split(" ");

            // Check the last number in the expression to ensure no dot is already present
            String lastPart = parts[parts.length - 1];

            if (!lastPart.contains(".")) {
                screen.setText(currentText + ".");
            }
        });


        // Set up the "AC" (all clear) button to reset the screen to "0"
        ac.setOnClickListener(view -> screen.setText("0"));
    }

    // Helper method to handle different operations
    @SuppressLint("SetTextI18n")
    private void handleOperation(TextView screen, String operation) {
        // Add the selected operation to the screen text if it does not already end with an operation
        if (!screen.getText().toString().endsWith(" ")) {
            screen.setText(screen.getText().toString() + " " + operation + " ");
        }
    }
}