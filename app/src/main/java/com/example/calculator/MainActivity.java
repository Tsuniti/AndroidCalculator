package com.example.calculator;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    TextView numberText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        numberText = findViewById(R.id.textNumber);
    }




    public void numberListener(View view){
        Button button = (Button) view;

        String buttonText = button.getText().toString();
//        Object tag = button.getTag();
//        int id = button.getId();
        //if...

        String text = numberText.getText().toString();
        String[] stringNumbers = text.split("[+\\-*/]", -1);
        if(stringNumbers[stringNumbers.length - 1].length() >= 15){

            Toast toast = Toast.makeText(this, "You cannot enter more than 15 digits", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.BOTTOM, 0, 100);
            toast.show();

            return;

        }


        numberText.append(buttonText);
    }
    public void operatorListener(View view){

        String text = numberText.getText().toString();

        if(text.isEmpty())
        {
            return;
        }


        if(text.endsWith("+")
                || text.endsWith("-")
                || text.endsWith("*")
                || text.endsWith("/"))
        {
            numberText.setText(text.substring(0, text.length() - 1));
        }
        else if(text.startsWith("-"))
        {
            text = text.substring(1, text.length());
        }

        if(text.contains("+")
        || text.contains("-")
        || text.contains("*")
        || text.contains("/")) {
            result();
        }

            Button button = (Button) view;

            numberText.append(button.getText().toString());


    }

    public void dotListener(View view)
    {
        String text = numberText.getText().toString();

        if(text.isEmpty())
        {
            numberText.append("0.");
            return;
        }


        String[] stringNumbers = text.split("[+\\-*/]", -1);
        if(stringNumbers[stringNumbers.length - 1].isEmpty())
        {
            numberText.append("0.");
            return;
        }
        if(stringNumbers[stringNumbers.length - 1].contains("."))
        {
            return;
        }

        numberText.append(".");
    }

    public void backspaceListener(View view){
        String text = numberText.getText().toString();

        if(text.isEmpty())
        {
            return;
        }

        numberText.setText(text.substring(0, text.length() - 1));

    }
    public void clearListener(View view){
        numberText.setText("");
    }


    public void equalsListener(View view){

        String text = numberText.getText().toString();

        if(text.isEmpty())
        {
            return;
        }

        if(text.endsWith("+")
                || text.endsWith("-")
                || text.endsWith("*")
                || text.endsWith("/"))
        {
            return;
        }

        result();
    }

    public void result()
    {

        double result;
        String resultStr;

        char operator = 0;
        int operatorIndex = -1;
        String expression = numberText.getText().toString();

        if(expression.isEmpty())
        {
            return;
        }


        for (int i = 1; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if (c == '+' || c == '-' || c == '*' || c == '/') {
                operator = c;
                operatorIndex = i;
                break;
            }
        }

        if (operatorIndex == -1) {
            return;
        }

        double first = Double.parseDouble(expression.substring(0, operatorIndex));
        double second = Double.parseDouble(expression.substring(operatorIndex + 1));


        switch (operator) {
            case '+':
                result = first + second;
                break;
            case '-':
                result = first - second;
                break;
            case '*':
                result = first * second;
                break;
            case '/':
                if (second == 0) {

                    Toast toast = Toast.makeText(this, "You can't divide by zero", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.BOTTOM, 0, 100);
                    toast.show();

                    return;
                }
                result = first / second;
                break;
            default: throw new IllegalStateException("unknown operator: " + operator);
        }

        if(result == (long) result)
        {
            resultStr = String.valueOf((long) result);
        }
        else
        {
            resultStr = String.valueOf(result);
        }

        numberText.setText(resultStr);
    }
}