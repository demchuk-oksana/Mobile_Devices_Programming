package com.example.lab_w_2;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

public class MainActivity extends AppCompatActivity {
    EditText expression;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        expression = findViewById(R.id.expression);
        result = findViewById(R.id.result);

        int[] buttonIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
                R.id.btnAdd, R.id.btnSubtract, R.id.btnMultiply, R.id.btnDivide,
                R.id.btnOpenParen, R.id.btnCloseParen
        };

        // Listener for normal button clicks
        View.OnClickListener listener = v -> {
            Button b = (Button) v;
            expression.append(b.getText().toString());
        };

        for (int id : buttonIds) {
            findViewById(id).setOnClickListener(listener);
        }

        // Clear button click
        findViewById(R.id.btnClear).setOnClickListener(v -> expression.setText(""));

        // Equals button click
        findViewById(R.id.btnEquals).setOnClickListener(v -> evaluateExpression());

        // Backspace button click
        findViewById(R.id.btnDelete).setOnClickListener(v -> {
            String currentText = expression.getText().toString();
            if (currentText.length() > 0) {
                expression.setText(currentText.substring(0, currentText.length() - 1));
            }
        });

        // Decimal point button click
        findViewById(R.id.btnDot).setOnClickListener(v -> appendDecimalPoint());
    }

    // Append a decimal point if allowed
    private void appendDecimalPoint() {
        String currentText = expression.getText().toString();

        // Check if the last character is a number and if a decimal already exists in it
        if (!currentText.isEmpty() && isValidDecimal(currentText)) {
            expression.append(".");
        }
    }

    // Check if the last number in the expression already has a decimal point
    private boolean isValidDecimal(String expr) {
        // Match the last number in the expression (without any operators)
        String[] parts = expr.split("[-+*/^()]");
        String lastPart = parts.length > 0 ? parts[parts.length - 1] : "";

        // Return true if there is no decimal point in the last number
        return !lastPart.contains(".");
    }

    // Evaluate the mathematical expression
    private void evaluateExpression() {
        String expr = expression.getText().toString();

        if (!isValidExpression(expr)) {
            result.setText("Error: Invalid expression");
            return;
        }

        try {
            Expression expression = new ExpressionBuilder(expr).build();
            double evalResult = expression.evaluate();
            result.setText("" + evalResult);
        } catch (ArithmeticException e) {
            result.setText("Error: " + e.getMessage());
        } catch (Exception e) {
            result.setText("Error: Invalid input");
        }
    }

    // Validate the expression (parentheses matching)
    private boolean isValidExpression(String expr) {
        int openParen = 0, closeParen = 0;
        for (char c : expr.toCharArray()) {
            if (c == '(') openParen++;
            if (c == ')') closeParen++;
            if (closeParen > openParen) return false;
        }
        return openParen == closeParen;
    }
}
