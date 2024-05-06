package il.ac.tcb.calcapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText editTextNumber1, editTextNumber2;
    TextView res;
    Button calc;
    Spinner spinner;
    String operator;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        editTextNumber1 = findViewById(R.id.editTextNumber1);
        editTextNumber2 = findViewById(R.id.editTextNumber2);
        res = findViewById(R.id.res);
        calc = findViewById(R.id.calc);

        // New Spinner Integration with ArrayList
        spinner = findViewById(R.id.spinner);
        ArrayList<String> operatorsList = new ArrayList<>();
        operatorsList.add("+");
        operatorsList.add("-");
        operatorsList.add("*");
        operatorsList.add("/");
        operatorsList.add("^");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, operatorsList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                operator = operatorsList.get(position);
                Toast.makeText(context, operator, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing
            }
        });

        // Button Click Listener
        calc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculate();
            }

            private void calculate() {
                double num1 = Double.parseDouble(editTextNumber1.getText().toString());
                double num2 = Double.parseDouble(editTextNumber2.getText().toString());
                double result = 0;
                try {
                    if (operator == null) {
                        Toast.makeText(context, "Please select an operator", Toast.LENGTH_SHORT).show();
                        return; // Exit the method early
                    }
                    switch (operator) {
                        case "+":
                            result = num1 + num2;
                            break;
                        case "-":
                            result = num1 - num2;
                            break;
                        case "*":
                            result = num1 * num2;
                            break;
                        case "/":
                            if (num2 != 0) {
                                result = num1 / num2;
                            } else {
                                throw new ArithmeticException("Division by zero");
                            }
                            break;
                        case "^":
                            result = Math.pow(num1, num2);
                            break;
                    }
                    res.setText(String.format("%.2f", result));
                } catch (Exception e) {
                    Toast.makeText(context, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}